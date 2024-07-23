package com.SalsforceMiddlewareServer.configuration;

import com.SalsforceMiddlewareServer.adapters.out.SalesforceAuthenticationAdapter;
import com.SalsforceMiddlewareServer.adapters.out.SalesforceOutboundPortImpl;
import com.SalsforceMiddlewareServer.core.ports.out.SalesforceAuthenticationPort;
import com.SalsforceMiddlewareServer.core.ports.out.SalesforceOutboundPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.impl.client.CloseableHttpClient; //Apache HttpClient 구현체.
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class BeanConfiguration { // Salesforce와 상호작용하는 포트를 설정하는 여러 Bean을 정의

    @Bean
    @Primary // 같은 타입의 빈이 여러 개 있을 때 기본으로 사용될 빈을 지정
    public SalesforceAuthenticationPort salesforcePort(CloseableHttpClient closeableHttpClient,
                                                       SalesforceConfigurationProperties salesforceConfigurationProperties,
                                                       ObjectMapper objectMapper) {
        return new SalesforceAuthenticationAdapter(closeableHttpClient, salesforceConfigurationProperties, objectMapper);
    }

    @Bean
    public SalesforceOutboundPort salesforceOutboundPort(CloseableHttpClient closeableHttpClient,
                                                         ObjectMapper objectMapper,
                                                         SalesforceAuthenticationPort salesforceAuthenticationPort) {
        return new SalesforceOutboundPortImpl(closeableHttpClient, objectMapper, salesforceAuthenticationPort);
    }

}

//@Component
//@Component 어노테이션의 경우 개발자가 직접 작성한 Class를 Bean으로 등록하기 위한 어노테이션이다.
//@Bean
//@Bean 어노테이션의 경우 개발자가 직접 제어가 불가능한 외부 라이브러리등을 Bean으로 만들려할 때 사용된다.


