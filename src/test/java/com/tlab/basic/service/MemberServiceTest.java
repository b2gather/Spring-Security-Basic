package com.tlab.basic.service;

import com.tlab.basic.domain.dto.MemberDto;
import com.tlab.basic.domain.dto.MemberRegisterDto;
import com.tlab.basic.domain.entity.Member;
import com.tlab.basic.domain.mapper.MemberMapper;
import com.tlab.basic.domain.mapper.MemberMapperImpl;
import com.tlab.basic.exception.UsernameAlreadyExistException;
import com.tlab.basic.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

	@DisplayName("Register")
	@Test
	void Register() {
		// given
		MemberRegisterDto memberRegisterDto = getMemberRegisterDto();
		when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> {
			Member member = invocation.getArgument(0);
			ReflectionTestUtils.setField(member, "id", 1L);
			return member;
		});

		// when
		MemberDto registered = memberService.register(memberRegisterDto);

		// then
		assertThat(registered.getPassword()).isNotEqualTo(memberRegisterDto.getPassword());
		assertThat(passwordEncoder.matches(memberRegisterDto.getPassword(), registered.getPassword())).isTrue();
	}

	@DisplayName("Register | 존재하는 유저명 -> UsernameAlreadyExistException")
	@Test
	void Register_() {
		// given
		MemberRegisterDto memberRegisterDto = getMemberRegisterDto();
		when(memberRepository.existsByUsername(any())).thenReturn(true);

		// when
		// then
		assertThrows(UsernameAlreadyExistException.class, () ->
				memberService.register(memberRegisterDto)
		);
	}

	private MemberRegisterDto getMemberRegisterDto() {
		MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
		memberRegisterDto.setUsername("John");
		memberRegisterDto.setPassword("<PASSWORD>");
		memberRegisterDto.setEmail("<EMAIL>");
		return memberRegisterDto;
	}

}