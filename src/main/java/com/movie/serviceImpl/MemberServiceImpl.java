package com.movie.serviceImpl;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.movie.domain.MemberDTO;
import com.movie.service.MemberService;

@Repository
public class MemberServiceImpl implements MemberService{
	
	@Inject
	private SqlSession sqlSession;
	
	private static final String namespace="com.movie.mappers.MemberMapper";
	
//	회원가입(joinForm.jsp) - 회원가입 양식 저장
	@Override
	public void InsertJoinForm(MemberDTO memberDTO) {
		sqlSession.insert(namespace+".insertMember", memberDTO);
	}
	
//	회원가입(joinForm.jsp) - 아이디 중복체크
	@Override
	public MemberDTO selectAlreadyIdCheck(MemberDTO memberDTO) throws Exception{
		return sqlSession.selectOne(namespace+".selectAlreadyIdCheck", memberDTO);
	}
	
//	로그인(loginFormPop.jsp) - 로그인 요청 시 요청값과 DB값과의 true/false 구별 여부 확인
	@Override
	public MemberDTO selectLoginCheck(MemberDTO memberDTO) throws Exception{
		return sqlSession.selectOne(namespace+".selectLoginCheck", memberDTO);
	}
	
//	회원가입(joinForm.jsp) - 가입된 정보가 있는지에 대한 확인
	@Override
	public MemberDTO selectAlreadyInsertJoin(MemberDTO memberDTO) throws Exception{
	return sqlSession.selectOne(namespace+".selectAlreadyInsertJoin", memberDTO);
	}
	
//	아이디/비밀번호 찾기(findIdPw.jsp) - ID 찾기
	@Override
	public MemberDTO selectAlreadyIdSearchCheck(MemberDTO memberDTO) throws Exception{
		return sqlSession.selectOne(namespace+".selectAlreadyIdSearchCheck", memberDTO);
	}
	@Override
	public MemberDTO selectIdSearch(MemberDTO memberDTO) throws Exception{
		return sqlSession.selectOne(namespace+".selectIdSearch", memberDTO);
	}
	
//	아이디/비밀번호 찾기(findIdPw.jsp) - PW 찾기
	@Override
	public MemberDTO selectPwSearch(MemberDTO memberDTO) throws Exception{
		return sqlSession.selectOne(namespace+".selectPwSearch", memberDTO);
	}
	
//	아이디/비밀번호 찾기(findIdPw.jsp) - PW 변경
	@Override
	public void modifyPWUpdate(MemberDTO memberDTO) {
	   sqlSession.update(namespace+".modifyPWUpdate", memberDTO);
	}
}