package com.digicides;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import com.digicides.geocode.GeocodeService;

@SpringBootApplication
public class CodeChallengeProjApplication {

	@Bean(name = "geocodeService")
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	@ConfigurationProperties(prefix = "config")
	public GeocodeService getGeocodeService() {
		GeocodeService service = new GeocodeService();
		return service;
	}
	
	
	public static void main(java.lang.String[] args) {
		SpringApplication.run(CodeChallengeProjApplication.class, args);
	}

}
