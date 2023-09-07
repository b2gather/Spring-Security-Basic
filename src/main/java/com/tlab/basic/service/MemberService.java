package com.tlab.basic.service;

import com.tlab.basic.domain.dto.MemberDto;
import com.tlab.basic.domain.dto.MemberRegisterDto;
import com.tlab.basic.domain.entity.Member;
import com.tlab.basic.domain.mapper.MemberMapper;
import com.tlab.basic.exception.UsernameAlreadyExistException;
import com.tlab.basic.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	private final PasswordEncoder passwordEncoder;

	private final MemberMapper mapper;

	public MemberDto register(MemberRegisterDto memberRegisterDto) throws UsernameAlreadyExistException {
		if (memberRepository.existsByUsername(memberRegisterDto.getUsername())) {
			throw new UsernameAlreadyExistException();
		}
		Member member = mapper.toEntity(memberRegisterDto);
		member.encodePassword(passwordEncoder);
		return mapper.toDto(memberRepository.save(member));
    }

}
