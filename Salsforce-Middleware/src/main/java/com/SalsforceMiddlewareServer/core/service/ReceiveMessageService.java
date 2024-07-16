package com.SalsforceMiddlewareServer.core.service;

import com.SalsforceMiddlewareServer.core.domain.salesforce.SendToSalesforce;
import com.SalsforceMiddlewareServer.core.ports.in.ReceiveMessageUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiveMessageService implements ReceiveMessageUseCase { //메시지에 관한 서비스 클래스

    private final ChatGPTService chatGPTService;
    private final SalesforceService salesforceService;
    private final RabbitTemplate rabbitTemplate; //Spring의 RabbitMQ 템플릿 클래스를 주입
    private final Environment environment;

    @Override
    public void receiveMessage(String message) { //메시지를 받아서 처리하고 큐에 전송
        try {
            String response = processMessage(message);
            sendMessageToQueue(response);
        } catch (Exception e) {
            log.error("Error processing message: {}", message, e);
        }
    }

    @Override
    public void receiveGPTMessage(String message) { //GPT 메시지를 받아서 Salesforce로 전송
        try {
            sendChatToSalesforce(message);
        } catch (Exception e) {
            log.error("Error sending message to Salesforce: {}", message, e);
        }
    }

    private String processMessage(String message) { //메시지를 처리하여 ChatGPT 서비스로부터 응답을 받는다.
        String response = chatGPTService.chat(message);
        log.info("Response from ChatGPTService: {}", response);
        return response;
    }

    private void sendMessageToQueue(String message) {
        String queueName = environment.getProperty("rabbitmq.queue.name", "defaultQueueName"); //설정 클래스를 사용하여 yaml파일에 있는 큐 이름을 불러옴
        rabbitTemplate.convertAndSend(queueName, message);//메시지를 지정된 큐에 전송
        log.info("Sent message to {}: {}", queueName, message);
    }

    private void sendChatToSalesforce(String message) throws Exception {
        SendToSalesforce newChat = new SendToSalesforce();
        newChat.setReceive(message);
        salesforceService.sendToSalesforce(newChat);
        log.info("Sent message to Salesforce: {}", message);
    }
}
