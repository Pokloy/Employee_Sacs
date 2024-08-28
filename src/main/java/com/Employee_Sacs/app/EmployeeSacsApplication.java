package com.Employee_Sacs.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class EmployeeSacsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeSacsApplication.class, args);
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(EmployeeSacsApplication.class);
	}
}
