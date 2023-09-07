package com.tlab.basic.repository;

import com.tlab.basic.domain.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRepositoryTest extends AbstractRepositoryTest {

	@Autowired
    MemberRepository memberRepository;

	@DisplayName("Save")
	@Test
	void Save() {
		// given
		Member member = Member.builder()
				.username("test")
				.nickname("nickname")
				.email("test@test.com")
				.password("test")
				.build();

		// when
		Member saved = memberRepository.save(member);

		// then
		assertThat(saved.getId()).isNotNull();
		assertThat(saved).isEqualTo(member);
	}

	@DisplayName("FindByUsername | 있는 유저")
	@Test
	void FindByUsername() {
		// given
		Member saved = getSavedMember();

		// when
		Member found = memberRepository.findByUsername(saved.getUsername()).orElseThrow();

        // then
        assertThat(found).isEqualTo(saved);
	}

	@DisplayName("FindByUsername | 없는 유저 -> Optional.isEmpty")
	@Test
	void FindByUsername_notExistUsername() {
		// given
		Member saved = getSavedMember();

		// when
		Optional<Member> found = memberRepository.findByUsername(saved.getUsername() + "notExist");

        // then
        assertThat(found.isEmpty()).isEqualTo(true);
	}

	@DisplayName("ExistsByUsername | 있는 유저명 -> true")
	@Test
	void ExistsByUsername() {
		// given
		Member savedMember = getSavedMember();

		// when
		boolean exists = memberRepository.existsByUsername(savedMember.getUsername());

		// then
		assertThat(exists).isEqualTo(true);
	}

	@DisplayName("ExistsByUsername | 없는 유저명 -> false")
	@Test
	void ExistsByUsername_notExist() {
		// given
		Member savedMember = getSavedMember();

		// when
		boolean exists = memberRepository.existsByUsername(savedMember + "notExist");

		// then
		assertThat(exists).isEqualTo(false);
	}

	private Member getSavedMember() {
		return memberRepository.save(Member.builder()
				.username("savedMember").nickname("nickname").email("test@test.com").password("test").build());
	}

}