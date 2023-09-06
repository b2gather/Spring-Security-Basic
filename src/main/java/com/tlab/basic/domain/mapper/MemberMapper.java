package com.tlab.basic.domain.mapper;

import com.tlab.basic.domain.dto.MemberDto;
import com.tlab.basic.domain.dto.MemberRegisterDto;
import com.tlab.basic.domain.entity.Member;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MemberMapper {

	@Mapping(target = "roles", ignore = true)
	@Mapping(target = "provider", ignore = true)
	Member toEntity(MemberRegisterDto dto);

	MemberDto toDto(Member member);

}
