package com.tlab.basic.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IndexController.class)
class IndexControllerTest extends AbstractControllerTest {

	@DisplayName("IndexPage")
	@Test
	void IndexPage() throws Exception {
		// when
		ResultActions actions = mockMvc.perform(get(""));

		// then
		actions.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("index"))
				.andExpect(content().contentType("text/html;charset=UTF-8"))
		;
	}

	@DisplayName("IndexPage | /")
	@Test
	void IndexPage_slash() throws Exception {
		// when
		ResultActions actions = mockMvc.perform(get("/"));

		// then
		actions.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("index"))
				.andExpect(content().contentType("text/html;charset=UTF-8"))
		;
	}

	@DisplayName("UserPage | 인증없음 -> is3xxRedirection -> /login")
	@Test
	void UserPage() throws Exception {
		// when
		ResultActions actions = mockMvc.perform(get("/user"));

		// then
		actions.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"))
		;
	}

	@DisplayName("UserPage | USER -> isOk")
	@WithMockUser(roles = "USER")
	@Test
	void UserPage_withRole_USER() throws Exception {
		// when
		ResultActions actions = mockMvc.perform(get("/user"));

		// then
		actions.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/plain;charset=UTF-8"))
		;
	}

	@DisplayName("UserPage | MANAGER -> isOk")
	@WithMockUser(roles = "MANAGER")
	@Test
	void UserPage_withRole_MANAGER() throws Exception {
		// when
		ResultActions actions = mockMvc.perform(get("/user"));

		// then
		actions.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/plain;charset=UTF-8"))
		;
	}

	@DisplayName("UserPage | ADMIN -> isOk")
	@WithMockUser(roles = "ADMIN")
	@Test
	void UserPage_withRole_ADMIN() throws Exception {
		// when
		ResultActions actions = mockMvc.perform(get("/user"));

		// then
		actions.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/plain;charset=UTF-8"))
		;
	}

	@DisplayName("AdminPage | 인증없음 -> is3xxRedirection -> /login")
	@Test
	void AdminPage() throws Exception {
		// when
		ResultActions actions = mockMvc.perform(get("/admin"));

		// then
		actions.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"))
		;
	}

	@DisplayName("AdminPage | ADMIN -> isOk")
	@WithMockUser(roles = "ADMIN")
	@Test
	void AdminPage_withRole_ADMIN() throws Exception {
		// when
		ResultActions actions = mockMvc.perform(get("/admin"));

		// then
		actions.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/plain;charset=UTF-8"))
		;
	}

	@DisplayName("AdminPage | MANAGER -> isForbidden")
	@WithMockUser(roles = "MANAGER")
	@Test
	void AdminPage_withRole_MANAGER() throws Exception {
		// when
		ResultActions actions = mockMvc.perform(get("/admin"));

		// then
		actions.andDo(print())
				.andExpect(status().isForbidden())
		;
	}

	@DisplayName("AdminPage | USER -> isForbidden")
	@WithMockUser(roles = "USER")
	@Test
	void AdminPage_withRole_USER() throws Exception {
		// when
		ResultActions actions = mockMvc.perform(get("/admin"));

		// then
		actions.andDo(print())
				.andExpect(status().isForbidden())
		;
	}

	@DisplayName("ManagerPage | 인증없음 -> is3xxRedirection -> /login")
	@Test
	void ManagerPage() throws Exception {
		// when
		ResultActions actions = mockMvc.perform(get("/manager"));

		// then
		actions.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"))
		;
	}

	@DisplayName("ManagerPage | USER -> isForbidden")
	@WithMockUser(roles = "USER")
	@Test
	void ManagerPage_withRole_USER() throws Exception {
		// when
		ResultActions actions = mockMvc.perform(get("/manager"));

		// then
		actions.andDo(print())
				.andExpect(status().isForbidden())
		;
	}

	@DisplayName("ManagerPage | MANAGER -> isOk")
	@WithMockUser(roles = "MANAGER")
	@Test
	void ManagerPage_withRole_MANAGER() throws Exception {
		// when
		ResultActions actions = mockMvc.perform(get("/manager"));

		// then
		actions.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/plain;charset=UTF-8"))
		;
	}

	@DisplayName("ManagerPage | ADMIN -> isOk")
	@WithMockUser(roles = "ADMIN")
	@Test
	void ManagerPage_withRole_ADMIN() throws Exception {
		// when
		ResultActions actions = mockMvc.perform(get("/manager"));

		// then
		actions.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/plain;charset=UTF-8"))
		;
	}

}