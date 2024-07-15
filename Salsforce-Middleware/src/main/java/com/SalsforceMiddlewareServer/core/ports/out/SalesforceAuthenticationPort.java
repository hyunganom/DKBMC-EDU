package com.SalsforceMiddlewareServer.core.ports.out;

import com.SalsforceMiddlewareServer.core.domain.salesforce.SalesforceLoginResult;

public interface SalesforceAuthenticationPort {
    SalesforceLoginResult loginToSalesforce() throws Exception;
}
