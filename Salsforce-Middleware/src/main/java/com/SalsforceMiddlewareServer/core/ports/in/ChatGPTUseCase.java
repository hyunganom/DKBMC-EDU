package com.SalsforceMiddlewareServer.core.ports.in;

public interface ChatGPTUseCase {
    String chat(String prompt); //입력된 프롬프트(prompt)를 받아 ChatGPT와 상호작용한 결과를 문자열로 반환
}
//계약 정의 -  ChatGPT와의 상호작용에 대한 계약을 정의
//유연성 제공
//의존성 역전 - 인터페이스를 사용하여 애플리케이션의 주요 비즈니스 로직이 특정 구현체에 의존하지 않도록 한다.
