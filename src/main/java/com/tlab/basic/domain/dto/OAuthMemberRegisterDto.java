package com.tlab.basic.domain.dto;

import com.tlab.basic.domain.entity.MemberProvider;
import com.tlab.basic.domain.entity.MemberRole;
import lombok.*;

import java.util.EnumSet;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class OAuthMemberRegisterDto {

	private String username;

	private String nickname;

	private String email;

	private String password;

	private MemberProvider provider;

	private String providerId;

	@Builder.Default
	private EnumSet<MemberRole> roles = EnumSet.of(MemberRole.ROLE_USER);

}
