package com.SalsforceMiddlewareServer.core.ports.out;

import com.SalsforceMiddlewareServer.core.domain.salesforce.SendToSalesforce;

public interface SalesforceOutboundPort {
    void sendChat(SendToSalesforce sendChat) throws Exception; //객체를 Salesforce로 전송

}
