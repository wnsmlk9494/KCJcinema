<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<%@include file="cmn/common_css_js.jsp"%>
<link href="../css/joinMember.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="../js/joinSuccess.js"></script>
<meta http-equiv="refresh" content="600">
<title>회원가입 : 가입 완료</title>
</head>
<body>
	<%@include file="top.jsp"%>
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
						<img src="images/joinComplete.png">
					</div>
					<div class="agreement_sec">
						<div class="box_member">
							<div class="item_box case">
								<p class="s_txt"><font size="5"><strong class="em">KCJ CINEMA 회원가입을 축하합니다!</strong></font></p>
								<br/>
								<p class="s_txt"><font size="3.5">온라인 티켓 예매, 멤버쉽 포인트 적립 등의 서비스를 이용하실 수 있습니다.</font></p>
							</div>
						</div>
						<div class="btn_sec col2" style="">
							<button class="btn" id="btn_home" type="button">메인 화면으로</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	<%@include file="footer.jsp"%>
</body>
</html>