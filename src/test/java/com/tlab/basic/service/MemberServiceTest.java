package com.tlab.basic.service;

import com.tlab.basic.domain.dto.MemberDto;
import com.tlab.basic.domain.dto.MemberRegisterDto;
import com.tlab.basic.domain.entity.Member;
import com.tlab.basic.domain.mapper.MemberMapper;
import com.tlab.basic.domain.mapper.MemberMapperImpl;
import com.tlab.basic.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

	@Mock
	private MemberRepository memberRepository;

	@Spy
	private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

	@Spy
	private final MemberMapper mapper = new MemberMapperImpl();

	@InjectMocks
    private MemberService memberService;

	@BeforeEach
	void setUp() {
		when(memberRepository.save(Mockito.any(Member.class))).thenAnswer(invocation -> {
			Member member = invocation.getArgument(0);
			ReflectionTestUtils.setField(member, "id", 1L);
            return member;
		});
	}

	@AfterEach
	void tearDown() {
	}

	@DisplayName("Register")
	@Test
	void Register() {
		// given
		MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
		memberRegisterDto.setUsername("John");
		memberRegisterDto.setPassword("<PASSWORD>");
		memberRegisterDto.setEmail("<EMAIL>");

		// when
		MemberDto registered = memberService.register(memberRegisterDto);

		// then
		assertThat(registered.getPassword()).isNotEqualTo(memberRegisterDto.getPassword());
		assertThat(passwordEncoder.matches(memberRegisterDto.getPassword(), registered.getPassword())).isTrue();
	}
}