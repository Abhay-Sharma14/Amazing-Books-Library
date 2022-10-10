package com.spring.boot.cloud.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.boot.cloud.gateway.filter.JwtAuthenticationFilter;


@Configuration
public class GatewayConfig {

	@Autowired
	private JwtAuthenticationFilter filter;

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes().route("auth", r -> r.path("/auth/**").filters(f -> f.filter(filter)).uri("lb://auth"))
				.route("book-api", r -> r.path("/library/**").filters(f -> f.filter(filter)).uri("lb://book-api"))
				.route("issue-book-api", r -> r.path("/issue/**").filters(f -> f.filter(filter)).uri("lb://issue-book-api"))
				.build();
	}

}
