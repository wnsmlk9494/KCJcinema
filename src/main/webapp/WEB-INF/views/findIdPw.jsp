<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<html lang="kr">
<head>
<%@include file="cmn/common_css_js.jsp"%>
<link href="../css/findAccount.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="../js/findIdPw.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0, target-densityDpi=midium-dpi">
<meta http-equiv="refresh" content="600">
<title>아이디/비밀번호 찾기</title>
</head>
<body>
	<div class="popupFindAct" id="popupFindAct">
	<input type="hidden" id="distincCode" name="distincCode" value="${distincCode}"/>
		<ul class="findId">
			<li class="selectedId" id="id">
				<a href="#">아이디 찾기</a>
			</li>
			<li class="selectPw" id="pw">
				<a href="#">비밀번호 찾기</a>
			</li>
		</ul>
		<div class="idContents" id="idContents" style="display:block;">
			<p class="tit">정보를 정확히 입력해주시기 바랍니다.</p>
			<div class="findPwSec">
			<form id="idSearch" name="idSearch" method="post">
				<table class="findPwInput">
					<tr>
						<td class="td"><p>이름</p></td>
						<td><input class="inputTxt2" name="mberName" id="mberName" type="text" placeholder="이름을  입력하세요." maxlength="10" style="ime-mode:auto"></td>
					</tr>
					<tr>
						<td class="td"><p>생년월일</p></td>
						<td><input class="inputTxt2" name="mberBirth" id="mberBirth" type="text" placeholder="ex) 19940928" maxlength="8" style="ime-mode:disabled"></td>
					</tr>
					<tr>
						<td class="td"><p>휴대전화</p></td>
						<td><input class="inputTxt2" name="mberTel" id="mberTel" type="text" placeholder="ex) 010-1234-5678" maxlength="13" onkeyup="fn_autoHypenTel(this);" style="ime-mode:disabled"></td>
					</tr>
				</table>
				</form>	
				<div class="search">
					<a href="#" class="btn_close" onclick="fn_searchClose();">닫기</a>
					<a href="#" class="btn_search" onclick="fn_idSearch();">검색</a>
				</div>
			</div>
		</div>
		<div class="pwContents" id="pwContents" style="display:none;">
			<p class="tit">정보를 정확히 입력해주시기 바랍니다.</p>
			<div class="findPwSec">
			<form id="pwSearch" name="pwSearch" method="post">
				<table class="findPwInput">
					<tr>
						<td class="td"><p>아이디</p></td>
						<td><input class="inputTxt2" name="sMberId" id="sMberId" type="text" placeholder="아이디를  입력하세요." maxlength="10" style="ime-mode:disabled"></td>
					</tr>
					<tr>
						<td class="td"><p>이름</p></td>
						<td><input class="inputTxt2" name="sMberName" id="sMberName" type="text" placeholder="이름을  입력하세요." maxlength="10" style="ime-mode:auto"></td>
					</tr>
					<tr>
						<td class="td"><p>생년월일</p></td>
						<td><input class="inputTxt2" name="sMberBirth" id="sMberBirth" type="text" placeholder="ex) 19940928" maxlength="8" style="ime-mode:disabled"></td>
					</tr>
					<tr>
						<td class="td"><p>휴대전화</p></td>
						<td><input class="inputTxt2" name="sMberTel" id="sMberTel" type="text" placeholder="ex) 010-1234-5678" maxlength="13" onkeyup="fn_autoHypenTel(this);" style="ime-mode:disabled"></td>
					</tr>
				</table>
				</form>	
				<div class="search">
					<a href="#" class="btn_close" onclick="fn_searchClose();">닫기</a>
					<a href="#" class="btn_search" onclick="fn_pwSearch();">검색</a>
				</div>
			</div>
		</div>
	</div>
	<!-- 로딩바 및 이미지 -->
	<div id="Progress_Loading">
		<img src="../images/Progress_Loading.gif"/>
	</div>
</body>
</html>