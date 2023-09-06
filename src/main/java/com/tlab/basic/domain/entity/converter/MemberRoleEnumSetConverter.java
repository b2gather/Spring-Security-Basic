package com.tlab.basic.domain.entity.converter;

import com.tlab.basic.domain.entity.MemberRole;
import jakarta.persistence.AttributeConverter;
import org.springframework.util.StringUtils;

import java.util.EnumSet;
import java.util.stream.Collectors;

public class MemberRoleEnumSetConverter implements AttributeConverter<EnumSet<MemberRole>, String> {
	@Override
	public String convertToDatabaseColumn(EnumSet<MemberRole> attribute) {
		if (attribute == null) return null;
		return attribute.stream().map(MemberRole::name).collect(Collectors.joining(","));
	}

	@Override
	public EnumSet<MemberRole> convertToEntityAttribute(String dbData) {
		EnumSet<MemberRole> enumSet = EnumSet.noneOf(MemberRole.class);
		if (StringUtils.hasText(dbData)) {
			for (String s : dbData.split(",")) {
				enumSet.add(MemberRole.valueOf(s));
			}
		}
		return enumSet;
	}
}
