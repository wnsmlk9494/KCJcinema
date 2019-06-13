//페이징처리를 위한 첫 페이지 default
var hidden_morePage = "1";

//현재시간에서 20분을 더한 년+월+일+시+분
var date = new Date(); 
	date.setMinutes(date.getMinutes()+20);
var getFullYear = String(date.getFullYear());
var getMonth = String(date.getMonth()+1);
	if(getMonth < 10){getMonth = "0"+getMonth};
var getDate = String(date.getDate());
	if(getDate < 10){getDate = "0"+getDate};
var getHours = String(date.getHours());
	if(getHours < 10){getHours = "0"+getHours};
var getMinutes = String(date.getMinutes()); //'현재시간+20분 < 영화상영날짜및시간' 의 경우에만 취소가 가능
	if(getMinutes < 10){getMinutes = "0"+getMinutes};

$(function() {
	//영화예매내역 자동 첫 호출
	fn_moreMyBook(hidden_morePage);
	
	//더보기 클릭
	$("#moreList").click(function(){
		hidden_morePage = String(parseInt(hidden_morePage) + 1);
		fn_moreMyBook(hidden_morePage);
	})
})

//취소신청
function fn_cancelBook(gbn){
	$.ajax({
		type : 'POST',
		url : '/updateBookStatus.do',
		data : {bookNum:gbn, bookStatus:'02'},
		datatype : 'JSON',
		success : function(data){
        	alert("취소신청이 완료 되었습니다.");
        	location.reload();
        }
	})
}

