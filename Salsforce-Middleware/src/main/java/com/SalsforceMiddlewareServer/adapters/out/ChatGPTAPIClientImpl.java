package com.SalsforceMiddlewareServer.adapters.out;

import com.SalsforceMiddlewareServer.core.domain.chatgpt.ChatGPTRequest;
import com.SalsforceMiddlewareServer.core.domain.chatgpt.ChatGPTResponse;
import com.SalsforceMiddlewareServer.core.ports.out.ChatGPTAPIClient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ChatGPTAPIClientImpl implements ChatGPTAPIClient {

    private final RestTemplate restTemplate;
    private final Environment environment;

    @Override
    public ChatGPTResponse chat(ChatGPTRequest request) {
        String apiURL = environment.getProperty("openai.api.url");
        return restTemplate.postForObject(apiURL, request, ChatGPTResponse.class);
    }
}
//RestTemplate
//-HTTP 통신을 위한 도구로 RESTful API 웹 서비스와 상호작용을 쉽게 외부 도메인에서 데이터를 가져오거나 전송할 때 쓰는 클래스
// - postForObject() : POST 요청에 대한 결과를 객체로 반환한다.
//