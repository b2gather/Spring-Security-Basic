package com.tlab.basic.service;

import com.tlab.basic.domain.dto.MemberDto;
import com.tlab.basic.domain.dto.MemberRegisterDto;
import com.tlab.basic.domain.entity.Member;
import com.tlab.basic.domain.mapper.MemberMapper;
import com.tlab.basic.domain.mapper.MemberMapperImpl;
import com.tlab.basic.exception.UsernameAlreadyExistException;
import com.tlab.basic.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
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

	@DisplayName("FindByUsername")
	@Test
	void FindByUsername() {
		// given
		when(memberRepository.findByUsername(any()))
				.thenReturn(Optional.ofNullable(Member.builder().build()));

		// when
		Optional<MemberDto> memberDtoOptional = memberService.findByUsername("exist username");

		// then
		assertThat(memberDtoOptional.isPresent()).isTrue();
	}

	@DisplayName("FindByUsername | 없는 유저명 -> Optional.isEmpty")
	@Test
	void FindByUsername_empty() {
		// given
		when(memberRepository.findByUsername(any())).thenReturn(Optional.empty());

		// when
		Optional<MemberDto> memberDtoOptional = memberService.findByUsername("non exist username");

		// then
		assertThat(memberDtoOptional.isEmpty()).isTrue();
	}

	private MemberRegisterDto getMemberRegisterDto() {
		MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
		memberRegisterDto.setUsername("John");
		memberRegisterDto.setPassword("<PASSWORD>");
		memberRegisterDto.setEmail("<EMAIL>");
		return memberRegisterDto;
	}
}