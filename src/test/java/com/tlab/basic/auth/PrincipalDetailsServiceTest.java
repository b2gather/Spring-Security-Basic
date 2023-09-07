package com.tlab.basic.auth;

import com.tlab.basic.domain.entity.Member;
import com.tlab.basic.domain.entity.MemberRole;
import com.tlab.basic.domain.mapper.MemberMapper;
import com.tlab.basic.domain.mapper.MemberMapperImpl;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.EnumSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
class PrincipalDetailsServiceTest {

	private final String username = "username";

	@Mock
	MemberRepository memberRepository;

	@Spy
	MemberMapper mapper = new MemberMapperImpl();

	@InjectMocks
	PrincipalDetailsService principalDetailsService;

	@DisplayName("LoadUserByUsername")
	@Test
	void LoadUserByUsername() {
		// given
		Member member = Member.builder().username(username).password("password").roles(EnumSet.of(MemberRole.ROLE_USER, MemberRole.ROLE_ADMIN)).build();
		when(memberRepository.findByUsername(member.getUsername())).thenReturn(Optional.of(member));

		// when
		UserDetails userDetails = principalDetailsService.loadUserByUsername(username);

		// then
		assertThat(userDetails.getUsername()).isEqualTo(member.getUsername());
		assertThat(userDetails.getPassword()).isNotBlank();
		assertThat(userDetails.getAuthorities()).hasSize(2);
		assertThat(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()).containsOnly("ROLE_USER", "ROLE_ADMIN");
	}

	@DisplayName("LoadUserByUsername | 없는 유저명 -> UsernameNotFoundException")
	@Test
	void LoadUserByUsername_UsernameNotFoundException() {
		// given
		when(memberRepository.findByUsername(any())).thenReturn(Optional.empty());

		// when
		// then
		assertThrows(UsernameNotFoundException.class, () -> principalDetailsService.loadUserByUsername(username));
	}

}