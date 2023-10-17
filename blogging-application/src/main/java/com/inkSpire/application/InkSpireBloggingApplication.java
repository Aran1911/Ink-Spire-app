package com.inkSpire.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class InkSpireBloggingApplication {

	public static void main(String[] args) {
		SpringApplication.run(InkSpireBloggingApplication.class, args);
	}

}
