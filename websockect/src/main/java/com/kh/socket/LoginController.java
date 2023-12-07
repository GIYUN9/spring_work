package com.kh.socket;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
	
	@GetMapping(value = "login")
	public void login() {
		
	}
	
	@PostMapping(value = "login")
	public String login(HttpSession session, String nick){
		session.setAttribute("nick", nick);
		return "redirect:/chat";
	}
}
