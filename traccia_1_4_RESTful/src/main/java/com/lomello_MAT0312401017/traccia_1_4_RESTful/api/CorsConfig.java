package com.lomello_MAT0312401017.traccia_1_4_RESTful.api;

//CorsConfig.java
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

 @Override
 public void addCorsMappings(CorsRegistry registry) {
	 registry.addMapping("/**")
     .allowedOrigins("http://localhost:3000", "https://anotherdomain.com") // Aggiungi qui gli origini consentiti
     .allowedMethods("GET", "POST", "PUT", "DELETE")
     .allowCredentials(true);  // Abilita i credenziali
 }
}
