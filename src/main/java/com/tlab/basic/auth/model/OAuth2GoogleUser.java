package com.tlab.basic.auth.model;

import com.tlab.basic.domain.entity.OAuthProvider;
import lombok.Getter;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
public class OAuth2GoogleUser extends OAuth2ProviderUser {

	public OAuth2GoogleUser(OAuth2User oAuth2User) {
		super(OAuthProvider.GOOGLE, oAuth2User.getName(), oAuth2User.getAttributes());
	}

	@Override
	public String getEmail() {
		return getAttribute("email");
	}

	@Override
	public String getNickname() {
		return getAttribute("name");
	}

}
