<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="../js/loginFormPop.js"></script> 
<div class="login">
	<div class="login_main">
		<div class="login_wrap">
			<div class="part_first">Welcome You</div>
			<div class="part_second">
				<form class="data_form" id="loginFrm" name="loginFrm" method="post">
					<div class="inputInfo">
						<input type="text" class="mberId" id="mberId" name="mberId" maxlength="10" placeholder="ID(아이디)" onkeyup="login_enterkey();" style="ime-mode:disabled">
						<input type="password" class="mberPwd" id="mberPwd" name="mberPwd" maxlength="20" placeholder="PW(비밀번호)" onkeyup="login_enterkey();" style="ime-mode:disabled">
					</div>
					<div class="clickLogin">
<!-- 						<a href="#" class="login_btn" onclick="fn_loginCheck();">로그인</a> -->
						<a href="#" class="login_btn" id="loginClick">로그인</a>
					</div>
					<input type="hidden" id="RSAModulus" value="${RSAModulus}"/>
    				<input type="hidden" id="RSAExponent" value="${RSAExponent}"/>
				</form>
			</div>
			<div class="part_third">
				<ul>
					<li class="li_joinMember"><a href="/joinAgree.do" class="joinMember">회원가입</a></li>
					<li class="li_findId"><a href="#" class="findId" onclick="fn_findIdPw('id');">아이디 찾기</a></li>
					<li class="li_findPw"><a href="#" class="findPw" onclick="fn_findIdPw('pw');">비밀번호 찾기</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="part_empty"></div>
</div>