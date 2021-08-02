package com.ctwork.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CTPOSAppl {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(CTPOSAppl.class, args);

	}
}
