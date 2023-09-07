package com.tlab.basic.controller;

import com.tlab.basic.config.SecurityConfig;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@Import(SecurityConfig.class)
abstract class AbstractControllerTest {

	@Autowired
	MockMvc mockMvc;

}