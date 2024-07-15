package com.SalsforceMiddlewareServer.core.domain.chatgpt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //getter, setter, toString, equals, hashCode 메소드를 자동으로 생성
@NoArgsConstructor //파라미터가 없는 기본 생성자를 자동으로 생성
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자를 자동으로 생성
public class GPTMessage {
    private String role; //메시지의 역할을 나타내는 문자열 (예: "user", "assistant").
    private String content; //메시지의 내용을 나타내는 문자열.
}

//https://velog.io/@jhp21c/day-20-Chat-GPT-API 간단한 요청 실습 글
//https://velog.io/@yunh03/Spring-Boot%EC%97%90%EC%84%9C-ChatGPT-%ED%99%9C%EC%9A%A9%ED%95%98%EA%B8%B0-1-API-%EC%97%B0%EA%B2%B0-%ED%85%8C%EC%8A%A4%ED%8A%B8