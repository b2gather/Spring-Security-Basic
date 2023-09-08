package com.tlab.basic.auth.model;

import com.tlab.basic.domain.dto.OAuth2Member;
import com.tlab.basic.domain.entity.OAuthProvider;
import lombok.Getter;

import java.util.Map;

@Getter
public abstract class OAuth2ProviderUser {

	private final OAuthProvider provider;

	private final String providerId;

	private final Map<String, Object> attributes;

	public OAuth2ProviderUser(OAuthProvider oAuthProvider, String providerId, Map<String, Object> attributes) {
		this.provider = oAuthProvider;
		this.providerId = providerId;
		this.attributes = attributes;
	}

	public OAuth2ProviderUser(OAuthProvider oAuthProvider, Map<String, Object> attributes, String idAttributeName) {
		this.provider = oAuthProvider;
		this.attributes = attributes;
		this.providerId = getAttribute(idAttributeName);
	}

	public OAuth2Member toMember() {
		return OAuth2Member.builder()
				.username(getMemberUsername())
				.nickname(getNickname())
				.password(getProviderId())
				.email(getEmail())
				.provider(getProvider())
				.providerId(getProviderId())
				.build();
	}

	public abstract String getEmail();

	public abstract String getNickname();

	public String getMemberUsername() {
		return this.provider.name() + "_" + this.providerId;
	}

	@SuppressWarnings("unchecked")
	public <A> A getAttribute(String name) {
		return (A) attributes.get(name);
	}

}
