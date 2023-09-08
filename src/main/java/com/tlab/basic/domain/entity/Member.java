package com.tlab.basic.domain.entity;

import com.tlab.basic.domain.entity.converter.MemberRoleEnumSetConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.EnumSet;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AbstractAtEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String email;

	@Enumerated(EnumType.STRING)
	private OAuthProvider provider;

	private String providerId;

	@Convert(converter = MemberRoleEnumSetConverter.class)
	private EnumSet<MemberRole> roles = EnumSet.of(MemberRole.ROLE_USER);

	@Builder
	private Member(String username, String password, String nickname, String email, OAuthProvider provider, String providerId, EnumSet<MemberRole> roles) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.email = email;
		this.provider = provider;
		this.providerId = providerId;
		this.roles = roles;
	}

	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}

}
