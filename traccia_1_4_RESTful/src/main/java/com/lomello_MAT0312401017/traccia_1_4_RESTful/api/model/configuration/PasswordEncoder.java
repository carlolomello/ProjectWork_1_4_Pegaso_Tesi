package com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
	@Bean
	public static BCryptPasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}
}
