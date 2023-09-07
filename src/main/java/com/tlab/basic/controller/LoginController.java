package com.tlab.basic.controller;

import com.tlab.basic.domain.dto.LoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

	@GetMapping("")
	public String loginPage(Model model) {
		if (!model.containsAttribute("loginDto")) {
			model.addAttribute("loginDto", new LoginDto());
		}
		return "login";
	}

}
