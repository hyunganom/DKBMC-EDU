package com.SalsforceMiddlewareServer.adapters.out;

import com.SalsforceMiddlewareServer.core.domain.salesforce.SalesforceLoginResult;
import com.SalsforceMiddlewareServer.core.domain.salesforce.SendToSalesforce;
import com.SalsforceMiddlewareServer.core.ports.out.SalesforceAuthenticationPort;
import com.SalsforceMiddlewareServer.core.ports.out.SalesforceOutboundPort;
import com.SalsforceMiddlewareServer.utils.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class SalesforceOutboundPortImpl implements SalesforceOutboundPort { //SalesforceOutboundPort 인터페이스
    public static final String QUERY_PATH = "/services/data/v52.0/sobjects/chatGPT__e/";

    private final CloseableHttpClient closeableHttpClient;
    private final ObjectMapper objectMapper;
    private final SalesforceAuthenticationPort salesforceAuthenticationPort;

    @Override
    public void sendChat(SendToSalesforce sendChat) throws Exception {
        log.debug("sendChat(newChat={})", sendChat);

        SalesforceLoginResult salesforceLoginResult = salesforceAuthenticationPort.loginToSalesforce(); //Salesforce에 로그인하여 인증 토큰을 받습니다.

        URIBuilder builder = new URIBuilder(salesforceLoginResult.getInstanceUrl()); //인증된 Salesforce 인스턴스 URL을 설정
        builder.setPath(QUERY_PATH); //경로를 설정

        HttpPost post = new HttpPost(builder.build()); //HTTP POST 요청을 생성
        post.setHeader("Authorization", "Bearer " + salesforceLoginResult.getAccessToken()); //헤더를 설정
        post.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        post.setEntity(new StringEntity(objectMapper.writeValueAsString(sendChat), "UTF-8"));
        log.debug("post={}", post);

        HttpResponse httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(post);
            log.debug("HTTP response received: {}", httpResponse.getStatusLine());

            HttpUtils.checkResponse(httpResponse); //응답을 검사
            log.debug("Chat sent successfully!");
        } catch (IOException e) {
            log.error("IOException occurred during HTTP request: ", e);
            throw new Exception("Failed to send chat due to IO error", e);
        } catch (Exception e) {
            log.error("Exception occurred: ", e);
            throw e;
        } finally {
            if (httpResponse != null) {
                EntityUtils.consumeQuietly(httpResponse.getEntity());
            }
        }
    }
}
