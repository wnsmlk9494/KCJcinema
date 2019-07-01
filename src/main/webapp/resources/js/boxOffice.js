var hidden_morePage = "1";

//더보기 선택시 id="seq"+번호값 추가로 늘어나게 저장
var hidden_seq = 1; 

//D-day 표시를 위한 오늘 '년월일' 계산
var date = new Date();
var getYear = String(date.getFullYear());
var getMonth = String(date.getMonth()+1); if(getMonth < 10){getMonth = "0"+getMonth};
var getDate = String(date.getDate());
var getToday = getYear+getMonth+getDate;

$(document).ready(function(){
	fn_boxOffice_movieList(hidden_morePage, hidden_seq);
	
	$("#moreList").click(function(){
		//페이지수 1씩 증가
		hidden_morePage = String(parseInt(hidden_morePage) + 1);
		
		fn_boxOffice_movieList(hidden_morePage, hidden_seq);
	})
})

//boxOffice 영화 불러오기
function fn_boxOffice_movieList(pageValue, seq){
	$.ajax({	
		type : 'GET',
		url : '/movieList.do',
		data : {page:pageValue, listCount:"12", distincMovieList:"boxOffice"},
		datatype : 'JSON',
		cache : false,
		success : function(data){
			//top.jsp의 BOX_OFFICE의 CSS를 수정 (class="active")
			var boxOffice = document.getElementById('boxOffice');
			soonMovie.className = 'normal';
			showingMovie.className = 'normal';
			boxOffice.className = 'normal active';
			
			var subComment = JSON.parse(data);
			
			//영화 리스트 조회
			for(var i=0; i<subComment.movieListStringJson.length; i++){
				$("#ul_boxOfficeList").append(
					'<li class="movie_wrap">'+
						'<div class="movieImage_wrap">'+
							'<div class="movieImage">'+
								'<img alt="'+subComment.movieListStringJson[i].movieName+'" src="'+subComment.movieListStringJson[i].movieImage+'">'+
							'</div>'+
						'</div>'+
						'<div class="movieInfo_wrap">'+
							//D-DAY or SCORE 표시 시작
							'<div id="div_dday_score'+seq+'">'+
							'</div>'+
							//D-DAY or SCORE 표시 시작
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
				
				//상영 예정작인 경우
				if(getToday < subComment.movieListStringJson[i].strDate){
					$("#div_dday_score"+seq).append(
						'<div class="movieDateTxt" id="div_movieDate'+seq+'">'+
							'<p id="p_dday'+seq+'"></p>'+
						'</div>'
					);
					
					var calcDate = fn_parseDate(subComment.movieListStringJson[i].strDate, getToday);
					$("#div_movieDate"+seq).prepend(subComment.movieListStringJson[i].strDate.substring(0,4)+'.'+subComment.movieListStringJson[i].strDate.substring(4,6)+'.'+subComment.movieListStringJson[i].strDate.substring(6,8));
					$("#p_dday"+seq).append("D-"+Math.floor(calcDate));
					
					seq++;
					hidden_seq = seq;
				
				//상영 중인 경우
				}else{
					$("#div_dday_score"+seq).append(
						'<div class="movieScore">'+
							'<span class="avgScore">'+
								'<span>평점 </span>'+ 
								'<span name="averageScore">'+subComment.movieListStringJson[i].movieScore+'</span>'+
							'</span>'+
							'<span class="avgStar">'+
								'<span class="fillStar" id="span_fillStar'+seq+'"></span>'+
							'</span>'+
						'</div>'	
					);
					
					//별점을 정수로 조회하여 맥시멈점수를 비교해서 색칠
					//starOn:색칠별, starOff:색칠x별
					var movieScore = Math.floor(subComment.movieListStringJson[i].movieScore);
					var starOn = movieScore;
					var starOff = 5 - movieScore;
					for(s=1; s<=starOn; s++){
						$("#span_fillStar"+seq).append('<img src="images/star_mid_on.png"/>');
					}
					if(starOff != 0){
						for(h=1; h<=starOff; h++){
							$("#span_fillStar"+seq).append('<img src="images/star_mid_off.png"/>');
						}	
					}

					seq++;
					hidden_seq = seq;					
				}
			}
			
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