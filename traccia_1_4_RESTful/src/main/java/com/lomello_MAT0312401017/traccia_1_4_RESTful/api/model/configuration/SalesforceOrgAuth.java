package com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SalesforceOrgAuth {
    @Value("${salesforce.auth.url}")
    private String salesforceAuthUrl;
    @Value("${salesforce.auth.grant-type}")
    private String grantType;
    @Value("${salesforce.auth.client-id}")
    private String clientId;
    @Value("${salesforce.auth.client-secret}")
    private String clientSecret;
    @Value("${salesforce.auth.username}")
    private String username;
    @Value("${salesforce.auth.password}")
    private String password;
    
    public String getSalesforceAuthUrl() {
        return salesforceAuthUrl;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}