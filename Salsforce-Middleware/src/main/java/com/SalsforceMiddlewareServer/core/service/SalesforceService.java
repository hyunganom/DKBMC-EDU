package com.SalsforceMiddlewareServer.core.service;

import com.SalsforceMiddlewareServer.core.domain.salesforce.SendToSalesforce;
import com.SalsforceMiddlewareServer.core.ports.in.SalesforceUseCase;
import com.SalsforceMiddlewareServer.core.ports.out.SalesforceOutboundPort;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier; //특정 빈을 주입받기 위해 사용
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalesforceService implements SalesforceUseCase {

    private static final String SEND_FIELD = "send__c"; //JSON 필드 이름을 상수로 정의

    private final @Qualifier("salesforceOutboundPortImpl") SalesforceOutboundPort salesforceOutboundPort;
    private final ChatGPTService chatGPTService; //chatGPTService를 주입 받음
    private final ObjectMapper objectMapper; // JSON 처리를 위해 Jackson 라이브러리에서 제공

    @Override
    public void sendToSalesforce(SendToSalesforce newChat) {
        try {
            salesforceOutboundPort.sendChat(newChat); //아웃바운드 포트를 통해 Salesforce로 데이터를 전송
        } catch (Exception e) {
            log.error("Failed to send to Salesforce: {}", e.getMessage(), e); //로깅
            throw new RuntimeException("Failed to send to Salesforce", e);
        }
    }

    @Override
    public String handleSalesforceWebhook(String receiveChat) { //Salesforce 웹훅 요청을 처리
        try {
            JsonNode jsonNode = objectMapper.readTree(receiveChat); //수신된 JSON 문자열을 파싱
            String sendValue = jsonNode.get(SEND_FIELD).asText(); //특정 필드 값을 추출

            log.debug("Received from Salesforce: {}", receiveChat);
            log.debug("Salesforce send__c value: {}", sendValue);

            String response = chatGPTService.chat(sendValue); //객체를 생성하고 응답을 설정

            log.debug("ChatGPT response: {}", response);

            SendToSalesforce newChat = new SendToSalesforce();
            newChat.setReceive(response);

            sendToSalesforce(newChat); // Salesforce로 응답을 전송

            return response;
        } catch (Exception e) {
            log.error("Error handling webhook: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to handle Salesforce webhook", e);
        }
    }
}
