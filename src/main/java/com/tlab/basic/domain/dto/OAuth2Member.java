package com.tlab.basic.domain.dto;

import com.tlab.basic.domain.entity.MemberRole;
import com.tlab.basic.domain.entity.OAuthProvider;
import lombok.*;

import java.util.EnumSet;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class OAuth2Member {

	private String username;

	private String nickname;

	private String email;

	private String password;

	private OAuthProvider provider;

	private String providerId;

	@Builder.Default
	private EnumSet<MemberRole> roles = EnumSet.of(MemberRole.ROLE_USER);

}
