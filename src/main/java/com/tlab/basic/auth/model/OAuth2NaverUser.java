package com.tlab.basic.auth.model;

import com.tlab.basic.domain.entity.OAuthProvider;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class OAuth2NaverUser extends OAuth2ProviderUser {

	private static final String ATTRIBUTE_NAME_OF_NAVER_ID = "id";
	private static final String ATTRIBUTE_NAME_OF_ATTRIBUTE_WRAPPER = "response";

	public OAuth2NaverUser(OAuth2User oAuth2User) {
		super(OAuthProvider.NAVER,
				oAuth2User.getAttribute(ATTRIBUTE_NAME_OF_ATTRIBUTE_WRAPPER),
				ATTRIBUTE_NAME_OF_NAVER_ID);
	}

	@Override
	public String getEmail() {
		return getAttribute("email");
	}

	@Override
	public String getNickname() {
		String nickname = getAttribute("nickname");
		if (nickname == null) {
            return getAttribute("name");
        }
		return nickname;
	}

}
