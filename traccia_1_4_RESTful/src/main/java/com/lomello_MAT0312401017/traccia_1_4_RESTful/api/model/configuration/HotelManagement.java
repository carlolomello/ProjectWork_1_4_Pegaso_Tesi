package com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HotelManagement {
	@Value("${hotelmanagement.mail.recepist}")
    private String recepistEmail;

	public String getRecepistEmail() {
		return recepistEmail;
	}
	
}
