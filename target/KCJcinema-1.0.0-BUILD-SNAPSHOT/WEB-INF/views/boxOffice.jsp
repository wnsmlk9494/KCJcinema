<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="kr">
<head>
<%@include file="cmn/common_css_js.jsp"%>
<link href="../css/relateMovieInfo.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="../js/boxOffice.js"></script>
<!-- <meta http-equiv="refresh" content="600"> -->
<title>KCJ CINEMA : box_office</title>
</head>
<body>
	<input type="hidden" id="session_mberId" name="session_mberId" value="${sessionScope.session_mberId}" />
	<div class="layerPopOpen" id="layerPopOpen"></div>
	<div class="mask" id="mask"></div>
	<input type="hidden" id="maskVal" name="maskVal" />
	<%@include file="top.jsp"%>
	<div class="contentWrap">
		<div class="movie_container" id="container">
			<div class="full_wrap">
				<div class="flip_wrapper">
					<div class="movielist_util_wrap"></div>
					<!--boxOffice 영화 리스트 조회 -->
					<ul id="ul_boxOfficeList"></ul>
					<div id="locationMore"></div>
				</div>
			</div>
			<div class="div_morePage" id="div_morePage" style="width:100%; text-align:center;">
				<div class="morePage" id="morePage" style="width:61%; display:inline-block;">
					<button class="btn_morePage" id="moreList" style="display:block; color:#666; width:100%; height:45px; line-height:45px; text-align:center; font-size:18px; background-color:#f9f9f9; border:none;">더보기 +</button>
				</div>
			</div>
		</div>
	</div>
	<%@include file="footer.jsp"%>
</body>
</html>