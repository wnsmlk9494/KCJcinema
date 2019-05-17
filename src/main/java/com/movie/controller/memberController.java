package com.movie.controller;

import java.security.PrivateKey;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.movie.domain.MemberDTO;
import com.movie.service.MemberService;
import com.movie.util.RSAutil;
import com.movie.util.SHA256util;

@Controller
public class memberController {
	
	private static final Logger logger = LoggerFactory.getLogger(memberController.class);
	
	@Inject
	private MemberService memberService;
	@Inject
	private RSAutil RSAutil;
	@Inject
	private SHA256util SHA256util;
	
	//회원가입(joinAgree.jsp) - 회원가입 동의 작성 페이지로 이동
	@RequestMapping(value = "/joinAgree.do", method = RequestMethod.GET)
	public String joinAgree(Locale locale, Model model) {
		logger.info("welcome /joinAgree.do !!!");
		
		return "joinAgree";
	}
	
	//회원가입(joinForm.jsp) - 회원가입 양식 작성 페이지로 이동
	@RequestMapping(value = "/joinForm.do", method = RequestMethod.GET)
	public String joinForm(Locale locale, Model model, HttpServletRequest request, HttpSession session) {
		logger.info("welcome /joinForm.do !!!");

		//사전에 생성된 RSA 비밀키가 남아있다면 제거
		session.removeAttribute(RSAutil.RSA_WEB_KEY);
		//RSA 키 생성(공개키, 비밀키)
		RSAutil.initRsa(request);
		
		return "joinForm";
	}
	
	//회원가입(joinForm.jsp) - 아이디 중복체크
	@RequestMapping(value = "/selectAlreadyIdCheck.do", method = RequestMethod.POST)
	@ResponseBody
	public String joinIdCheck(MemberDTO memberDTO, Model model, HttpSession session, HttpServletRequest request) throws Exception {
		logger.info("Welcome /selectAlreadyIdCheck.do !!!!!");
		
		String mberId = request.getParameter("mberId");
		memberDTO.setMberId(mberId);
		
		MemberDTO checkValue = memberService.selectAlreadyIdCheck(memberDTO);
		String alreadyJoin = String.valueOf(checkValue.getCheckValue());
		
		return alreadyJoin;
	}
	
	//회원가입(joinForm.jsp) - 가입된 정보가 있는지에 대한 확인
	@RequestMapping(value = "/selectAlreadyInsertJoin.do", method = RequestMethod.POST)
	@ResponseBody
	public String selectAlreadyInsertJoin(MemberDTO memberDTO, HttpServletRequest request) throws Exception {
		logger.info("welcome /selectAlreadyInsertComment.do !!!");
		
		String mberName = request.getParameter("mberName");
		String mberTel = request.getParameter("mberTel");
		memberDTO.setMberName(mberName);
		memberDTO.setMberTel(mberTel);
		
		//checkValue의 값은 1 또는 0
		MemberDTO checkValue = memberService.selectAlreadyInsertJoin(memberDTO);	
		String alreadyJoin = String.valueOf(checkValue.getCheckValue());
		
		return alreadyJoin;
	}
	
	//회원가입(joinForm.jsp) - 회원가입 양식 저장
	@RequestMapping(value = "/joinFormInsert.do", method = RequestMethod.POST)
	public String joinFormInsert(MemberDTO memberDTO, HttpSession session, HttpServletRequest request) throws Exception {
		logger.info("Welcome /joinFormInsert.do !!!!!");
		
		String mberPwd = request.getParameter("mberPwd");
		
		//세션에서 비밀키를 얻음
		PrivateKey privateKey = (PrivateKey) session.getAttribute(RSAutil.RSA_WEB_KEY);
		
		//RSA 비밀키로 복호화
		mberPwd = RSAutil.decryptRsa(privateKey, mberPwd);
		
		//클라이언트 요청 패스워드 SHA-256 으로 SALT(임의의 문자열)를 이용해 암호화
        String salt = SHA256util.generateSalt();
        mberPwd = SHA256util.getEncrypt(mberPwd, salt);
        
        memberDTO.setMberPwd(mberPwd);
        memberDTO.setSalt(salt);
        
        memberService.InsertJoinForm(memberDTO);

        //비밀키 삭제
		session.removeAttribute(RSAutil.RSA_WEB_KEY);
		
		return "joinSuccess";
	}
	
	//회원가입(joinSuccess.jsp) - 회원가입 완료 페이지로 이동
	@RequestMapping(value = "/joinSuccess.do", method = RequestMethod.GET)
	public String joinSuccess() {
		logger.info("welcome /joinSuccess.do !!!");
		
		return "joinSuccess";
	}
	
