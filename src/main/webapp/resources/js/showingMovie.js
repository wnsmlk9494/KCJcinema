var hidden_morePage = "1";

//더보기 선택시 id="span_fillStar"+번호값 추가로 늘어나게 저장
var start_span_fillStar = 0;

$(document).ready(function(){
	fn_showingMovie_movieList(hidden_morePage, start_span_fillStar);
	
	$("#moreList").click(function(){
		//페이지수 1씩 증가
		hidden_morePage = String(parseInt(hidden_morePage) + 1);
		
		fn_showingMovie_movieList(hidden_morePage, start_span_fillStar);
	})
})

//showingMovie 영화 불러오기
function fn_showingMovie_movieList(pageValue, fillStar){
	$.ajax({	
		type : 'GET',
		url : '/movieList.do',
		data : {page:pageValue, listCount:"12", distincMovieList:"showingMovie"},
		datatype : 'JSON',
		cache : false,
		success : function(data){
			//top.jsp의 BOX_OFFICE의 CSS를 수정 (class="active")
			var showingMovie = document.getElementById('showingMovie');
			soonMovie.className = 'normal';
			showingMovie.className = 'normal';
			showingMovie.className = 'normal active';
			
			var subComment = JSON.parse(data);
			//영화 리스트 조회
			for(var i=0; i<subComment.movieListStringJson.length; i++){
				fillStar++;
				$("#ul_showingMovieList").append(
					'<li class="movie_wrap">'+
						'<div class="movieImage_wrap">'+
							'<div class="movieImage">'+
								'<img alt="'+subComment.movieListStringJson[i].movieName+'" src="'+subComment.movieListStringJson[i].movieImage+'">'+
							'</div>'+
						'</div>'+
						'<div class="movieInfo_wrap">'+
							'<div class="movieScore">'+
								'<span class="avgScore">'+
									'<span>평점 </span>'+ 
									'<span name="averageScore_015411">'+subComment.movieListStringJson[i].movieScore+'</span>'+
								'</span>'+
								'<span class="avgStar">'+
									'<span class="fillStar" id="span_fillStar'+fillStar+'"></span>'+
								'</span>'+
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
				
				//별점을 정수로 조회하여 맥시멈점수를 비교해서 색칠
				//starOn:색칠별, starOff:색칠x별
				var movieScore = Math.floor(subComment.movieListStringJson[i].movieScore);
				var starOn = movieScore;
				var starOff = 5 - movieScore;
				for(s=1; s<=starOn; s++){
					$("#span_fillStar"+fillStar).append('<img src="images/star_mid_on.png"/>');
				}
				if(starOff != 0){
					for(h=1; h<=starOff; h++){
						$("#span_fillStar"+fillStar).append('<img src="images/star_mid_off.png"/>');
					}	
				}
			}
			
			//더보기 선택시 id="span_fillStar"+번호값 추가로 늘어나게 저장
			start_span_fillStar = fillStar;
			
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