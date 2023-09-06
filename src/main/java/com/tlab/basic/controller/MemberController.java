package com.tlab.basic.controller;

import com.tlab.basic.domain.dto.MemberRegisterDto;
import com.tlab.basic.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public String register(MemberRegisterDto memberRegisterDto, Model model) {
		memberService.register(memberRegisterDto);
		return "redirect:/login";
	}

	@GetMapping("/join")
	@ResponseBody
	public String joinPage() {
		return "join";
	}

	@GetMapping("/joinProc")
	@ResponseBody
	public String joinProcPage() {
		return "회원가입 완료됨!";
	}

}
