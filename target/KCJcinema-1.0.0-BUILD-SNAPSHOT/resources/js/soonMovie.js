var hidden_morePage = "1";
$(document).ready(function(){
	fn_soonMovie_movieList(hidden_morePage);
	
	$("#moreList").click(function(){
		hidden_morePage = String(parseInt(hidden_morePage) + 1);
		fn_soonMovie_movieList(hidden_morePage);
	})
})

//soonMovie 영화 불러오기
function fn_soonMovie_movieList(pageValue){
	//top.jsp의 SOON_MOVIE의 CSS를 수정 (class="active")
	var soonMovie = document.getElementById('soonMovie');
	soonMovie.className = 'normal active';
	showingMovie.className = 'normal';
	boxOffice.className = 'normal';
	
	$.ajax({	
		type : 'GET',
		url : '/movieList.do',
		data : {page:pageValue, listCount:"12", distincMovieList:"soonMovie"},
		datatype : 'JSON',
		cache : false,
		success : function(data){
			var subComment = JSON.parse(data);
			//영화 리스트 조회
			for(var i=0; i<subComment.movieListStringJson.length; i++){
				$("#ul_soonMovieList").append(
					'<li class="movie_wrap">'+
						'<div class="movieImage_wrap">'+
							'<div class="movieImage">'+
								'<img alt="'+subComment.movieListStringJson[i].movieName+'" src="'+subComment.movieListStringJson[i].movieImage+'">'+
							'</div>'+
						'</div>'+
						'<div class="movieInfo_wrap">'+
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