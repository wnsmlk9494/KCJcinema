<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<%@include file="cmn/common_css_js.jsp"%>
<link href="../css/joinMember.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="../js/joinAgree.js"></script>
<meta http-equiv="refresh" content="600">
<title>회원가입 : 약관 동의</title>
</head>
<body>
	<%@include file="cmn/top.jsp"%>
	<div class="bodyWrap">
		<div class="contentsWrap">
			<div class="contents">
				<div class="cont_header">
					<p><font size="4px"><strong>회원가입</strong></font> <font size="2px">&nbsp;KCJ CINEMA에 오신 것을 환영합니다.</font></p>
					<br/>
				</div>
				<div class="cont_area">
					<div class="member_join">
						<div class="step_join">
							<img src="images/joinAgree.png"/>
						</div>
						<div class="agreement_sec">
							<div class="box_member">
								<h2 class="h2_tit">KCJ CINEMA 서비스 이용약관 및 정보 활용 동의</h2>
								<div class="agreement_clause">
									<div class="item_box_case">
										<h3 class="h5_tit">개인정보 수집 및 활용 동의 (필수)</h3>
										<div class="clause_box">
											<div class="polViewnew">
												<table class="table_agreement">
													<thead>
														<tr>
															<th>수집 목적</th>
															<th>수집 항목</th>
															<th>보유 기간</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td>멤버십 서비스 제공, 본인 확인 및 가입 의사 확인, 만 14세 미만 회원의 경우 회원가입 처리를 위한 법정 대리인 동의 여부 확인 및 추후 법정 대리인에 대한 본인 확인, 회원에 대한 고지 사항 전달, 통합 ID 관리, 카드발급, 포인트 적립 사용 정산, 고객센터 운영, 불량회원 부정이용 방지 및 비인가 사용 방지, VIP 서비스 제공</td>
															<td>아이핀 CI, 이름, 생년월일, 성별, 아이디, 비밀번호, 휴대전화번호, 이메일주소, 카드번호 및 카드 비밀번호 , 만 14세 미만 회원의 법정대리인 본인 확인 정보(이름, 아이핀 CI)</td>
															<td>회원 탈퇴 후 30일 까지</td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
										<p class="stxt">이용자는 개인정보 수집 및 활용(필수)에 대한 동의를 거부할 권리가 있으나, <strong style="color:#ee6900; font-weight:normal;">미 동의 시 회원가입을 하실 수 없습니다.</strong></p>
										<div class="radio_agreement">
											<span class="radio_btn">
												<input type="radio" id="disagree" name="agreeornot" value="notagree" checked="checked"/><label for="disagree">미동의</label>
											</span>
											<span class="radio_btncase">
												<input type="radio" id="agree" name="agreeornot" value="agree"/><label for="agree">동의</label>
											</span>
											<input name="effchar1" title="개인정보 수집 및 활용 동의" id="1" type="hidden" value="Y"/>
										</div>
									</div>
								</div>
							</div>
							<div class="btn_sec_col2">
								<button class="btn" id="btn_agree_cancle" type="button">취소</button>
								<button class="btn_em" id="btn_form" type="button">확인</button>
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