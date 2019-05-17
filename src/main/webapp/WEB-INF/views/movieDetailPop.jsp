<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="../js/movieDetailPop.js"></script>
<div class="modal_pop">
	<div class="btn_close">
			<button type="button" class="closeMovieDetailPop" id="closeMovieDetailPop"></button>
		</div>
	<div class="movieDetailPop" id="layerPop">
		<div class="contents">
			<input type="hidden" id="hidden_movieNum" name="hidden_movieNum" value="${movieNum}"/>
			<input type="hidden" id="session_mberId" name="session_mberId" value="${sessionScope.session_mberId}"/>
			<div class="movieInfo_box">
				<div class="left_wrap">
					<img class="movieImage" src="${movieImage}" alt="${movieName}">
				</div>
				<div class="right_wrap">
					<div class="movieTitle">
						<h2 style="margin-bottom:5px;">
							<c:if test="${movieAge != 'all'}">
								<i class="age_l age_${movieAge}_l">${movieAge}세관람가</i>
							</c:if>
							<c:if test="${movieAge == 'all' }">
								<i class="age_l age_${movieAge}_l">${movieAge}관람가</i>
							</c:if>
							<span>${movieName}</span>
						</h2>
						<p style="font-size:13px;">${movieEngName}, ${movieFilmDate.substring(0,4)}</p>
					</div>
					<div class="movieInfo">
						<ul class="info_wrap">
							<li><strong>개봉일</strong> :
								${movieFilmDate.substring(0,4)}.${movieFilmDate.substring(4,6)}.${movieFilmDate.substring(6,8)}</li>
							<li><strong>감독</strong> : ${movieDirector}</li>
							<li><strong>출연진</strong> : ${movieActor}</li>
							<li><strong>장르</strong> : ${movieGenre} / ${movieTime} 분</li>
							<c:if test="${movieAge eq '15'}">
								<li><strong>상영등급</strong> : 15세 이상</li>
							</c:if>
							<c:if test="${movieAge eq '12'}">
								<li><strong>상영등급</strong> : 12세 이상</li>
							</c:if>
							<c:if test="${movieAge eq '19'}">
								<li><strong>상영등급</strong> : 19세 이상</li>
							</c:if>
							<c:if test="${movieAge eq 'all'}">
								<li><strong>상영등급</strong> : 전체 이용가</li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
			<div class="movieRow_box">
				<hr class="sub-title"></hr>
				<h3>줄거리</h3>
				<div class="summary">${movieStory}</div>
			</div>
			<div class="movieRow_box comment_box">
				<hr class="sub-title"></hr>
				<div id="movieAvgScore"></div>
				<div id="hide_write_comment">
					<div class="write_comment_wrap">
						<div class="select_star_Wrap">
							<div class="out_wrap">
							<div class="middle_wrap">
							<div class="in_wrap">
								<input type="image" id="selectStar1" src="images/star_mid_off.png">
								<input type="image" id="selectStar2" src="images/star_mid_off.png">
								<input type="image" id="selectStar3" src="images/star_mid_off.png">
								<input type="image" id="selectStar4" src="images/star_mid_off.png">
								<input type="image" id="selectStar5" src="images/star_mid_off.png">
								<input type="hidden" id="hidden_selectedStar" name="hidden_selectedStar">
							</div>
							<div id="div_selectStar"></div>
							</div>
							</div>
						</div>
						<div class="textarea_comment">
							<textarea id="writeComment" name="writeComment" maxlength="140" rows="1" cols="1" placeholder="최소 10자에서 최대 140자까지 입력 가능하며, 부적절한 내용은 제제 사항이 됩니다."></textarea>
						</div>
						<div class="button_comment">
							<button onclick="fn_insertMovieComment();">등록</button>
						</div>
					</div>
				</div>
	<!-- 			총평수 조회 -->
				<div class="allCount"></div>
	<!-- 			영화 댓글 조회 -->
				<div class="allComment">
					<ul id="ul_subComment"></ul>
				</div>
	<!-- 			페이징 영역 -->
				<div class="buttonPage">
					<ul id="ul_pageArea"></ul>
				</div>
			</div>
		</div>	
	</div>
</div>