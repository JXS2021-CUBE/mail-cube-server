package com.cube.mailcube.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("http://localhost:3000", "https://mail-cube-web.vercel.app:80", "https://mailcube-chorom.vercel.app:80", "https://www.test-cors.org:80")
			.allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH")
			.exposedHeaders("Location");
	}
}