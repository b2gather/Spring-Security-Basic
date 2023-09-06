package com.tlab.basic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

	@GetMapping({"", "/"})
	public String indexPage() {
		return "index";
	}

	@GetMapping("/user")
	@ResponseBody
	public String userPage() {
        return "user";
    }

	@GetMapping("/admin")
	@ResponseBody
	public String adminPage() {
        return "admin";
    }

	@GetMapping("/manager")
	@ResponseBody
	public String managerPage() {
        return "manager";
    }

}
