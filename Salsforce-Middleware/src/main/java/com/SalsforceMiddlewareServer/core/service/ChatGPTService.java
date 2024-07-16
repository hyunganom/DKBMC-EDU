package com.SalsforceMiddlewareServer.core.service;

import com.SalsforceMiddlewareServer.core.domain.chatgpt.ChatGPTRequest;
import com.SalsforceMiddlewareServer.core.ports.in.ChatGPTUseCase;
import com.SalsforceMiddlewareServer.core.ports.out.ChatGPTAPIClient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service //비즈니스 로직을 처리하는 서비스(Service) 클래스에 적용
@RequiredArgsConstructor
public class ChatGPTService implements ChatGPTUseCase {

    private final Environment environment; //환경 설정 값을 로드하기 위해 사용.
    private final ChatGPTAPIClient chatGPTAPIClient; //외부 ChatGPT API와 상호작용하기 위한 포트

    @Override
    public String chat(String prompt) { //프롬프트 입력을 받아 ChatGPT API와 상호작용하여 응답을 반환
        String model = environment.getProperty("openai.model"); //환경 설정에서 openai.model 값을 로드합니다.
        ChatGPTRequest request = new ChatGPTRequest(model, prompt); //요청 객체를 생성

        // ChatGPT API 클라이언트를 사용하여 요청을 보내고 응답에서 첫 번째 선택의 메시지 내용을 반환
        //메서드 체인이라는건데 메서드 호출을 연결해서 코드의 가독성을 높임
        return chatGPTAPIClient.chat(request).getChoices().get(0).getMessage().getContent();
    }
}
