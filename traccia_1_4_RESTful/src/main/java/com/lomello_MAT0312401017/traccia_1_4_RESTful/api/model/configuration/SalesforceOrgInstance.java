package com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SalesforceOrgInstance {
    @Value("${salesforce.instance.url}")
    private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
