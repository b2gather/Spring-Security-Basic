package com.tlab.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

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
				.logout(customizer -> customizer
						.logoutSuccessUrl("/")
				)
				.build();
	}

	@Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
