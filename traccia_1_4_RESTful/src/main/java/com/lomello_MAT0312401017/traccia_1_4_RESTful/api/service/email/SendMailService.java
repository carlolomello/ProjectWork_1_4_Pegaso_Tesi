package com.lomello_MAT0312401017.traccia_1_4_RESTful.api.service.email;

import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.email.Mail;

public interface SendMailService {
	void sendMail(Mail mail);
	void sendHtmlMail(Mail mail);
}