package com.SalsforceMiddlewareServer.adapters.out;

import com.SalsforceMiddlewareServer.configuration.SalesforceConfigurationProperties; //Salesforce 설정 속성을 포함하는 클래스.
import com.SalsforceMiddlewareServer.core.domain.salesforce.SalesforceLoginResult; //Salesforce 로그인 결과를 나타내는 클래스.
import com.SalsforceMiddlewareServer.core.ports.out.SalesforceAuthenticationPort; //Salesforce 인증 포트 인터페이스.
import com.fasterxml.jackson.databind.ObjectMapper; //JSON 직렬화/역직렬화 라이브러리.
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse; //Apache HttpClient 관련 클래스.
import org.apache.http.NameValuePair; //Apache HttpClient 관련 클래스.
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired; //Spring의 의존성 주입 어노테이션.
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SalesforceAuthenticationAdapter implements SalesforceAuthenticationPort {

    private static final String TOKEN_URL = "https://login.salesforce.com/services/oauth2/token"; // 상수 : Salesforce 인증 토큰을 요청할 URL을 정의

    private final CloseableHttpClient closeableHttpClient; //Apache HttpClient를 주입받는다.
    private final SalesforceConfigurationProperties salesforceConfigurationProperties; //설정 속성을 주입받는다.
    private final ObjectMapper objectMapper;

    @Autowired //의존성 주입 어노테이션을 사용하여 생성자를 통해 필드를 주입
    public SalesforceAuthenticationAdapter(CloseableHttpClient closeableHttpClient, SalesforceConfigurationProperties salesforceConfigurationProperties, ObjectMapper objectMapper) {
        this.closeableHttpClient = closeableHttpClient;
        this.salesforceConfigurationProperties = salesforceConfigurationProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    public SalesforceLoginResult loginToSalesforce() throws Exception { //loginToSalesforce() : Salesforce에 로그인 요청을 보내고 응답을 처리
        List<NameValuePair> loginParams = new ArrayList<>(); //로그인 파라미터를 설정
        loginParams.add(new BasicNameValuePair("client_id", salesforceConfigurationProperties.getConsumerKey()));
        loginParams.add(new BasicNameValuePair("client_secret", salesforceConfigurationProperties.getConsumerSecret()));
        loginParams.add(new BasicNameValuePair("grant_type", "password"));
        loginParams.add(new BasicNameValuePair("username", salesforceConfigurationProperties.getUsername()));
        loginParams.add(new BasicNameValuePair("password", salesforceConfigurationProperties.getPassword()));

        HttpPost post = new HttpPost(TOKEN_URL); //객체를 생성하고 URL을 설정
        post.setEntity(new UrlEncodedFormEntity(loginParams)); //로그인 파라미터를 엔티티로 설정

        HttpResponse httpResponse = closeableHttpClient.execute(post); //POST 요청을 실행하고 응답을 받는다.
        log.debug("HTTP response status: {}", httpResponse.getStatusLine());

        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            throw new IOException("Failed to login to Salesforce: " + httpResponse.getStatusLine().getReasonPhrase());
        }

        SalesforceLoginResult salesforceLoginResult = objectMapper.readValue(httpResponse.getEntity().getContent(), SalesforceLoginResult.class); // 응답 본문을 SalesforceLoginResult 객체로 변환
        log.debug("salesforceLoginResult={}", salesforceLoginResult);
        return salesforceLoginResult;
    }
}
