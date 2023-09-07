package com.tlab.basic.auth;

import com.tlab.basic.domain.dto.MemberDto;
import com.tlab.basic.domain.entity.MemberRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Setter
public class PrincipalDetails implements UserDetails, OAuth2User {

	@Getter
	private final MemberDto member;

	private final Collection<? extends GrantedAuthority> authorities;

	private String oAuth2UserName;

	private Map<String, Object> oAuth2UserAttributes = new HashMap<>();

	private boolean isAccountNonExpired;

	private boolean isAccountNonLocked;

	private boolean isCredentialsNonExpired;

	private boolean isEnabled;

	public PrincipalDetails(MemberDto memberDto) {
		this.member = memberDto;
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		for (MemberRole role : member.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.toString()));
		}
		this.authorities = authorities;
		this.isAccountNonExpired = true;
		this.isAccountNonLocked = true;
		this.isCredentialsNonExpired = true;
		this.isEnabled = true;
	}

	public PrincipalDetails(MemberDto memberDto, OAuth2User oAuth2User) {
		this(memberDto);
		this.oAuth2UserName = oAuth2User.getName();
		this.oAuth2UserAttributes = oAuth2User.getAttributes();
	}

	@Override
	public @Nullable String getName() {
		return this.oAuth2UserName;
	}

	@Override
	public <A> A getAttribute(String name) {
		return OAuth2User.super.getAttribute(name);
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.oAuth2UserAttributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.member.getPassword();
	}

	@Override
	public String getUsername() {
		return this.member.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

}