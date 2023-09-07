package com.tlab.basic.auth;

import com.tlab.basic.auth.PrincipalDetails;
import com.tlab.basic.domain.dto.MemberDto;
import com.tlab.basic.domain.dto.OAuthMemberRegisterDto;
import com.tlab.basic.domain.entity.MemberProvider;
import com.tlab.basic.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2DetailsService extends DefaultOAuth2UserService {

	private final MemberService memberService;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = this.loadUserFromSuper(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		if (registrationId.equals("google")) {
			String oAuth2UserName = oAuth2User.getName();
			String username = registrationId + "_" + oAuth2UserName;

			MemberDto memberDto;
			Optional<MemberDto> memberDtoOptional = memberService.findByUsername(username);
			if (memberDtoOptional.isPresent()) {
				memberDto = memberDtoOptional.get();
			} else {
				OAuthMemberRegisterDto oAuthMemberRegisterDto = OAuthMemberRegisterDto.builder()
						.username(username)
						.nickname(oAuth2User.getAttribute("name"))
						.password(oAuth2UserName)
						.email(oAuth2User.getAttribute("email"))
						.provider(MemberProvider.valueOf(registrationId.toUpperCase()))
						.providerId(oAuth2UserName)
						.build();
				memberDto = memberService.register(oAuthMemberRegisterDto);
			}
			return new PrincipalDetails(memberDto, oAuth2User);
		}
		return oAuth2User;
	}

	protected OAuth2User loadUserFromSuper(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		return super.loadUser(userRequest);
	}

}
