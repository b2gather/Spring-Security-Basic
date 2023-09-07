package com.tlab.basic.controller;

import com.tlab.basic.domain.dto.MemberDto;
import com.tlab.basic.domain.dto.MemberRegisterDto;
import com.tlab.basic.exception.UsernameAlreadyExistException;
import com.tlab.basic.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
class MemberControllerTest extends AbstractControllerTest {

	@MockBean
	private MemberService memberService;

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	@DisplayName("RegisterPage")
	@Test
	void RegisterPage() throws Exception {
		// when
		ResultActions actions = mockMvc.perform(get("/members/register"));

		// then
		actions.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("members/register"))
				.andExpect(content().contentType("text/html;charset=UTF-8"))
		;
	}

	@DisplayName("Register")
	@Test
	void Register() throws Exception {
		// given
		MemberRegisterDto memberRegisterDto = getMemberRegisterDto();
		when(memberService.register(memberRegisterDto)).thenReturn(new MemberDto());

		// when
		ResultActions actions = mockMvc.perform(post("/members")
				.param("username", memberRegisterDto.getUsername())
				.param("password", memberRegisterDto.getPassword())
				.param("email", memberRegisterDto.getEmail())
		);

		// then
		actions.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
        ;
	}

	@DisplayName("Register | 존재하는 유저명 -> UsernameAlreadyExistException -> RegisterPage")
	@Test
	void Register_UsernameAlreadyExistException() throws Exception {
		// given
		MemberRegisterDto memberRegisterDto = getMemberRegisterDto();
		when(memberService.register(any())).thenThrow(new UsernameAlreadyExistException());

		// when
		ResultActions actions = mockMvc.perform(post("/members")
				.param("username", memberRegisterDto.getUsername())
				.param("password", memberRegisterDto.getPassword())
				.param("email", memberRegisterDto.getEmail())
		);

		// then
		actions.andDo(print())
                .andExpect(status().isUnprocessableEntity())
				.andExpect(view().name("members/register"))
				.andExpect(model().attributeExists(
						"memberRegisterDto", "org.springframework.validation.BindingResult.memberRegisterDto"
				))
                .andExpect(model().attributeHasFieldErrorCode("memberRegisterDto", "username", "AlreadyExist"))
        ;
	}

	private MemberRegisterDto getMemberRegisterDto() {
		MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
		memberRegisterDto.setUsername("username");
		memberRegisterDto.setPassword("password");
		memberRegisterDto.setEmail("<EMAIL>");
		return memberRegisterDto;
	}

}