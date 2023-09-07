package com.tlab.basic.domain.dto;

import com.tlab.basic.domain.entity.MemberProvider;
import com.tlab.basic.domain.entity.MemberRole;
import lombok.Data;

import java.util.EnumSet;

@Data
public class MemberDto {

	private String username;

	private String nickname;

	private String password;

	private String email;

	private MemberProvider provider;

	private String providerId;

	private EnumSet<MemberRole> roles;

}
