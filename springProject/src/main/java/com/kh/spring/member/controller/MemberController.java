package com.kh.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

@Controller // Controller타입의 어노테이션을 빈 스캐닝을 통해 자동으로 빈 등록
public class MemberController {
	
	// private MemberService MemberService = new MemberServiceImpl();
	
	/*
	 * 기존 객체 생성 방식
	 * 객체간의 결합도가 높아진다.(소스코드의 수정이 일어날 경우 하나하나 전부 다 바꿔줘야한다.)
	 * 서비스가 동시에 매우 많이 호출될 경우 그만큼 객체가 생성된다.
	 * 
	 * Spring의 DI(Dependency Injection)를 이용한 방식
	 * 객체를 생성해서 주입해줌
	 * new라는 키워드 없이 선언만해준다. @Autowired어노테이션을 반드시 사용해야한다.
	 * 
	 */
	@Autowired
	private MemberService memberService;
	
	/*
	 * Spring에서 파라미터(요청시 전달값)을 받는 방법
	 * 
	 * 1. HttpServletRequest을 이용해서 전달받기(JSP/Servlet방식)
	 * 		해당 메소드의 매개변수로 HttpServletRequest를 작성해두면
	 * 		스프링컨테이너가 해당 메소드를 호출시자동으로 해당 객체를 생성해서 매개변수로 주입해줌
	 */
	
	/*
	@RequestMapping("/login.me") //RequestMapping타입의 어노테이션을 붙여줌으로 써 HandlerMapping 등록
	public String loginMember(HttpServletRequest request) {
		String userId = request.getParameter("id");
		String userPwd = request.getParameter("pwd");
		
		System.out.println("ID : " + userId);
		System.out.println("PWD : " + userPwd);
		
		return "main";
	}
	*/
	
	
	/*
	 * 2. @RequestParam 어노테이션을 이용하는 방법
	 * request.getParameter("키")로 벨류를 추출하는 대신 해주는 어노테이션
	 * 
	 * value속성의 jsp에서 작성했던 name속성값을 담으면 알아서 해당 매개변수로 받아올 수 있다.
	 * 만약, 넘어온 값이 비어있는 상태라면  defaultValue속성으로 기본값을 지정할 수 있다.
	 * 
	 */
	
	/*
	@RequestMapping("/login.me") //RequestMapping타입의 어노테이션을 붙여줌으로 써 HandlerMapping 등록
	public String loginMember(@RequestParam(value="id", defaultValue = "aaa") String userId,
								@RequestParam(value="pwd") String userPwd) {
		
		System.out.println("ID : " + userId);
		System.out.println("PWD : " + userPwd);
		
		return "main";
	}
	*/
	
	
	/*
	 * 3. @RequestParam 어노테이션을 생략하는 방법
	 * 단, 매개변수명을 jsp의 name속성값(요청시 전달하는 값의 키값)과 동일하게 세팅해줘야 자동으로 값이 주입됨
	 * 위에서 사용했던 defaultValue 추가 속성은 사용할 수 없음
	 */
	
	/*
	@RequestMapping(value = "/login.me")
	public String loginMember(String id, String pwd) {
		
		Member m = new Member();
		m.setUserId(id);
		m.setUserPwd(pwd);
		
		System.out.println("ID : " + m.getUserId());
		System.out.println("PWD : " + m.getUserPwd());
		
		return "main";
	}
	*/
	
	
	/*
	 * 4. 커맨드 객체 방식
	 * 
	 * 해당 메소도의 매개변수로
	 * 요청시 전달값을 담고자하는 VO클래스의 타입을 세팅 후
	 * 요청시 전달값의 키값(jsp의 name속성값)을 vo클래스에 담고자하는 필드명으로 작성
	 */
	
	/*
	@RequestMapping(value = "/login.me")
	public String loginMember(Member m) {

		System.out.println("ID : " + m.getUserId());
		System.out.println("PWD : " + m.getUserPwd());
		
		Member loginUser = memberService.loginMember(m);
		if(loginUser == null) { //로그인실패 => 에러문구를 requestScope에 담고 에러페이지로 포워딩
			System.out.println("로그인 실패");
		}else { // 로그인성공 =>? sessionScope에 로그인유저 담아서 메인으로 url재요청
			System.out.println("로그인 성공");
		}
		
		return "main";
	}
	*/
	
	/*
	 * 요청 처리 후 응답데이터를 담고 응답페이지로 포워딩 또는 url재요청하는 방법
	 * 
	 * 1. 스프링에서 제공하는 Model객체를 이용하는 방법
	 * 포워딩할 응답뷰로 전달하고자하는 데이터를 맵형식(k-v)으로 담을 수 있는 영역
	 * Model객체는 requestScope
	 * *setAttribute가 아니라 addAttribute
	 * 
	 */
	
	/*
	@RequestMapping(value = "/login.me")
	public String loginMember(Member m, Model model, HttpSession session) {

		Member loginUser = memberService.loginMember(m);
		
		
		if(loginUser == null) { //로그인실패 => 에러문구를 requestScope에 담고 에러페이지로 포워딩
			model.addAttribute("errorMsg", "로그인 실패");
			
			// /WEB-INF/views/		/common/errorPage		.jsp
			return "/common/errorPage";
		}else { // 로그인성공 =>? sessionScope에 로그인유저 담아서 메인으로 url재요청
			session.setAttribute("loginUser", loginUser);
			
			return "redirect:/";
		}
		
	}
	*/
	
	/*
	 * 2. 스프링에서 제공하는 ModelAndView 객체를 사용하는 방법
	 * 
	 */
	
	@RequestMapping(value = "/login.me")
	public ModelAndView loginMember(Member m, ModelAndView mv, HttpSession session) {

		Member loginUser = memberService.loginMember(m);
		
		
		if(loginUser == null) { //로그인실패 => 에러문구를 requestScope에 담고 에러페이지로 포워딩
			//model.addAttribute("errorMsg", "로그인 실패");
			mv.addObject("errorMsg", "로그인  실패");
			
			// /WEB-INF/views/		/common/errorPage		.jsp
			mv.setViewName("common/errorPage");
		}else { // 로그인성공 =>? sessionScope에 로그인유저 담아서 메인으로 url재요청
			session.setAttribute("loginUser", loginUser);
			
			mv.setViewName("redirect:/");
		}
		
		return mv;
	}
	
	@RequestMapping(value = "/logout.me")
	public ModelAndView logoutMember(ModelAndView mv, HttpSession session) {
		// session.invalidate();
		session.removeAttribute("loginUser");
		
		mv.setViewName("redirect:/");
		
		return mv;
	}
	
}
