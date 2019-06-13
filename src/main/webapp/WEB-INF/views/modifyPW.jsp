<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<input type="hidden" id="RSAModulus" value="${RSAModulus}"/>
<input type="hidden" id="RSAExponent" value="${RSAExponent}"/>
<div class="idContents" id="idContents">
	<p class="tit">정보를 정확히 입력해주시기 바랍니다.</p>
	<div class="findPwSec">
	<form id="form_modifyPw" name="form_modifyPw" method="post">
		<input type="hidden" id="mberId" name="mberId" value="${mberId}"/>
		<table class="findPwInput">
			<tr>
				<td class="td"><p>새 비밀번호</p></td>
				<td><input class="inputTxt2" name="mberPwd" id="mberPwd" type="password" placeholder="새 비밀번호를  입력하세요." style="ime-mode:disabled"></td>
			</tr>
			<tr>
				<td class="td"><p>비밀번호 확인</p></td>
				<td><input class="inputTxt2" name="mberPwdCheck" id="mberPwdCheck" type="password" placeholder="비밀번호 확인을 입력하세요." style="ime-mode:disabled"></td>
			</tr>
		</table>
	</form>	
		<div class="search">
			<a href="#" class="btn_search" onclick="fn_modifyPw();">확인</a>
		</div>
	</div>
</div>