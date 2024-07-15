package com.SalsforceMiddlewareServer.core.domain.chatgpt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; //Jackson 라이브러리의 JsonIgnoreProperties 어노테이션을 사용하여 JSON 직렬화/역직렬화 시 알 수 없는 속성을 무시
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatGPTResponse {
    private String id; //응답의 고유 식별자
    private String object; // 응답 객체의 유형을 나타내는 문자열
    private List<Choice> choices;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Choice { // 내부 클래스
        private int index;
        private GPTMessage message; //선택지와 관련된 메시지 객체.
        private Object logprobs;
    }
}
