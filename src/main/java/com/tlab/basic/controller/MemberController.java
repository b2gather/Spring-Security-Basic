package com.tlab.basic.controller;

import com.tlab.basic.domain.dto.MemberRegisterDto;
import com.tlab.basic.exception.UsernameAlreadyExistException;
import com.tlab.basic.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/register")
	public String registerPage(Model model) {
		if (!model.containsAttribute("memberRegisterDto")) {
			model.addAttribute("memberRegisterDto", new MemberRegisterDto());
		}
		return "members/register";
	}

	@PostMapping("")
	public String register(MemberRegisterDto memberRegisterDto, BindingResult bindingResult, HttpServletResponse response) {
		try {
			memberService.register(memberRegisterDto);
		} catch (UsernameAlreadyExistException e) {
			bindingResult.rejectValue("username", "AlreadyExist", "Username already exist");
			response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            return "members/register";
		}
		return "redirect:/";
	}

}
