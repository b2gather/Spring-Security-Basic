package com.tlab.basic.auth;

import com.tlab.basic.domain.dto.MemberDto;
import com.tlab.basic.domain.dto.OAuthMemberRegisterDto;
import com.tlab.basic.domain.entity.MemberProvider;
import com.tlab.basic.domain.entity.MemberRole;
import com.tlab.basic.domain.mapper.MemberMapper;
import com.tlab.basic.domain.mapper.MemberMapperImpl;
import com.tlab.basic.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
class OAuth2DetailsServiceTest {

	MemberMapper mapper = new MemberMapperImpl();

	@Mock
	MemberService memberService;

	@InjectMocks @Spy
	OAuth2DetailsService service;

	@DisplayName("LoadUser | 구글유저정보취득 -> 기존등록정보없음 -> 멤버등록")
	@Test
	void LoadUser_saveUser() {
		// given
		String registrationId = "google";
		OAuth2User oAuth2User = this.getOAuth2UserOfGoogle();
		String username = this.createUsername(registrationId, oAuth2User);
		OAuth2UserRequest userRequest = this.getOAuth2UserRequestMock(registrationId);

		doReturn(oAuth2User).when(service).loadUserFromSuper(userRequest);
		when(memberService.findByUsername(username))
				.thenReturn(Optional.empty());
		when(memberService.register(any(OAuthMemberRegisterDto.class)))
				.thenAnswer(invocation ->
						mapper.toDto(mapper.toEntity(invocation.<OAuthMemberRegisterDto>getArgument(0)))
				);

		// when
		PrincipalDetails result = (PrincipalDetails) service.loadUser(userRequest);
		MemberDto member = result.getMember();

		// then
		assertThat(result.getUsername()).isEqualTo(username);
		assertThat(result.getName()).isEqualTo(oAuth2User.getName());
		assertThat(result.getAttributes()).isEqualTo(oAuth2User.getAttributes());
		assertThat(member.getUsername()).isEqualTo(username);
		assertThat(member.getNickname()).isEqualTo(oAuth2User.getAttribute("name"));
		assertThat(member.getEmail()).isEqualTo(oAuth2User.getAttribute("email"));
		assertThat(member.getProvider()).isEqualTo(MemberProvider.GOOGLE);
		assertThat(member.getProviderId()).isEqualTo(oAuth2User.getName());
	}

	@DisplayName("LoadUser | 구글유저정보취득 -> 기존등록정보있음 -> 등록정보반환")
	@Test
	void LoadUser_findUser() {
		// given
		String registrationId = "google";
		OAuth2User oAuth2User = this.getOAuth2UserOfGoogle();
		OAuth2UserRequest userRequest = this.getOAuth2UserRequestMock(registrationId);
		String username = this.createUsername(registrationId, oAuth2User);
		MemberDto memberDto = this.getMemberDto(username, oAuth2User);

		doReturn(oAuth2User).when(service).loadUserFromSuper(userRequest);
		when(memberService.findByUsername(username))
				.thenReturn(Optional.of(memberDto));

		// when
		PrincipalDetails result = (PrincipalDetails) service.loadUser(userRequest);

		// then
		assertThat(result.getUsername()).isEqualTo(username);
		assertThat(result.getName()).isEqualTo(oAuth2User.getName());
		assertThat(result.getAttributes()).isEqualTo(oAuth2User.getAttributes());
		assertThat(result.getMember()).isEqualTo(memberDto);
	}

	@DisplayName("LoadUser | 구글이 아님 -> super.loadUser 그대로 리턴")
	@Test
	void LoadUser_notGoogle() {
		// given
		String registrationId = "facebook";
		OAuth2User oAuth2User = this.getOAuth2UserOfGoogle();
		OAuth2UserRequest userRequest = this.getOAuth2UserRequestMock(registrationId);

		doReturn(oAuth2User).when(service).loadUserFromSuper(userRequest);

		// when
		OAuth2User result = service.loadUser(userRequest);

		// then
		assertThat(result).isNotInstanceOf(PrincipalDetails.class);
		assertThat(result).isEqualTo(oAuth2User);
	}

	@DisplayName("LoadUserFromSuper | 테스트를 위해 우회한 메소드")
	@Test
	void LoadUserFromSuper() {
		// then
		assertThrows(IllegalArgumentException.class,
				() -> service.loadUserFromSuper(null));
	}

	private String createUsername(String registrationId, OAuth2User oAuth2User) {
		return registrationId + "_" + oAuth2User.getName();
	}

	private MemberDto getMemberDto(String username, OAuth2User oAuth2User) {
		MemberDto memberDto = new MemberDto();
		memberDto.setUsername(username);
		memberDto.setNickname(oAuth2User.getAttribute("name"));
		memberDto.setEmail(oAuth2User.getAttribute("email"));
		memberDto.setProvider(MemberProvider.GOOGLE);
		memberDto.setProviderId(oAuth2User.getName());
		memberDto.setRoles(EnumSet.of(MemberRole.ROLE_USER));
		return memberDto;
	}

	private OAuth2UserRequest getOAuth2UserRequestMock(String registrationId) {
		OAuth2UserRequest userRequest = Mockito.mock(OAuth2UserRequest.class);
		ClientRegistration clientRegistration = Mockito.mock(ClientRegistration.class);
		when(userRequest.getClientRegistration()).thenReturn(clientRegistration);
		when(clientRegistration.getRegistrationId()).thenReturn(registrationId);
		return userRequest;
	}

	private OAuth2User getOAuth2UserOfGoogle() {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("sub", "123456789");
		attributes.put("name", "구글유저이름");
		attributes.put("email", "test@gmail.com");
		return new DefaultOAuth2User(null, attributes, "sub");
	}

}