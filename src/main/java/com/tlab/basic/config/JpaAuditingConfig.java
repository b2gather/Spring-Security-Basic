package com.tlab.basic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing  // 슬라이스 테스트에서 관련 에러를 방지하기 위해 별도 설정을 작성
public class JpaAuditingConfig {
}
