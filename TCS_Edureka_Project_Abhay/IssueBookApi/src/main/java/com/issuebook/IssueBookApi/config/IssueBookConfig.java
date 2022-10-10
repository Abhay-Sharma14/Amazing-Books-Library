package com.issuebook.IssueBookApi.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class IssueBookConfig {
	
	@Bean
	public RestTemplate getRestTemplate(){
		return  new RestTemplate();
	}


}
