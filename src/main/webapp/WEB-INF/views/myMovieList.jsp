<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<%@include file="cmn/common_css_js.jsp"%>
<link href="../css/myMovieList.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="../js/myMovieList.js"></script>
<meta http-equiv="refresh" content="600">
<title>KCJ CINEMA : 예매내역</title>
</head>
<body>
	<%@include file="cmn/top.jsp"%>
	<div class="myMovieList">
		<div class="contentsWrap">
			<div class="contents">
				<div class="cont_header">
					<p><font size="4px"><strong>나의 예매내역</strong></font> <font size="2px">&nbsp;예매내역을 확인하실 수 있습니다.</font></p>
					<br/>
				</div>
				<div class="cont_footer">
					<p><font size="2px" style="color:RED">* 예매 취소는 영화 상영 20분 전까지만 가능합니다.</font></p>
					<br/>
				</div>
				<!-- 영화 리스트 조회 -->
				<div id="div_total_wrap_List"></div>
				<div class="div_morePage" id="div_morePage">
					<div class="morePage" id="morePage">
						<button class="btn_morePage" id="moreList">더보기 +</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 로딩바 및 이미지 -->
	<div id="Progress_Loading">
		<img src="../images/Progress_Loading.gif"/>
	</div>
	<!-- 로딩바 및 이미지 -->
	<%@include file="cmn/progress_loading.jsp"%>
	<%@include file="cmn/footer.jsp"%>
</body>
</html>