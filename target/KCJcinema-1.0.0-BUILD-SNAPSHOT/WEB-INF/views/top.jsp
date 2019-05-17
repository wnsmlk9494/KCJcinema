<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="../css/joinMember.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="../js/top.js"></script>
<div class="header">
	<div class="header_first">
		<div class="loginPopOpen" id="loginPopOpen"></div>
		<ul>
			<!-- 세션에서 id값을 조회 -->
			<c:if test="${empty sessionScope.session_mberId}">
				<li><a href="/joinAgree.do" style="margin-right: 50px;">회원가입</a> </li>
				<li><a href="#" onclick="fn_goLogin();">로그인</a></li>
			</c:if>
			<c:if test="${not empty sessionScope.session_mberId}">
				<li><a href="/logOut.do" style="margin-right: 50px;">로그아웃</a></li>
				<li><a href="/myMovieList.do">나의 예매내역</a></li>
				<li><strong>${sessionScope.session_mberName}님 환영합니다</strong></li>
			</c:if>
		</ul>
	</div>
	<div class="header_second">
		<h1><a href="/boxOffice.do">KCJ CINEMA</a></h1>
		<div class="second_menu">
			<div class="menu"><a href="#" id="boxOffice" onclick="fn_movieList('box');">BOX_OFFICE</a></div>
			<div class="menu"><a href="#" id="showingMovie" onclick="fn_movieList('showing');">SHOWING_MOVIE</a></div>
			<div class="menu"><a href="#" id="soonMovie" onclick="fn_movieList('soon');">SOON_MOVIE</a></div>
		</div>
	</div>
</div>