package com.tlab.basic.auth;

import com.tlab.basic.domain.entity.Member;
import com.tlab.basic.domain.entity.MemberRole;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

@Setter
public class MemberUserDetails implements UserDetails {

	private final String username;

	private final String password;

	private final Collection<? extends GrantedAuthority> authorities;

	private boolean isAccountNonExpired;

	private boolean isAccountNonLocked;

	private boolean isCredentialsNonExpired;

	private boolean isEnabled;

	public MemberUserDetails(String username, String password, EnumSet<MemberRole> roles) {
		this.username = username;
		this.password = password;
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		for (MemberRole role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.toString()));
		}
		this.authorities = authorities;
		this.isAccountNonExpired = true;
		this.isAccountNonLocked = true;
		this.isCredentialsNonExpired = true;
		this.isEnabled = true;
	}

	public static MemberUserDetails of(Member member) {
		return new MemberUserDetails(member.getUsername(), member.getPassword(), member.getRoles());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
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
