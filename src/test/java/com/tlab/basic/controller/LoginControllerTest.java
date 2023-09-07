package com.tlab.basic.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
class LoginControllerTest extends AbstractControllerTest {

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	@DisplayName("LoginPage")
	@Test
	void LoginPage() throws Exception {
		// when
		ResultActions actions = mockMvc.perform(get("/login"));

		// then
		actions.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("login"))
				.andExpect(content().contentType("text/html;charset=UTF-8"))
		;
	}

}