	//로그인(loginFormPop.jsp) - 로그인 팝업 펼치기
	@RequestMapping(value = "/loginFormPop.do", method = RequestMethod.GET)
	public String login_form(HttpSession session, HttpServletRequest request) throws Exception {
		logger.info("welcome /loginForm.do !!!");
		
		//사전에 생성된 RSA 비밀키가 남아있다면 제거
		session.removeAttribute(RSAutil.RSA_WEB_KEY);
		//RSA 키 생성(공개키, 비밀키)
		RSAutil.initRsa(request);
		
		return "loginFormPop";
	}
	
	//로그인(loginFormPop.jsp) - 로그인 요청 시 요청값과 DB값과의 true/false 구별 여부 확인
	@RequestMapping(value = "/loginCheck.do", method = RequestMethod.POST)
	@ResponseBody
	public String loginCheck(MemberDTO memberDTO, Model model, HttpSession session, HttpServletRequest request) throws Exception {
		logger.info("Welcome /loginCheck.do !!!!!");
		
		String mberPwd = request.getParameter("mberPwd");
		
		//로그인 결과로 success, fail를 ajax에 전달해주기 위한 변수
		String loginStatus = "";
        
		//요청된 id가 DB에 있는지 검사. 있으면 1 없으면 0
		//0이라면 없는 id이므로 요청된 ajax로 none을 전달
		MemberDTO checkValue = memberService.selectAlreadyIdCheck(memberDTO);
		String checkValueString = String.valueOf(checkValue.getCheckValue());
		if(checkValueString.equals("0")) {
			logger.info("로그인 실패. 해당하는 아이디는 존재하지 않습니다. 요청된 ajax로 'none' 메시지를 전달합니다.");
			loginStatus = "none";
			return loginStatus;
		}
		
		//DB에 요청된 id가 있다면
		//세션에서 비밀키를 얻음
		PrivateKey privateKey = (PrivateKey) session.getAttribute(RSAutil.RSA_WEB_KEY);

		//RSA 비밀키로 복호화
		mberPwd = RSAutil.decryptRsa(privateKey, mberPwd);
		
        //요청된 아이디를 조회하여 가입시 사용했던 salt와 SHA256 암호화된 비밀번호 조회
        MemberDTO loginUser = memberService.selectLoginCheck(memberDTO);
        String already_mberPwd = loginUser.getMberPwd();
        String salt = loginUser.getSalt();
        
        //클라이언트의 로그인 요청 비밀번호에 조회된 salt로 SHA256 암호화
        mberPwd = SHA256util.getEncrypt(mberPwd, salt);
        
        //salt(요청받은비밀번호) 와 DB에 저장된 비밀번호가 같다면 성공
        if(mberPwd.equals(already_mberPwd)){
        	logger.info("로그인 성공. 요청된 ajax로 'success' 메시지를 전달합니다.");
        	loginStatus = "success";
        	
        	//로그인 성공으로 세션에 유저 정보를 등록
        	String mberId = loginUser.getMberId();
            String mberName = loginUser.getMberName();
            String mberBirth = loginUser.getMberBirth();
            String mberSexCode = loginUser.getMberSexCode();
            String mberTel = loginUser.getMberTel();
            String mberEmail = loginUser.getMberEmail();
        	session.setAttribute("session_mberId", mberId);
        	session.setAttribute("session_mberName", mberName);
        	session.setAttribute("session_mberBirth", mberBirth);
        	session.setAttribute("session_mberSexCode", mberSexCode);
        	session.setAttribute("session_mberTel", mberTel);
        	session.setAttribute("session_mberEmail", mberEmail);
        	
        	//비밀키 삭제
    		session.removeAttribute(RSAutil.RSA_WEB_KEY);
        }else{
        	logger.info("로그인 실패. 패스워드가 일치하지 않습니다. 요청된 ajax로 'fail' 메시지를 전달합니다.");
        	loginStatus = "fail";
        }
        
		return loginStatus;
	}
	
	//(top.jsp) - 로그아웃
	@RequestMapping(value = "/logOut.do", method = RequestMethod.GET)
	public String logOut(HttpSession session) {
		logger.info("welcome /logOut.do !!!");
		
		//모든 세션정보 삭제
		session.invalidate();
		
		return "redirect:/boxOffice.do";
	}
	
	//아이디/비밀번호찾기(findIdPw.jsp) - ID/PW 찾기 이동
	@RequestMapping(value = "/findIdPw.do", method = RequestMethod.GET)
	public String findIdPw(Model model, HttpSession session, HttpServletRequest request) {
		logger.info("welcome /findIdPw.do !!!");
		
		String distincCode = request.getParameter("distincCode");
		
		model.addAttribute("distincCode", distincCode);
		
		return "findIdPw";
	}
	
