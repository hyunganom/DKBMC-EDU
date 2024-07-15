package com.SalsforceMiddlewareServer.core.domain.salesforce;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SalesforceAttributes {
    private String type; //Salesforce 객체의 유형
    private String url; //salesforce 객체의 URL
}