//나의 예매내역 불러오기
function fn_moreMyBook(pageValue){
	$.ajax({	
		type : 'GET',
		url : '/selectMoreMyBook.do',
		data : {page:pageValue, listCount:"6"},
		datatype : 'JSON',
		cache : false,
		success : function(data){
			var subMyMovie = JSON.parse(data);
			//예매 리스트 조회
			for(var i=0; i<subMyMovie.myMovieListStringJson.length; i++){
				$("#div_total_wrap_List").append(
					'<div class="total_wrap_List">'+
						'<div class="total_wrap">'+
							'<table class="total_table">'+
								'<colgroup>'+
									'<col style="width:15%;">'+
									'<col style="width:30%;">'+
									'<col style="width:35%;">'+
									'<col style="width:20%;">'+
								'</colgroup>'+
								'<tbody>'+
									'<tr>'+
										'<th colspan="2" class="txt_thRed">예매번호 : '+subMyMovie.myMovieListStringJson[i].bookNum+'</th>'+
										'<th class="txt_thRed">예매정보</th>'+
										//예매상태를 보여주기 위함(취소신청, 취소완료)
										'<th class="status_td" id="th_bookStatus'+subMyMovie.myMovieListStringJson[i].bookNum+'" rowspan="7"></th>'+
									'</tr>'+
									'<tr>'+
										'<td class="txt_tdWhite" rowspan="6">'+
											'<div class="img_div">'+
												'<img alt="영화포스터" src="'+subMyMovie.myMovieListStringJson[i].movieImage+'" style="width:112.5px; height:160px; padding-bottom:10px;"/>'+
											'</div>'+
										'</td>'+
										'<td class="txt_tdWhite" rowspan="6">'+
											'<span class="age age_'+subMyMovie.myMovieListStringJson[i].movieAge+'">'+subMyMovie.myMovieListStringJson[i].movieAge+'</span>'+subMyMovie.myMovieListStringJson[i].movieName+
										'</td>'+
										'<td class="txt_tdGrey">상영일 및 예매일</td>'+
									'</tr>'+
									'<tr>'+
										'<td class="txt_tdWhite">'+subMyMovie.myMovieListStringJson[i].strDate.replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3')+' / '+subMyMovie.myMovieListStringJson[i].bookDate.substring(0,10)+'</td>'+
									'</tr>'+
									'<tr>'+
										'<td class="txt_tdGrey">상영정보</td>'+
									'</tr>'+
									'<tr>'+
										'<td class="txt_tdWhite">'+subMyMovie.myMovieListStringJson[i].cinemaRoomName+', '+subMyMovie.myMovieListStringJson[i].strTime.substring(0,2)+':'+subMyMovie.myMovieListStringJson[i].strTime.substring(2,4)+'~'+subMyMovie.myMovieListStringJson[i].endTime.substring(0,2)+':'+subMyMovie.myMovieListStringJson[i].endTime.substring(2,4)+' ('+subMyMovie.myMovieListStringJson[i].seatNum+')</td>'+
									'</tr>'+
									'<tr>'+
										'<td class="txt_tdGrey">결제정보</td>'+
									'</tr>'+
									//예매수가 1매냐 2매냐에 따라 매수를 다르게 표기하기 위함
									'<tr id="tr_payInfo'+subMyMovie.myMovieListStringJson[i].bookNum+'"></tr>'+
								'</tbody>'+
							'</table>'+
						'</div>'+
					'</div>'
				);
				
				//strDate는 tbl_cinemabook테이블의 'strDate'와'strTime'을 합한 것 (년월일시분)
				//dateAndTime은 MySQL의 NOW()와 현재를 비교하기 위한 현재날짜 값 (년월일시분)
				//'-' ':' '공백'을 제거
				var strDate = parseInt(subMyMovie.myMovieListStringJson[i].strDate.replace(/(\s*)/g,"").replace(/\-/g,"").replace(/\:/g,"")+subMyMovie.myMovieListStringJson[i].strTime);
				var dateAndTime = parseInt(getFullYear+getMonth+getDate+getHours+getMinutes);
				
				//예매상태를 보여주기 위함(취소신청, 취소완료, 취소불가)
				var bookStatus = subMyMovie.myMovieListStringJson[i].bookStatus;
				
				//영화상영시간이 현재+20분보다 더 크면 취소가 가능함
				if(bookStatus == "01" && strDate>dateAndTime){
					$("#th_bookStatus"+subMyMovie.myMovieListStringJson[i].bookNum).append(
						'<button type="button" class="btn_Cancel" id="btn_bookCancel'+subMyMovie.myMovieListStringJson[i].bookNum+'">취소신청</button>'
					);
				}
				//영화상영시간보다 현재+20분이 더 크면 취소할 수 없음
				else if(bookStatus == "01" && strDate<dateAndTime){
					$("#th_bookStatus"+subMyMovie.myMovieListStringJson[i].bookNum).append(
						'<div id="btn_'+subMyMovie.myMovieListStringJson[i].bookNum+'">'+
						'<span class="span_canceled">취소불가</span></div>'
					);
				}
				//취소한 내역(bookStatus=02)이 있다면 취소완료를 표시 
				else if(bookStatus== "02"){
					$("#th_bookStatus"+subMyMovie.myMovieListStringJson[i].bookNum).append(
						'<div id="btn_'+subMyMovie.myMovieListStringJson[i].bookNum+'">'+
						'<span class="span_canceled">취소완료</span></div>'
					);
				}
				
				//예매수가 1매냐 2매냐에 따라 매수를 다르게 표기하기 위함
				var seatNumString = subMyMovie.myMovieListStringJson[i].seatNum;
				var seatNumSplit = seatNumString.split(',');
				if(seatNumSplit.length == 1){																				
					$("#tr_payInfo"+subMyMovie.myMovieListStringJson[i].bookNum).append('<td class="txt_tdWhite">1매 - '+subMyMovie.myMovieListStringJson[i].totalAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'원</td>');
				}else if(seatNumSplit.length == 2){
					$("#tr_payInfo"+subMyMovie.myMovieListStringJson[i].bookNum).append('<td class="txt_tdWhite">2매 - '+subMyMovie.myMovieListStringJson[i].totalAmt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'원</td>');
				}
			}
			
			//총페이지가 포함된 구간이면 더보기 버튼 삭제
			if(subMyMovie.endPageNum == subMyMovie.totalPageNum){
				$("#div_morePage").html("");
			}
			
			//취소신청을 클릭 시 함수 실행
			$("button[id^='btn_bookCancel']").click(function(){
				var id = $(this).attr('id');
				var subId = id.substring(14,id.length);
				fn_cancelBook(subId);
			})
        }
	})
}