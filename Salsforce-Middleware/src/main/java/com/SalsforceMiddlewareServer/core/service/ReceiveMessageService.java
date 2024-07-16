package com.SalsforceMiddlewareServer.core.service;

import com.SalsforceMiddlewareServer.core.domain.salesforce.SendToSalesforce;
import com.SalsforceMiddlewareServer.core.ports.in.ReceiveMessageUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiveMessageService implements ReceiveMessageUseCase {

    private final ChatGPTService chatGPTService;
    private final SalesforceService salesforceService;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void receiveMessage(String message) {
        try {
            String response = processMessage(message);
            sendMessageToQueue(response);
        } catch (Exception e) {
            log.error("Error processing message: {}", message, e);
        }
    }

    @Override
    public void receiveGPTMessage(String message) {
        try {
            sendChatToSalesforce(message);
        } catch (Exception e) {
            log.error("Error sending message to Salesforce: {}", message, e);
        }
    }

    private String processMessage(String message) {
        String response = chatGPTService.chat(message);
        log.info("Response from ChatGPTService: {}", response);
        return response;
    }

    private void sendMessageToQueue(String message) {
        rabbitTemplate.convertAndSend("gptqueue", message);
        log.info("Sent message to gptqueue: {}", message);
    }

    private void sendChatToSalesforce(String message) throws Exception {
        SendToSalesforce newChat = new SendToSalesforce();
        newChat.setReceive(message);
        salesforceService.sendToSalesforce(newChat);
        log.info("Sent message to Salesforce: {}", message);
    }
}
