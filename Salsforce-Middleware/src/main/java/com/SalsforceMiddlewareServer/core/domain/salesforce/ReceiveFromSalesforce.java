package com.SalsforceMiddlewareServer.core.domain.salesforce;

import com.fasterxml.jackson.annotation.JsonProperty; //Jackson 라이브러리의 JsonProperty 어노테이션을 사용하여 JSON 직렬화/역직렬화 시 필드 이름을 매핑
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ReceiveFromSalesforce {
    @JsonProperty("send__c")
    String send__c; //Salesforce에서 받은 데이터를 매핑할 필드
}
