package com.SalsforceMiddlewareServer.core.ports.in;

import com.SalsforceMiddlewareServer.core.domain.salesforce.SendToSalesforce;

public interface SalesforceUseCase { // Salesforce와의 상호작용을 위한 유스 케이스(Use Case)를 정의
    void sendToSalesforce(SendToSalesforce newChat); //객체를 Salesforce로 전송
    String handleSalesforceWebhook(String receiveChat); // Salesforce에서 수신된 웹훅 요청을 처리하고 그 결과를 문자열로 반환하는 메서드
}
