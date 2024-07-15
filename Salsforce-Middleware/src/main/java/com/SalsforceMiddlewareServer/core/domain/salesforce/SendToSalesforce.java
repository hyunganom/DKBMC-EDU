package com.SalsforceMiddlewareServer.core.domain.salesforce;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SendToSalesforce {
    @JsonProperty("Receive__c")
    private String receive; //Salesforce에 보낼 데이터
}
