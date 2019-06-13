<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="../js/movieBookPop.js"></script>
<div class="reservation">
	<button type="button" class="closeMovieBookPop" id="closeMovieBookPop"></button>
	<form id="frmSelect" name="frmSelect" method="post">
		<input type="hidden" id="session_mberId" name="session_mberId" value="${sessionScope.session_mberId}"/>
		<input type="hidden" id="movieNum" name="movieNum" value="${movieNum}"/>
		<input type="hidden" id="movieName" name="movieName" value="${movieInfo.movieName}"/>
		<input type="hidden" id="strDate" name="strDate" value="${today}"/>
		<input type="hidden" id="locationNum" name="locationNum"/>
		<input type="hidden" id="locationName" name="locationName"/>
		<input type="hidden" id="cinemaNum" name="cinemaNum"/>
		<input type="hidden" id="cinemaName" name="cinemaName"/>
		<input type="hidden" id="cinemaRoomNum" name="cinemaRoomNum"/>
		<input type="hidden" id="cinemaRoomName" name="cinemaRoomName"/>
		<input type="hidden" id="strTime" name="strTime"/>
		<input type="hidden" id="endTime" name="endTime"/>
		<input type="hidden" id="seatNum" name="seatNum"/>
		<input type="hidden" id="totalAmt" name="totalAmt"/>
		<input type="hidden" id="bookNum" name="bookNum"/>
		<input type="hidden" id="personCount" name="personCount"/>
	</form>
	<div class="wrapper">
		<div class="contents ReservationBn_ok" id="step1" tabindex="0" style="display:block;">
<!-- 		시작 - 예매 좌측 (날짜, 극장, 영화) -->
			<div class="left_wrap">
				<div class="date">
					<div class="title "><h2>날짜</h2></div>
					<div class="slidebar" id="sel_date">
						<ul class="slidebar_control">
						</ul>
						<div tabindex="0" class="slidebar_item">
							<ol>
								<li>
								<a id="date1" title="" class="active" onclick="fn_clickDate('1');" href="#">${today.substring(6.8)}(${dayName})</a>
								<input type="hidden" id="bookDate1" name="bookDate1" value="${today}"/>
								</li>

								<li>
								<a id="date2" title=""   onclick="fn_clickDate('2');" href="#">${today2.substring(6.8)}(${dayName2})</a>
								<input type="hidden" id="bookDate2" name="bookDate2" value="${today2}"/>
								</li>

								<li>
								<a id="date3" title=""  onclick="fn_clickDate('3');" href="#">${today3.substring(6.8)}(${dayName3})</a>
								<input type="hidden" id="bookDate3" name="bookDate3" value="${today3}"/>
								</li>

								<li>
								<a id="date4" title=""  onclick="fn_clickDate('4');" href="#">${today4.substring(6.8)}(${dayName4})</a>
								<input type="hidden" id="bookDate4" name="bookDate4" value="${today4}"/>
								</li>

								<li>
								<a id="date5" title=""  onclick="fn_clickDate('5');" href="#">${today5.substring(6.8)}(${dayName5})</a>
								<input type="hidden" id="bookDate5" name="bookDate5" value="${today5}"/>
								</li>
							</ol>
						</div>
					</div>
				</div>
				<div class="theater">
					<div class="title "><h2>극장</h2></div>
					<div class="cinemaList" id="cinemaList">
						<div class="mainLocation">
							<ul>
								<c:set var="i" value="0"/>
								<c:forEach var="mainLocationList" items="${mainLocationList}" begin="0" step="1">
									<li><a id="mainLocation${mainLocationList.locationNum}" href="#">${mainLocationList.locationName}</a></li>
									<input type="hidden" id="${mainLocationList.locationNum}hidden_locationName" name="hidden_locationName" value="${mainLocationList.locationName}"/>
								</c:forEach>
							</ul>
						</div>
						<div class="subLocation">
							<ul id="ul_subLocation">
							</ul>
						</div>						
					</div>
				</div>
				<div class="movie" style="padding:15px; height:263px; width:100%;">
					<div class="title"><h2>영화</h2></div>
					<div class="selectedMovie" style="height:192px; width:100%;">
						<div class="moviePoster" style="position:relative; float:left; width:144px; height:100%;">
							<img alt="${movieInfo.movieName}" src="${movieInfo.movieImage}" style="width:100%; height:100%;">
						</div>
						<div class="movieInfo" style="position:relative; float:left; width:256px; height:100%; padding-left:15px;">
							<ul>
								<li style="font-weight:bold; font-size:17px;"><span class="film_rate age_${movieInfo.movieAge}" style="margin-right:5px;"></span>${movieInfo.movieName}<font size="2">(${movieInfo.movieEngName})</font></li>
							</ul>
						</div>						
					</div>
				</div>
			</div>
