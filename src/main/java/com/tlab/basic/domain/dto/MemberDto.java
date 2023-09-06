package com.tlab.basic.domain.dto;

import com.tlab.basic.domain.entity.MemberRole;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.EnumSet;

@Data
public class MemberDto {

	private String username;

	private String password;

	private String email;

	private String provider;

	private EnumSet<MemberRole> roles;

	private LocalDateTime createdAt;

}
