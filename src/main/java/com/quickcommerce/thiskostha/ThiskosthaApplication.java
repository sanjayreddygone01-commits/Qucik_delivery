package com.quickcommerce.thiskostha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ThiskosthaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThiskosthaApplication.class, args);
	}
	@Bean
	public RestTemplate getRestTemplste() {
		return new RestTemplate();
	}
}
