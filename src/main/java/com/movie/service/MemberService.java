package com.movie.service;

import com.movie.domain.MemberDTO;

public interface MemberService {
	
	//회원가입(joinForm.jsp) - 회원가입 양식 저장
	public void InsertJoinForm(MemberDTO memberDTO);
	
	//회원가입(joinForm.jsp) - 아이디 중복체크
	public MemberDTO selectAlreadyIdCheck(MemberDTO memberDTO) throws Exception;
	
	//로그인(loginFormPop.jsp) - 로그인 요청 시 요청값과 DB값과의 true/false 구별 여부 확인
	public MemberDTO selectLoginCheck(MemberDTO memberDTO) throws Exception;
	
	//회원가입(joinForm.jsp) - 가입된 정보가 있는지에 대한 확인
	public MemberDTO selectAlreadyInsertJoin(MemberDTO memberDTO) throws Exception;
	
	//아이디/비밀번호 찾기(findIdPw.jsp) - ID 찾기
	public MemberDTO selectAlreadyIdSearchCheck(MemberDTO memberDTO) throws Exception;
	public MemberDTO selectIdSearch(MemberDTO memberDTO) throws Exception;
	
	//아이디/비밀번호 찾기(findIdPw.jsp) - PW 찾기
	public MemberDTO selectPwSearch(MemberDTO memberDTO) throws Exception;
	
	//아이디/비밀번호 찾기(findIdPw.jsp) - PW 변경
	public void modifyPWUpdate(MemberDTO memberDTO);
}