package com.tlab.basic.config;

import com.tlab.basic.auth.OAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final OAuth2DetailsService oAuth2DetailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(customizer -> customizer
						.requestMatchers("/user/**").authenticated()
						.requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.anyRequest().permitAll()
				)
				.formLogin(customizer -> customizer
						.loginPage("/login")
						.loginProcessingUrl("/login")
						.defaultSuccessUrl("/")
						.permitAll()
				)
				.oauth2Login(customizer -> customizer
						.loginPage("/login")
						.userInfoEndpoint(uieCustomizer -> uieCustomizer
								.userService(oAuth2DetailsService)
						)
				)
				.logout(customizer -> customizer
						.logoutSuccessUrl("/")
				)
				.build();
	}

}
