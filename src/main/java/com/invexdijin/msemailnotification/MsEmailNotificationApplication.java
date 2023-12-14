package com.invexdijin.msemailnotification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MsEmailNotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsEmailNotificationApplication.class, args);
	}

}
