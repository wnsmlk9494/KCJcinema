var hidden_morePage = "1";

//더보기 선택시 id="p_dday"+번호값 추가로 늘어나게 저장
var hidden_p_dday = 0;

//D-day 표시를 위한 오늘 '년월일' 계산
var date = new Date();
var getYear = String(date.getFullYear());
var getMonth = String(date.getMonth()+1); if(getMonth < 10){getMonth = "0"+getMonth};
var getDate = String(date.getDate());
var getToday = getYear+getMonth+getDate;

$(document).ready(function(){
	fn_soonMovie_movieList(hidden_morePage, hidden_p_dday);
	
	$("#moreList").click(function(){
		//페이지수 1씩 증가
		hidden_morePage = String(parseInt(hidden_morePage) + 1);
		
		fn_soonMovie_movieList(hidden_morePage, hidden_p_dday);
	})
})

//soonMovie 영화 불러오기
function fn_soonMovie_movieList(pageValue, p_dday){
	$.ajax({	
		type : 'GET',
		url : '/movieList.do',
		data : {page:pageValue, listCount:"12", distincMovieList:"soonMovie"},
		datatype : 'JSON',
		cache : false,
		success : function(data){
			//top.jsp의 BOX_OFFICE의 CSS를 수정 (class="active")
			var soonMovie = document.getElementById('soonMovie');
			soonMovie.className = 'normal';
			showingMovie.className = 'normal';
			soonMovie.className = 'normal active';
			
			var subComment = JSON.parse(data);
			//영화 리스트 조회
			for(var i=0; i<subComment.movieListStringJson.length; i++){
				p_dday++;
				$("#ul_soonMovieList").append(
					'<li class="movie_wrap">'+
						'<div class="movieImage_wrap">'+
							'<div class="movieImage">'+
								'<img alt="'+subComment.movieListStringJson[i].movieName+'" src="'+subComment.movieListStringJson[i].movieImage+'">'+
							'</div>'+
						'</div>'+
						'<div class="movieInfo_wrap">'+
							'<div class="movieDateTxt" id="div_movieDate'+p_dday+'">'+
								'<p id="p_dday'+p_dday+'"></p>'+
							'</div>'+
							'<div class="movieInfo">'+
								'<h3 class="movieTitle">'+
									'<span class="film_rate age_'+subComment.movieListStringJson[i].movieAge+'">'+subComment.movieListStringJson[i].movieName+'</span>'+subComment.movieListStringJson[i].movieName+
								'</h3>'+
								'<div class="btn_wrap sm_btn">'+
									'<a title="상세보기" class="img_btn imgBtnMovie btn_left"'+'onclick="fn_movieDetail('+subComment.movieListStringJson[i].movieNum+');"'+
										'href="javascript:void(0)">상세정보</a>'+
									'<a title="예매하기" class="img_btn imgBtnMovie btn_right"'+'onclick="fn_movieBook('+subComment.movieListStringJson[i].movieNum+');"'+
										'href="#">예매하기</a>'+
								'</div>'+
							'</div>'+
						'</div>'+
					'</li>'
				);
				
				//D-DAY를 구하기 위한 날짜 차이 구하는 함수 Date('0000-00-00')형식으로 계산해야 함
				var calcDate = fn_parseDate(subComment.movieListStringJson[i].strDate, getToday);
				$("#div_movieDate"+p_dday).prepend(subComment.movieListStringJson[i].strDate.substring(0,4)+'.'+subComment.movieListStringJson[i].strDate.substring(4,6)+'.'+subComment.movieListStringJson[i].strDate.substring(6,8));
				$("#p_dday"+p_dday).append("D-"+calcDate);
			}
			
			hidden_p_dday = p_dday;
			
			//총페이지가 포함된 구간이면 더보기 버튼 삭제
			if(subComment.endPageNum == subComment.totalPageNum){
				$("#div_morePage").html("");
			}
        }
	})
}

function fn_movieDetail(gbn) {
	//스크롤 바를 감춘다.
	document.getElementsByTagName('body')[0].style.overflow = 'hidden';
	
	var maskHeight = $(document).height();
	var maskWidth = $(window).width();
	$('#mask').css({'width' : maskWidth, 'height' : maskHeight});
	$('#mask').fadeIn(500);
    $.ajax({
        type : 'get',
        url : '/movieDetailPop.do',
        data : 'movieNum='+gbn,
        dataType : 'html',
        cache : false,
        success : function(data){
            $("#layerPopOpen").html(data);
            
            $("#closeMovieDetailPop").click(function(){
            	//스크롧 바를 나타낸다
            	document.getElementsByTagName('body')[0].style.overflow = 'auto';
            	
     			$('#layerPopOpen').html("");
     			$('#mask').hide();
     		})
        }
    });
}

function fn_movieBook(gbn) {
	var sessionId = $("#session_mberId").val();
	if(!sessionId){
		alert("로그인이 필요한 서비스입니다.");
		fn_loginFormPop();
		return;
	}
	
	//스크롤 바를 감춘다.
	document.getElementsByTagName('body')[0].style.overflow = 'hidden';
	
	var maskHeight = $(document).height();
	var maskWidth = $(window).width();
	$('#mask').css({'width' : maskWidth, 'height' : maskHeight});
	$('#mask').fadeIn(500);
	$.ajax({
        type : 'get',
        url : '/movieBookPop.do',
        data : 'movieNum='+gbn,
        dataType : 'html',
        cache : false,
        success : function(data){
            $("#layerPopOpen").html(data);
            
            $("#closeMovieBookPop").click(function(){
            	//스크롧 바를 나타낸다
            	document.getElementsByTagName('body')[0].style.overflow = 'auto';
            	
     			$('#layerPopOpen').html("");
     			$('#mask').hide();
     		})
        }
    });
}

//Date('2019-01-01') 형식의 날짜 차이 계산
//Date('2019', '01', '01')로 값을 넣으면 31일, 30일, 28일의 말일 계산을 자동적으로 못해줌. 따라서 '2019-01-01' 형식으로 되어야 함
function fn_parseDate(strDate, getToday){
	var y_strDate = strDate.substr(0,4);
	var m_strDate = strDate.substr(4,2);
	var d_strDate = strDate.substr(6,2);
	var strDateFormat = new Date(y_strDate+'-'+m_strDate+'-'+d_strDate);
	
	var y_getToday = getToday.substr(0,4);
	var m_getToday = getToday.substr(4,2);
	var d_getToday = getToday.substr(6,2);
	var getTodayFormat = new Date(y_getToday+'-'+m_getToday+'-'+d_getToday);
	
//	밀리세컨드 값이 조회되기 때문에 일자를 구하려면 1000*60*60*24
	var resultDate = (strDateFormat - getTodayFormat) / (1000*60*60*24);
	
	return resultDate;
}