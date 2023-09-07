package com.tlab.basic.auth;

import com.tlab.basic.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return MemberUserDetails.of(
				this.memberRepository.findByUsername(username)
						.orElseThrow(() -> new UsernameNotFoundException("Not exist Username"))
		);
	}

}