<!-- 		종료 - 예매 좌측 (날짜, 극장, 영화) -->
<!-- 		시작 - 예매 우측 (시간) -->
			<div class="right_wrap">
				<div class="time">
					<div class="title "><h2>시간</h2></div>
				</div>
				<div class="movie_list">
					<ul id="ul_movieShowInfo"></ul>	
				</div>
			</div>
<!-- 		종료 - 예매 우측 (시간) -->
		</div>
		
		<div id="step2" tabindex="0" class="contents ReservationBn_ok" style="display:none;">
			<div class="selectbox">
				<ul class="ul_personSelect">
					<li class="li_personSelect">
						<div class="left_li">
							<div class="title"><span>관람 인원 수</span></div>
							<div class="select_box">
								<select id="personCnt">       
									<option selected="selected" value="1">1</option>       
									<option value="2">2</option>  
								</select>
							</div>
						</div>
						<div class="right_li">
							<button type="button" class="search_btn" id="goPrev"><span class="search_btn_span">◀ 극장 및 시간</span></button>
							<button type="button" class="search_btn2" id="goNext"><span class="search_btn_span2">결제하기 ▶</span></button>	
						</div>
					</li>
				</ul>
			</div>
			<div class="mseat_wrap">
				<div class="mseat_inner">
					<div class="screen_box">
						<div class="screen_tit">
							<span>Screen</span>
						</div>
						<div class="screen_scroll">
							<div class="screen_Fbox">
								<div class="seat_Barea">
									<div class="seat_area">
										<span class="seat_tit" style="left: -30px; top: 0px;">A</span>
										<a class="asdf" id="seatA01" style="left: 22px;  top: 0px;" href="#">1</a>
										<a class="" id="seatA02" style="left: 48px;  top: 0px;" href="#">2</a>
										<a class="" id="seatA03" style="left: 75px;  top: 0px;" href="#">3</a>
										<a class="" id="seatA04" style="left: 102px; top: 0px;" href="#">4</a>
										<a class="" id="seatA05" style="left: 128px; top: 0px;" href="#">5</a>
										<a class="" id="seatA06" style="left: 155px; top: 0px;" href="#">6</a>
										<a class="" id="seatA07" style="left: 196px; top: 0px;" href="#">7</a>
										<a class="" id="seatA08" style="left: 222px; top: 0px;" href="#">8</a>
										<a class="" id="seatA09" style="left: 249px; top: 0px;" href="#">9</a>
										<a class="" id="seatA10" style="left: 276px; top: 0px;" href="#">10</a>
										<a class="" id="seatA11" style="left: 315px; top: 0px;" href="#">11</a>
										
										<span class="seat_tit" style="left: -30px; top: 27px;">B</span>
										<a class="" id="seatB01" style="left: 22px;  top: 27px;" href="#">1</a>
										<a class="" id="seatB02" style="left: 48px;  top: 27px;" href="#">2</a>
										<a class="" id="seatB03" style="left: 75px;  top: 27px;" href="#">3</a>
										<a class="" id="seatB04" style="left: 102px; top: 27px;" href="#">4</a>
										<a class="" id="seatB05" style="left: 128px; top: 27px;" href="#">5</a>
										<a class="" id="seatB06" style="left: 155px; top: 27px;" href="#">6</a>
										<a class="" id="seatB07" style="left: 196px; top: 27px;" href="#">7</a>
										<a class="" id="seatB08" style="left: 222px; top: 27px;" href="#">8</a>
										<a class="" id="seatB09" style="left: 249px; top: 27px;" href="#">9</a>
										<a class="" id="seatB10" style="left: 276px; top: 27px;" href="#">10</a>
										<a class="" id="seatB11" style="left: 315px; top: 27px;" href="#">11</a>
										
										<span class="seat_tit" style="left: -30px; top: 54px;">C</span>
										<a class="" id="seatC01" style="left: 22px;  top: 54px;" href="#">1</a>
										<a class="" id="seatC02" style="left: 48px;  top: 54px;" href="#">2</a>
										<a class="" id="seatC03" style="left: 75px;  top: 54px;" href="#">3</a>
										<a class="" id="seatC04" style="left: 102px; top: 54px;" href="#">4</a>
										<a class="" id="seatC05" style="left: 128px; top: 54px;" href="#">5</a>
										<a class="" id="seatC06" style="left: 155px; top: 54px;" href="#">6</a>
										<a class="" id="seatC07" style="left: 196px; top: 54px;" href="#">7</a>
										<a class="" id="seatC08" style="left: 222px; top: 54px;" href="#">8</a>
										<a class="" id="seatC09" style="left: 249px; top: 54px;" href="#">9</a>
										<a class="" id="seatC10" style="left: 276px; top: 54px;" href="#">10</a>
										<a class="" id="seatC11" style="left: 315px; top: 54px;" href="#">11</a>
										
										<span class="seat_tit" style="left: -30px; top: 81px;">D</span>
										<a class="" id="seatD01" style="left: 22px;  top: 81px;" href="#">1</a>
										<a class="" id="seatD02" style="left: 48px;  top: 81px;" href="#">2</a>
										<a class="" id="seatD03" style="left: 75px;  top: 81px;" href="#">3</a>
										<a class="" id="seatD04" style="left: 102px; top: 81px;" href="#">4</a>
										<a class="" id="seatD05" style="left: 128px; top: 81px;" href="#">5</a>
										<a class="" id="seatD06" style="left: 155px; top: 81px;" href="#">6</a>
										<a class="" id="seatD07" style="left: 196px; top: 81px;" href="#">7</a>
										<a class="" id="seatD08" style="left: 222px; top: 81px;" href="#">8</a>
										<a class="" id="seatD09" style="left: 249px; top: 81px;" href="#">9</a>
										<a class="" id="seatD10" style="left: 276px; top: 81px;" href="#">10</a>
										<a class="" id="seatD11" style="left: 315px; top: 81px;" href="#">11</a>
										
										<span class="seat_tit" style="left: -30px; top: 108px;">E</span>
										<a class="" id="seatE01" style="left: 22px;  top: 108px;" href="#">1</a>
										<a class="" id="seatE02" style="left: 48px;  top: 108px;" href="#">2</a>
										<a class="" id="seatE03" style="left: 75px;  top: 108px;" href="#">3</a>
										<a class="" id="seatE04" style="left: 102px; top: 108px;" href="#">4</a>
										<a class="" id="seatE05" style="left: 128px; top: 108px;" href="#">5</a>
										<a class="" id="seatE06" style="left: 155px; top: 108px;" href="#">6</a>
										<a class="" id="seatE07" style="left: 196px; top: 108px;" href="#">7</a>
										<a class="" id="seatE08" style="left: 222px; top: 108px;" href="#">8</a>
										<a class="" id="seatE09" style="left: 249px; top: 108px;" href="#">9</a>
										<a class="" id="seatE10" style="left: 276px; top: 108px;" href="#">10</a>
										<a class="" id="seatE11" style="left: 315px; top: 108px;" href="#">11</a>
										
										<span class="seat_tit" style="left: -30px; top: 135px;">F</span>
										<a class="" id="seatF01" style="left: 22px;  top: 135px;" href="#">1</a>
										<a class="" id="seatF02" style="left: 48px;  top: 135px;" href="#">2</a>
										<a class="" id="seatF03" style="left: 75px;  top: 135px;" href="#">3</a>
										<a class="" id="seatF04" style="left: 102px; top: 135px;" href="#">4</a>
										<a class="" id="seatF05" style="left: 128px; top: 135px;" href="#">5</a>
										<a class="" id="seatF06" style="left: 155px; top: 135px;" href="#">6</a>
										<a class="" id="seatF07" style="left: 196px; top: 135px;" href="#">7</a>
										<a class="" id="seatF08" style="left: 222px; top: 135px;" href="#">8</a>
										<a class="" id="seatF09" style="left: 249px; top: 135px;" href="#">9</a>
										<a class="" id="seatF10" style="left: 276px; top: 135px;" href="#">10</a>
										<a class="" id="seatF11" style="left: 315px; top: 135px;" href="#">11</a>
										
										<span class="seat_tit" style="left: -30px; top: 162px;">G</span>
										<a class="" id="seatG01" style="left: 22px;  top: 162px;" href="#">1</a>
										<a class="" id="seatG02" style="left: 48px;  top: 162px;" href="#">2</a>
										<a class="" id="seatG03" style="left: 75px;  top: 162px;" href="#">3</a>
										<a class="" id="seatG04" style="left: 102px; top: 162px;" href="#">4</a>
										<a class="" id="seatG05" style="left: 128px; top: 162px;" href="#">5</a>
										<a class="" id="seatG06" style="left: 155px; top: 162px;" href="#">6</a>
										<a class="" id="seatG07" style="left: 196px; top: 162px;" href="#">7</a>
										<a class="" id="seatG08" style="left: 222px; top: 162px;" href="#">8</a>
										<a class="" id="seatG09" style="left: 249px; top: 162px;" href="#">9</a>
										<a class="" id="seatG10" style="left: 276px; top: 162px;" href="#">10</a>
										<a class="" id="seatG11" style="left: 315px; top: 162px;" href="#">11</a>
										
										<span class="seat_tit" style="left: -30px; top: 189px;">H</span>
										<a class="" id="seatH01" style="left: 22px;  top: 189px;" href="#">1</a>
										<a class="" id="seatH02" style="left: 48px;  top: 189px;" href="#">2</a>
										<a class="" id="seatH03" style="left: 75px;  top: 189px;" href="#">3</a>
										<a class="" id="seatH04" style="left: 102px; top: 189px;" href="#">4</a>
										<a class="" id="seatH05" style="left: 128px; top: 189px;" href="#">5</a>
										<a class="" id="seatH06" style="left: 155px; top: 189px;" href="#">6</a>
										<a class="" id="seatH07" style="left: 196px; top: 189px;" href="#">7</a>
										<a class="" id="seatH08" style="left: 222px; top: 189px;" href="#">8</a>
										<a class="" id="seatH09" style="left: 249px; top: 189px;" href="#">9</a>
										<a class="" id="seatH10" style="left: 276px; top: 189px;" href="#">10</a>
										<a class="" id="seatH11" style="left: 315px; top: 189px;" href="#">11</a>
										
										<span class="seat_tit" style="left: -30px; top: 216px;">I</span>
										<a class="" id="seatI01" style="left: 22px;  top: 216px;" href="#">1</a>
										<a class="" id="seatI02" style="left: 48px;  top: 216px;" href="#">2</a>
										<a class="" id="seatI03" style="left: 75px;  top: 216px;" href="#">3</a>
										<a class="" id="seatI04" style="left: 102px; top: 216px;" href="#">4</a>
										<a class="" id="seatI05" style="left: 128px; top: 216px;" href="#">5</a>
										<a class="" id="seatI06" style="left: 155px; top: 216px;" href="#">6</a>
										<a class="" id="seatI07" style="left: 196px; top: 216px;" href="#">7</a>
										<a class="" id="seatI08" style="left: 222px; top: 216px;" href="#">8</a>
										<a class="" id="seatI09" style="left: 249px; top: 216px;" href="#">9</a>
										<a class="" id="seatI10" style="left: 276px; top: 216px;" href="#">10</a>
										<a class="" id="seatI11" style="left: 315px; top: 216px;" href="#">11</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="notifySelect">
				<div class="notifyBox" style="padding:15px;">
					<p style="color:RED; font-size:14px; margin-bottom:5px;">*&nbsp; 관람 인원 수를 선택하셔야 좌석 선택이 가능합니다.</p>
					<p style="color:RED; font-size:14px; margin-bottom:5px;">*&nbsp; 관람 인원 수에 따라 좌석 선택에 제한이 있을 수 있습니다.</p>
					<p style="color:RED; font-size:14px; margin-bottom:5px;">*&nbsp; 이미 예매 된 좌석은 선택하실 수 없습니다.</p>
				</div>
			</div>
		</div>
		
		<div id="step3" tabindex="0" class="contents ReservationBn_ok" style="display:none;">
			<div class="selectbox">
				<ul class="ul_personSelect" style="text-align:right;">
					<button type="button" class="search_btn" id="goPrev2"><span class="search_btn_span">◀ 좌석 선택</span></button>
					<button type="button" class="search_btn2" id="goPay"><span class="search_btn_span2">결제하기</span></button>
				</ul>
			</div>
			<div style="margin:0 auto; width:388px; height:561.5px;">
				<div style="width:100%; height:100%; border:1px solid #000; background:#33373a; font-size:14px;">
					<div style="width:100%; height:30px;">
						<div style="width:100%; height:53px;">
							<p style="padding: 15px 0px; height: 100%; text-align: center; font-size: 22px; border-bottom:1px solid #000; color:#fff;">KCJ CINEMA</p>
						</div>
						<div style="width:100%; height:446px;">
							<div style="width:100%; height:105px; padding:15px 30px; border-bottom:1px solid #000;">
								<div style="width:100%; height:100%;">
									<table style="width:100%; height:100%; text-align:left; color:#fff !important;">
										<tr>
											<td id="txt_location">
												KCJ CINEMA, 서울(수락)
											</td>
											<td>
												123-45-67890
											</td>
										</tr>
										<tr>
											<td>
												www.kcjcinema.com
											</td>
											<td>
												02-1234-5678
											</td>
										</tr>
									</table>
								</div>
							</div>
							<div style="width:100%; height:213px; padding:15px 30px; border-bottom:1px solid #000;">
								<div style="width:100%; height:100%;">
									<table style="width:100%; height:100%; text-align:left; padding-bottom:5px; color:#fff !important;">
										<tr>
											<td id="txt_bookNum">
