package com.kh.ajax.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kh.ajax.model.vo.Member;

@Controller
public class AjaxController {
	
	/**
	 * 1. HttpServletRequest 객체로 응답데이터 응답하기
	 * 2. 응답할 문자열을 데이터로 반환
	 * @throws IOException 
	 */
	/*
	@RequestMapping("ajax1.do")
	public void ajaxMethod1(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		System.out.println(name + " : " + age);
		
		String responseDate = "응답문자열 : " + name + "은 " + age + "살 입니다.";
		
		try {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().print(responseDate);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
	
	/*
	 * HttpServletRequest와 HttpServletResponse를 사용하지 않고 처리하는 방식 
	 */
	
	/*
	@ResponseBody
	@RequestMapping(value = "ajax1.do", produces = "text/html; charset=UTF-8")
	public String ajaxMethod1(String name, int age) {
		System.out.println(name + " : " + age);
		
		String responseDate = "응답문자열 : " + name + "은 " + age + "살 입니다.";
		
		return responseDate;
	}
	*/
	
	/*
	@RequestMapping("ajax1.do")
	public String ajaxMethod1(String name, int age, HttpServletResponse response) throws IOException {
		System.out.println(name + " : " + age);
		
		//데이터를 이름, 나이 따로 분리해서 보내주고 싶지만 한번에 번환이 된다.
//		response.setContentType("text/html; charset=UTF-8");
//		response.getWriter().print(name);
//		response.getWriter().print(age);

		 첫번째 방법
		JSONArray jArr = new JSONArray();
		jArr.add(name); // ['홍길동']
		jArr.add(age); // ['홍길동', 55]
		
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jArr);
		
		
		
		JSONObject jobj = new JSONObject();
		jobj.put("name", name);
		jobj.put("age", age);
		
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jobj);
		
		
		//--------------------여러명의 정보를 보내고 싶을 때
		//김기민 60 임도연 70
		
		JSONArray jArr = new JSONArray();
		
		JSONObject jobj1 = new JSONObject();
		jobj1.put("name", name);
		jobj1.put("age", age);
		jArr.add(jobj1);
		
		JSONObject jobj2 = new JSONObject();
		jobj2.put("name", "김기민");
		jobj2.put("age", "60");
		jArr.add(jobj2);
		
		JSONObject jobj3 = new JSONObject();
		jobj3.put("name", "임도연");
		jobj3.put("age", "70");
		jArr.add(jobj3);

		return jArr.toJSONString();
	}
	*/
	
	@ResponseBody
	@RequestMapping(value = "ajax1.do", produces = "application/json; charset=UTF-8")
	public String ajaxMethod1(String name, int age){
		
		//Member m = new Member("user01", "pass01", name, age, "01022224444");
//		return new Gson().toJson(m);
		ArrayList<Member> list = new ArrayList();
		list.add(new Member("user01", "pass01", name, age, "01022224424"));
		list.add(new Member("user02", "pass02", name, age, "01022224434"));
		list.add(new Member("user03", "pass03", "김개똥", 15, "01022224444"));
		list.add(new Member("user04", "pass04", name, age, "01022224454"));
		list.add(new Member("user05", "pass05", name, age, "01022224464"));
		
		return new Gson().toJson(list);
	}
		
}