	//아이디/비밀번호찾기(findIdPw.jsp) - ID/PW 찾기 이동
	@RequestMapping(value = "/idSearch.do", method = RequestMethod.POST)
	public String idSearch(MemberDTO memberDTO, Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		logger.info("Welcome /idSearch.do !!!!!");
		
		//이름,전화번호를 받아 DTO에 세팅
		String mberName = request.getParameter("mberName");
		String mberBirth = request.getParameter("mberBirth");
		String mberTel = request.getParameter("mberTel");
		
		logger.info("mberTel" + mberTel);
		
		memberDTO.setMberName(mberName);
		memberDTO.setMberBirth(mberBirth);
		memberDTO.setMberTel(mberTel);
		
		MemberDTO checkValue = memberService.selectAlreadyIdSearchCheck(memberDTO);	
		String alreadyJoin = String.valueOf(checkValue.getCheckValue());
		if(alreadyJoin.equals("1")){
			//조회
			MemberDTO list = memberService.selectIdSearch(memberDTO);
			//조회된 아이디를 JS에 JSON형식으로 code 전달
			String code = list.getMberId();
			model.addAttribute("code", code);
		}else if(alreadyJoin.equals("0")){
			//조회된 아이디가 없을 경우 "none"을 전달
			model.addAttribute("code", "none");
		}
		
		return "cmn/returnJson";
	}
	
	//PW찾기 이벤트 (ID찾기와 같은 작업)
	@RequestMapping(value = "/pwSearch.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String pwSearch(MemberDTO memberDTO, Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		logger.info("Welcome /pwSearch.do !!!!!");
		
		String mberId = request.getParameter("sMberId");
		String mberName = request.getParameter("sMberName");
		String mberBirth = request.getParameter("sMberBirth");
		String mberTel = request.getParameter("sMberTel");
		memberDTO.setMberId(mberId);
		memberDTO.setMberName(mberName);
		memberDTO.setMberBirth(mberBirth);
		memberDTO.setMberTel(mberTel);
		
		MemberDTO checkValue = memberService.selectPwSearch(memberDTO);	
		String alreadyJoin = String.valueOf(checkValue.getCheckValue());
		//등록된 아이디가 있다면 
		if(alreadyJoin.equals("1")){
			//조회된 아이디를 JS에 JSON형식으로 code 전달
			model.addAttribute("code", mberId);
		}else if(alreadyJoin.equals("0")){
			//조회된 아이디가 없을 경우 "none"을 전달
			model.addAttribute("code", "none");
		}
		
		return "cmn/returnJson";
	}
	
	//아이디/비밀번호 찾기(findIdPw.jsp) - PW 변경으로 이동
	@RequestMapping(value = "/modifyPW.do", method = RequestMethod.POST)
	public String modifyPW(MemberDTO memberDTO, Model model, HttpSession session, HttpServletRequest request) throws Exception {
		logger.info("welcome /modifyPW.do !!!");
		
		String mberId = request.getParameter("mberId");
		
		model.addAttribute("mberId", mberId);
		
		//사전에 생성된 RSA 비밀키가 남아있다면 제거
		session.removeAttribute(RSAutil.RSA_WEB_KEY);
		//RSA 키 생성(공개키, 비밀키)
		RSAutil.initRsa(request);
		
		return "modifyPW";
	}
	
	//아이디/비밀번호 찾기(findIdPw.jsp) - PW 변경
	@RequestMapping(value = "/modifyPWUpdate.do", method = RequestMethod.POST)
	public String modifyPWInsert(MemberDTO memberDTO, Model model, HttpSession session, HttpServletRequest request) throws Exception {
		logger.info("welcome /modifyPWUpdate !!!");
		
		String mberId = request.getParameter("mberId"); //결국 findIdPw.jsp로부터 쭉 전달해온거. 검증받은 아이디값.
		String mberPwd = request.getParameter("mberPwd");
		memberDTO.setMberId(mberId);
		
		//세션에서 비밀키를 얻음
		PrivateKey privateKey = (PrivateKey) session.getAttribute(RSAutil.RSA_WEB_KEY);
		
		//RSA 비밀키로 복호화
		mberPwd = RSAutil.decryptRsa(privateKey, mberPwd);
		
		logger.info("복화화된 mberPwd === "+mberPwd);
		
		//클라이언트 요청 패스워드 SHA-256 으로 SALT(임의의 문자열)를 이용해 암호화
		String salt = SHA256util.generateSalt();
		mberPwd = SHA256util.getEncrypt(mberPwd, salt);
		
		memberDTO.setMberPwd(mberPwd);
		memberDTO.setSalt(salt);
		
		memberService.modifyPWUpdate(memberDTO);
		
		String code = "success";
		model.addAttribute("code", code);
		
		//비밀키 삭제
		session.removeAttribute(RSAutil.RSA_WEB_KEY);
		
		return "cmn/returnJson";
	}
}