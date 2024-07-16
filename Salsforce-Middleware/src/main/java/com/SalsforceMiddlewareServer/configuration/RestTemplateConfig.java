package com.SalsforceMiddlewareServer.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder; //RestTemplate을 빌드하기 위한 유틸리티 클래스
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.ClientHttpRequestInterceptor; //HTTP 요청 인터셉터 인터페이스.
import org.springframework.web.client.RestTemplate; //Spring의 REST 클라이언트.

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, Environment environment) { //Spring의 RestTemplate을 구성
        String token = environment.getProperty("openai.api.key");

        return builder
                //요청에 Authorization 헤더와 Content-Type 헤더를 추가하는 인터셉터를 구성
                .additionalInterceptors((ClientHttpRequestInterceptor) (request, body, execution) -> { //람다식
                    request.getHeaders().set("Authorization", "Bearer " + token); //Authorization 헤더를 설정합니다.
                    request.getHeaders().set("Content-Type", "application/json"); //Content-Type 헤더를 설정
                    return execution.execute(request, body);
                })
                .build();
    }
}