<!-- 												* 예매번호 : T201902170101000 -->
											</td>
										<tr>
											<td id="txt_movieName">
<!-- 												* 영화 : 드래곤볼 슈퍼 극장판 : 브로리 어쩌구 -->
											</td>
										</tr>
										<tr>
											<td id="txt_dateTime">
<!-- 												* 관람일 : 2019-02-19, 21:35~23:26 -->
											</td>
										</tr>
										<tr>
											<td id="txt_seat">
<!-- 												* 관람석 : 서울(수락), 1관(A01,A02) -->
											</td>
										</tr>
										<tr>
											<td id="txt_ticket">
<!-- 												* 예매수 : 2매 -->
											</td>
										</tr>
									</table>
								</div>
							</div>
							<div style="width:100%; height:48px; padding:0 30px;">
								<div style="width:100%; height:100%;">
									<table style="width:100%; height:100%; text-align:left; color:#fff;">
										<tr>
											<td style="text-align:left;">
												* 가격
											</td>
											<td id="txt_totalAmt" style="text-align:right; font-size:18px; color:#ece6cc; font-weight:bold;">
<!-- 												20,000원 -->
											</td>
										<tr>
									</table>
								</div>
							</div>
							<div style="width:100%; height:30px; text-align:right; color:#000; font-size:15px; margin-top:100px; padding-right:16px; color:#ece6cc;">
								<input type="checkbox" id="agreeCheck" name="agreeCheck"/>&nbsp;상기 결제사항에 동의합니다.
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>