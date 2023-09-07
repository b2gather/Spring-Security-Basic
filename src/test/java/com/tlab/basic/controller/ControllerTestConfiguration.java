package com.tlab.basic.controller;

import com.tlab.basic.auth.OAuth2DetailsService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ControllerTestConfiguration {

	@Bean
	OAuth2DetailsService oAuth2DetailsService() {
		return new OAuth2DetailsService(null);
	}

}
