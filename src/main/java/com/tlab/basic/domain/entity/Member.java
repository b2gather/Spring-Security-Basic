package com.tlab.basic.domain.entity;

import com.tlab.basic.domain.entity.converter.MemberRoleEnumSetConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.EnumSet;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String email;

	private String provider;

	@Convert(converter = MemberRoleEnumSetConverter.class)
	private EnumSet<MemberRole> roles = EnumSet.of(MemberRole.ROLE_USER);

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	@Builder
	private Member(String username, String password, String email, String provider, EnumSet<MemberRole> roles) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.provider = provider;
		if (roles != null) this.roles = roles;
	}

	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}

}
