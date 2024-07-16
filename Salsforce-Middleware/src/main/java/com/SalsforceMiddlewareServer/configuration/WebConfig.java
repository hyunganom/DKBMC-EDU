package com.SalsforceMiddlewareServer.configuration;

import com.SalsforceMiddlewareServer.interceptors.LoggingInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature; //Jackson의 직렬화 기능 설정.
import org.apache.http.client.config.RequestConfig; //HttpClient의 요청 구성.
import org.apache.http.impl.client.CloseableHttpClient; //HttpClient 구현체.
import org.apache.http.impl.client.HttpClients; //HttpClient 빌더 유틸리티.
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //이 클래스를 Spring의 구성 클래스(Config class)로 등록
public class WebConfig implements WebMvcConfigurer { //WebMvcConfigurer : Spring MVC의 설정을 커스터마이징하기 위해 구현하는 인터페이스(아직 인터셉터 구현이 안되었음(TODO))

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLoggingInterceptor()).addPathPatterns("/**");
    }

    @Bean //Spring 컨텍스트에서 관리하는 빈을 정의
    public LoggingInterceptor getLoggingInterceptor() {
        return new LoggingInterceptor();
    }

    @Bean
    public ObjectMapper objectMapper() { // JSON 직렬화/역직렬화 라이브러리인 Jackson의 ObjectMapper를 빈으로 정의
        return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT); //  JSON 출력을 보기 좋게 들여쓰기
    }

    @Bean
    public CloseableHttpClient closeableHttpClient() {// Apache HttpClient의 CloseableHttpClient를 빈으로 정의
        // 요청 구성 설정을 정의
        RequestConfig requestConfig = RequestConfig.custom() //
                .setConnectTimeout(30 * 1000) // 연결 타임아웃을 30초로 설정
                .setSocketTimeout(30 * 1000)  // 소켓 타임아웃을 30초로 설정
                .build(); //빌더를 사용하여 CloseableHttpClient를 생성(빌더 패턴 사용)
                //타임아웃 30초는 만약 HTTP 통신에 실패하였을때 30초 타임아웃
        return HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();
    }
}
