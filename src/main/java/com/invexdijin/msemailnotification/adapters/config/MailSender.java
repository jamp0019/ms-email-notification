package com.invexdijin.msemailnotification.adapters.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.HashMap;
import java.util.Properties;

@Configuration
public class MailSender {

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.hostinger.com");
        mailSender.setPort(587);
        mailSender.setUsername("contacto@invexdijin.com");
        mailSender.setPassword("C0nt@c_22042024");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");
        return mailSender;
    }

    @Bean
    public HashMap<String, Object> useMap() {
        HashMap<String, Object> responseMap = new HashMap<>();
        return responseMap;
    }
}
