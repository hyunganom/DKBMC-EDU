package com.SalsforceMiddlewareServer.core.ports.in;

public interface ReceiveMessageUseCase {
    void receiveMessage(String message); //일반적인 메시지를 수신하여 처리하는 메서드
    void receiveGPTMessage(String message); //GPT와 관련된 메시지를 수신하여 처리하는 메서드
}
