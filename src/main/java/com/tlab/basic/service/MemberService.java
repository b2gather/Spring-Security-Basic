package com.tlab.basic.service;

import com.tlab.basic.domain.dto.MemberDto;
import com.tlab.basic.domain.dto.MemberRegisterDto;
import com.tlab.basic.domain.dto.OAuthMemberRegisterDto;
import com.tlab.basic.domain.entity.Member;
import com.tlab.basic.domain.mapper.MemberMapper;
import com.tlab.basic.exception.UsernameAlreadyExistException;
import com.tlab.basic.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository repository;

	private final PasswordEncoder passwordEncoder;

	private final MemberMapper mapper;

	public MemberDto register(MemberRegisterDto memberRegisterDto) throws UsernameAlreadyExistException {
		String username = memberRegisterDto.getUsername();
		if (repository.existsByUsername(username)) {
			throw new UsernameAlreadyExistException();
		}
		return register(mapper.toEntity(memberRegisterDto));
	}

	public MemberDto register(OAuthMemberRegisterDto oAuthMemberRegisterDto) {
		return register(mapper.toEntity(oAuthMemberRegisterDto));
	}

	private MemberDto register(Member member) {
		member.encodePassword(passwordEncoder);
		return mapper.toDto(repository.save(member));
	}

	public Optional<MemberDto> findByUsername(String username) {
		return repository.findByUsername(username).map(mapper::toDto);
	}

}
