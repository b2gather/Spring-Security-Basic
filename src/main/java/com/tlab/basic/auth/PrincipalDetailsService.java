package com.tlab.basic.auth;

import com.tlab.basic.domain.entity.Member;
import com.tlab.basic.domain.mapper.MemberMapper;
import com.tlab.basic.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

	private final MemberMapper mapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = this.memberRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Not exist Username"));
		return new PrincipalDetails(mapper.toDto(member));
	}

}
