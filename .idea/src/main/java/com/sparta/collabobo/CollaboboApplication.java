package com.sparta.collabobo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan(basePackages = "com.sparta.collabobo")
public class CollaboboApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollaboboApplication.class, args);
	}

}
