package com.tlab.basic.auth;

import com.tlab.basic.auth.model.OAuth2GoogleUser;
import com.tlab.basic.auth.model.OAuth2NaverUser;
import com.tlab.basic.auth.model.OAuth2ProviderUser;
import com.tlab.basic.domain.dto.MemberDto;
import com.tlab.basic.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2DetailsService extends DefaultOAuth2UserService {

	private final MemberService memberService;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = this.loadUserFromSuper(userRequest);

		OAuth2ProviderUser providerUser;
		String providerName = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
		switch (providerName) {
			case "GOOGLE" -> providerUser = new OAuth2GoogleUser(oAuth2User);
			case "NAVER" -> providerUser = new OAuth2NaverUser(oAuth2User);
			default -> {
				return oAuth2User;
			}
		}
		MemberDto memberDto = memberService.findByUsername(providerUser.getMemberUsername())
				.orElseGet(() -> memberService.register(providerUser.toMember()));
		return new PrincipalDetails(memberDto, providerUser.getAttributes());
	}

	protected OAuth2User loadUserFromSuper(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		return super.loadUser(userRequest);
	}

}
