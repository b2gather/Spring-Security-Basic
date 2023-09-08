package com.tlab.basic.domain.dto;

import com.tlab.basic.domain.entity.MemberRole;
import com.tlab.basic.domain.entity.OAuthProvider;
import lombok.Data;

import java.util.EnumSet;

@Data
public class MemberDto {

	private String username;

	private String nickname;

	private String password;

	private String email;

	private OAuthProvider provider;

	private String providerId;

	private EnumSet<MemberRole> roles;

}
