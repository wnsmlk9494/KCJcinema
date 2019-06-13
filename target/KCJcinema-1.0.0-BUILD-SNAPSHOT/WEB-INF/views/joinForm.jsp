<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<html>
<head>
<%@include file="cmn/common_css_js.jsp"%>
<link href="../css/joinMember.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="../js/joinForm.js"></script>
<meta http-equiv="refresh" content="600">
<title>회원가입 : 정보 작성</title>
</head>
<body>
	<%@include file="cmn/top.jsp"%>
	<div class="bodyWrap">
		<div class="contentsWrap">
			<div class="contents">
				<div class="cont_header">
					<p><font size="4px"><strong>회원가입</strong></font><font size="2px">&nbsp;KCJ CINEMA에 오신 것을 환영합니다.</font></p>
					<br/>
				</div>
				<div class="cont_area">
					<div class="member_join">
						<div class="step_join">
							<img src="images/joinForm.png">
						</div>
						<div class="member_data">
							<h2>회원정보 입력</h2>
							<div class="member_info">
								<div class="table_header">
									<h3>기본정보</h3>
									<div class="info">
										<p><font color="RED">*</font> 표시는 필수 입력 항목입니다.</p>
									</div>
								</div>
								<form id="form_join" name="form_join" method="POST">
								<div class="table_col">
									<table>
										<colgroup>
											<col style="width:23%;">
											<col style="width:77%;">
										</colgroup>
										<tbody>
											<tr class="input">
												<th scope="row" class="mandatory"><font color="RED">*</font> 이름</th>
												<td><div class="input_group">
													<span class="input_txt">
														<input type="text" class="text" id="mberName" name="mberName" placeholder="이름 입력" maxlength="20" style="ime-mode:auto">
													</span>
													<span class="select">
														<input type="hidden" id="mberSexCode" name="mberSexCode"/>
														<select name="mberSex" id="mberSex">
															<option value="M">남</option>
															<option value="W">여</option>
														</select>
													</span>
													</div>
												</td>
											</tr>
											<tr class="input">
												<th scope="row" class="mandatory"><font color="RED">*</font> 아이디</th>
												<td><div class="input_group">
														<span class="input_txt">
															<input type="text" class="text" placeholder="아이디 입력" name="mberId" id="mberId" maxlength="10" style="ime-mode:disabled"> 
														</span>
														<button type="button" class="btn btn_search" onclick="fn_idCheck();">중복확인</button>
													</div>
													<p class="msg_desc">아이디는 영문, 숫자의 조합으로 5~10자리까지 가능합니다.</p>
												</td>
											</tr>
											<tr class="input">
												<th scope="row" class="mandatory"><font color="RED">*</font> 비밀번호</th>
												<td><div class="input_group">
														<span class="input_txt"><input type="password" class="text" placeholder="비밀번호 입력" name="mberPwd" id="mberPwd" maxlength="20" style="ime-mode:disabled"></span>
													</div>
													<input type="hidden" id="RSAModulus" value="${RSAModulus}"/>
        											<input type="hidden" id="RSAExponent" value="${RSAExponent}"/>
													<p class="msg_desc">비밀번호는 10~20자리까지 가능합니다.</p>
												</td>
											</tr>
											<tr class="input">
												<th scope="row" class="mandatory"><font color="RED">*</font> 비밀번호 확인</th>
												<td><div class="input_group">
														<span class="input_txt">
															<input type="password" class="text" placeholder="비밀번호 재입력" name="mberPwdCheck"  id="mberPwdCheck" maxlength="20" style="ime-mode:disabled">
														</span>
													</div>
												</td>
											</tr>
											<tr class="input">
												<th scope="row" class="mandatory"><font color="RED">*</font> 생년월일</th>
												<td><div class="input_group">
														<span class="input_txt">
															<input type="text" class="text" placeholder="생년월일 입력" id="mberBirth" name="mberBirth" maxlength="8" style="ime-mode:disabled">
														</span>
														<p class="msg_desc">생년월일 8자리 ex) 19940928</p>
													</div>
												</td>
											</tr>
											<tr class="input">
												<th scope="row" class="mandatory"><font color="RED">*</font> 휴대전화</th>
												<td><div class="input_group">
														<span class="select">
															<select name="mberTel1" id="mberTel1">
																<option value="010">010</option>
																<option value="011">011</option>
																<option value="016">016</option>
																<option value="017">017</option>
																<option value="019">019</option>
															</select>
														</span>
														<span class="symbol">-</span>
														<span class="input_txt">
															<input type="text" placeholder="1111" class="text tel" id="mberTel2" name="mberTel1" maxlength="4" style="ime-mode:disabled">
														</span>
														<span class="symbol">-</span>
														<span class="input_txt">
															<input type="text" placeholder="2222" class="text tel" id="mberTel3" name="mberTel2" maxlength="4" style="ime-mode:disabled">
														</span>
														<input type="hidden" id="mberTel" name="mberTel"/>
													</div>
													<p class="msg_desc">휴대전화 11자리 ex) 01011112222</p>
												</td>
											</tr>
											<tr class="input">
												<th scope="row" class="mandatory"><font color="RED">*</font> 이메일</th>
												<td><div class="email_write">
														<span class="input_txt" data-skin="form">
															<input type="text" class="text small" title="이메일 아이디 입력" name="mberEmail1" id="mberEmail1" placeholder="이메일 아이디" maxlength="25" style="ime-mode:disabled">
														</span>
														<span class="symbol">@</span>
														<span class="input_txt">
															<input type="text" class="text small" tithile="이메일 도메인 입력" name="mberEmail2" id="mberEmail2" placeholder="이메일 도메인" maxlength="25" style="ime-mode:disabled">
															<input type="hidden" id="mberEmail" name="mberEmail"/>
														</span>
														<span class="select" data-skin="form">
														<select title="이메일 도메인 선택" name="mberEmailOpt" id="mberEmailOpt" data-control="emailSelector">
															<option value="0">직접입력</option>
															<option value="naver.com">naver.com</option>
															<option value="hanmail.net">hanmail.net</option>
															<option value="nate.com">nate.com</option>
															<option value="gmail.com">gmail.com</option>
														</select>
														</span>
													</div>
													<p class="msg_desc">이메일 주소 입력 시 사용 가능 특수 문자 : - . _</p>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
								</form>
							</div>
							<div class="btn_sec">
								<button type="button" class="btn" id="btn_form_cancle">취소</button>
								<button type="button" class="btn btn_em" id="btn_success">등록</button>
							</div>
							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 로딩바 및 이미지 -->
	<%@include file="cmn/progress_loading.jsp"%>
	<%@include file="cmn/footer.jsp"%>
</body>
</html>