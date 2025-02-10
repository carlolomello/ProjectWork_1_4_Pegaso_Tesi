package com.lomello_MAT0312401017.traccia_1_4_RESTful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, DataSourceAutoConfiguration.class}) 
//SecurityAutoConfiguration exclude per spring security
//DataSourceAutoConfiguration.class exclude per database del tracciamento di batch
public class Traccia14ResTfulApplication {
	public static void main(String[] args) {
		SpringApplication.run(Traccia14ResTfulApplication.class, args);
		
		
	}
}
