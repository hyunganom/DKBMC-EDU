package com.SalsforceMiddlewareServer.core.domain.salesforce;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) //JSON 직렬화/역직렬화 시 클래스에 존재하지 않는 속성을 무시
@Data
public class SalesforceLoginResult {
    @JsonProperty(value = "access_token")
    private String accessToken; //Salesforce로부터 받은 액세스 토큰을 저장하는 문자열.

    @JsonProperty(value = "instance_url")
    private String instanceUrl; //Salesforce 인스턴스의 URL을 저장하는 문자열.
}
