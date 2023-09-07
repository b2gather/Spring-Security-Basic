package com.tlab.basic.repository;

import com.tlab.basic.domain.entity.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class MemberRepositoryTest {

	@Autowired
    MemberRepository memberRepository;

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	@DisplayName("Save")
	@Test
	void Save() {
		// given
		Member member = Member.builder()
				.username("test")
				.email("test@test.com")
				.password("test")
				.build();

		// when
		Member saved = memberRepository.save(member);

		// then
		assertThat(saved.getId()).isNotNull();
	}

	@DisplayName("FindByUsername")
	@Test
	void FindByUsername() {
		// given
		Member member = Member.builder().username("test").email("test@test.com").password("test").build();
		Member saved = memberRepository.save(member);

		// when
		Member found = memberRepository.findByUsername(saved.getUsername()).orElseThrow();

        // then
        assertThat(found).isEqualTo(saved);
	}

	@DisplayName("FindByUsername | 없는 유저명 -> Optional.isEmpty")
	@Test
	void FindByUsername_notExistUsername() {
		// given
		Member member = Member.builder().username("test").email("test@test.com").password("test").build();
		Member saved = memberRepository.save(member);

		// when
		Optional<Member> found = memberRepository.findByUsername(saved.getUsername() + "notExist");

        // then
        assertThat(found.isEmpty()).isEqualTo(true);
	}

}