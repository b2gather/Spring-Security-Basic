package com.tlab.basic.repository;

import com.tlab.basic.domain.entity.Member;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {

	Member save(Member member);

	Optional<Member> findByUsername(String username);

}
