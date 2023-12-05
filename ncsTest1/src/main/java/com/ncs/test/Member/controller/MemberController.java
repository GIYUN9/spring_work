package com.ncs.test.Member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ncs.test.Member.model.service.MemberService;
import com.ncs.test.Member.model.vo.Member;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	
	@RequestMapping(value = "login")
	public String memberLogin(Member m, HttpSession session) {
		
		Member loginUser = memberService.loginMember(m);
		
		if(loginUser != null) {
			session.setAttribute("loginMember", loginUser);
			return "redirect:/";
		}else {
			session.setAttribute("msg", "아이디가 일치하지 않습니다.");
			return "views/errorPage";
		}
		
	}
}
