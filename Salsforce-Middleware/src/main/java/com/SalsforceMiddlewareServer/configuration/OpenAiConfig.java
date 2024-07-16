package com.SalsforceMiddlewareServer.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenAiConfig {

    @Value("${openai.api.key}") //애플리케이션 설정 값을 주입받기 위한 어노테이션.
    private String openAiKey;

    @Bean
    public RestTemplate template() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> { // 람다식
            request.getHeaders().add("Authorization", "Bearer " + openAiKey); //요청 인터셉터를 추가하여 모든 요청에 Authorization 헤더를 설정
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}
