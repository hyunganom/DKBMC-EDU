package com.SalsforceMiddlewareServer.adapters.in.messaging;

import com.SalsforceMiddlewareServer.core.ports.in.ReceiveMessageUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component //이 클래스를 Spring의 컴포넌트로 등록
@Slf4j
@RequiredArgsConstructor
public class RabbitMQAdapter {

    private final ReceiveMessageUseCase receiveMessageUseCase;

    @RabbitListener(queues = "q.api") //큐에서 메시지를 수신
    public void receiveMessage(String message) {
        log.info("Received message: {}", message);
        receiveMessageUseCase.receiveMessage(message);  // 메시지를 처리하도록 UseCase에 전달
    }

    @RabbitListener(queues = "gptqueue")
    public void receiveGPTMessage(String message) {
        log.info("Received message from gptqueue: {}", message);
        receiveMessageUseCase.receiveGPTMessage(message);
    }
}