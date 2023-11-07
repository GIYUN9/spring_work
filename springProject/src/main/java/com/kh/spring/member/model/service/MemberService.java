package com.kh.spring.member.model.service;

import com.kh.spring.member.model.vo.Member;

public interface MemberService {
	
	//로그인 서비스(select)
	Member loginMember(Member m);
	
	//회원가입 서비스(insert)
	int insertMember(Member m);
	
	//회원정보 수정 서비스 (update)
	int updateMember(Member m);
	
	//회원탈퇴 서비스 (update로함 [delete])
	int deleteMember(String userId);
	
	//아이디중복체크(select)
	int idCheck(String checkId);
	
	//마이페이지용 selectMember
	Member selectMember(String userId);
}
