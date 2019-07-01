var session_mberId = $("#session_mberId").val();
var hidden_movieNum = $("#hidden_movieNum").val();
//페이징처리를 위한 첫 페이지 default
var hidden_clickPage = 1;

$(document).ready(function(){
	
	fn_selectCommentCount();
	
	$("#writeComment").click(function(){
		if(!session_mberId){
			alert("로그인이 필요한 서비스입니다.");
			return;
		}
	})
	
	$("input[id^='selectStar']").click(function(){
		var id = $(this).attr('id');
		subId = id.substring(10,11);
		
		//별5개 초기화
		for(var i=1; i<=5; i++){
			$("#selectStar"+i).attr("src","images/star_mid_off.png");
		}
		
		//선택한 별까지 색칠
		for(var i=1; i<=subId; i++){
			$("#selectStar"+i).attr("src","images/star_mid_on.png");
		}
		
		//별점 클릭시 해당 문구가 나오기 위한 p태그 등장
		$("#div_selectStar").html("");
		$("#div_selectStar").append('<p style="margin-top:6px; font-size:13px;" id="starScoreText" name="starScoreText"></p>');
		if(subId == "1"){				
			$("#starScoreText").text("그다지 추천은 안해요..");
		}else if(subId == "2"){
			$("#starScoreText").text("그냥 그랬어요..");
		}else if(subId == "3"){
			$("#starScoreText").text("볼만 했어요.");
		}else if(subId == "4"){
			$("#starScoreText").text("재밌지만 조금 아쉬웠어요~");
		}else if(subId == "5"){
			$("#starScoreText").text("추천할 만한 영화였어요!");
		}
		
		$("#hidden_selectedStar").val(subId);
	})
})
	
//댓글 관련 모든 함수 실행
function fn_selectCommentCount(){
	//로그인 유무를 파악하고, 이미 댓글을 남겼다면 작성 창 가림
	fn_alreadyInsertedComment();
	
	//총 평수 조회
	fn_allAccessment();
	
	//영화 평점 조회, 댓글이 없는 영화라면 하단 로직 실행 중지
	fn_movieAvgScore();
	
	//해당 모든 댓글 불러오기
	fn_allComment();
}

//로그인 유무를 파악하고, 이미 댓글을 남겼다면 작성 창 가림
function fn_alreadyInsertedComment(){
	if(session_mberId){
		$.ajax({	
			type : 'GET',
			url : '/selectAlreadyInsertComment.do',
			data : {movieNum:hidden_movieNum},
			datatype : 'JSON',
			cache : false,
			success : function(alreadyComment){
				if(alreadyComment == "1"){
					$("#hide_write_comment").html("");
				}
            }
		})
	}else if(!session_mberId){
		$("#hide_write_comment").html("");
	}
}

//총 평수 조회
function fn_allAccessment(){
	$.ajax({	
		type : 'GET',
		url : '/selectCommentCount.do',
		data : {movieNum:hidden_movieNum},
		datatype : 'JSON',
		cache : false,
		success : function(allCount){
			$(".allCount").html("");
			$(".allCount").append('<span>총 평수 : '+allCount+'</span>');
        }
	})
}

//영화 평점 조회
function fn_movieAvgScore(){
	$.ajax({	
		type : 'GET',
		url : '/selectMovieAvgScore.do',
		data : {movieNum:hidden_movieNum},
		datatype : 'JSON',
		cache : false,
		success : function(movieAvgScore){
			//해당 영화에 댓글이 없을 경우 none을 반환받고, 이 경우 하단 로직을 실행하지 않도록 한다.
			if(movieAvgScore == "none"){
				return;
			}
			$("#movieAvgScore").html("");
			$("#movieAvgScore").append('<h3>네티즌 평점<font size="3"; style="color:#000">('+movieAvgScore+'점)</font></h3>');
        }
	})
}

//해당 모든 댓글 불러오기
function fn_allComment(){
	$.ajax({	
		type : 'GET',
		url : '/selectAllComment.do',
		data : {movieNum:hidden_movieNum, page:hidden_clickPage, listCount:"6", pageCount:"10"},
		datatype : 'JSON',
		cache : false,
		async : false,
		success : function(data){
			//해당 영화에 댓글이 없을 경우 none을 반환받고, 이 경우 하단 로직을 실행하지 않도록 한다.
			if(data == "none"){
				return;
			}
			
			var subComment = JSON.parse(data);
			//댓글 리스트 부분 초기화
			$("#ul_subComment").html("");
			
			//로그인 유저 외 전체 댓글 조회
			for(var i=0; i<subComment.commentStringJson.length; i++){
				$("#ul_subComment").append(
					'<li class="comment_box">'+
						'<div class="starScore" id="div_starScore'+i+'">'+
						'</div>'+
						'<div class="contents">'+
						'<p>'+subComment.commentStringJson[i].movieComment+'</p>'+
							'<dl>'+
								'<dt id="dt_userInfo'+subComment.commentStringJson[i].seqNum+'">'+
									'<em class="em_left">'+subComment.commentStringJson[i].subMberId+'</em>'+
									'<em class="em_center">'+subComment.commentStringJson[i].insertTime+'</em>'+
								'</dt>'+
							'</dl>'+
						'</div>'+
						'<div class="emotion">'+
							'<button class="good" href="#" onclick="fn_updatelikeCount('+subComment.commentStringJson[i].seqNum+');"></button>'+
							'<strong><span class="like_Count">'+subComment.commentStringJson[i].likeComment+'</span></strong>'+
							'<button class="bad" href="#" onclick="fn_updatedislikeCount('+subComment.commentStringJson[i].seqNum+');"></button>'+
							'<strong><span class="dislike_Count">'+subComment.commentStringJson[i].dislikeComment+'</span></strong>'+
						'</div>'+
					'</li>'
				);
				
				//별점을 조회하여 맥시멈점수를 비교해서 색칠
				//starOn:색칠별, starOff:색칠x별
				var starOn = subComment.commentStringJson[i].movieScore;
				var starOff = 5 - subComment.commentStringJson[i].movieScore;
				for(s=1; s<=starOn; s++){
					$("#div_starScore"+i).append('<img src="images/star_mid_on.png"/>');
				}
				if(starOff != 0){
					for(h=1; h<=starOff; h++){
						$("#div_starScore"+i).append('<img src="images/star_mid_off.png"/>');
					}	
				}
			}
			
			//로그인 유저 댓글 조회
			for(var i=0; i<subComment.mycommentStringJson.length; i++){
				$("#ul_subComment").prepend(
					'<li class="comment_box my">'+
						'<div class="starScore" id="div_starScore'+i+'">'+
						'</div>'+
						'<div class="contents">'+
						'<p>'+subComment.mycommentStringJson[i].movieComment+'</p>'+
							'<dl>'+
								'<dt id="dt_userInfo'+subComment.mycommentStringJson[i].seqNum+'">'+
									'<em class="em_left">'+subComment.mycommentStringJson[i].mberId+'</em>'+
									'<em class="em_center">'+subComment.mycommentStringJson[i].insertTime+'</em>'+
								'</dt>'+
							'</dl>'+
						'</div>'+
						'<div class="emotion">'+
							'<button class="good"></button>'+
							'<strong><span class="like_Count">'+subComment.mycommentStringJson[i].likeComment+'</span></strong>'+
							'<button class="bad"></button>'+
							'<strong><span class="dislike_Count">'+subComment.mycommentStringJson[i].dislikeComment+'</span></strong>'+
						'</div>'+
					'</li>'
				);
				
				//별점을 조회하여 맥시멈점수를 비교해서 색칠
				//starOn:색칠별, starOff:색칠x별
				var starOn = subComment.mycommentStringJson[i].movieScore;
				var starOff = 5 - subComment.mycommentStringJson[i].movieScore;
				for(s=1; s<=starOn; s++){
					$("#div_starScore"+i).append('<img src="images/star_mid_on.png"/>');
				}
				if(starOff != 0){
					for(h=1; h<=starOff; h++){
						$("#div_starScore"+i).append('<img src="images/star_mid_off.png"/>');
					}	
				}
				
				//로그인 유저 댓글 삭제 버튼
				$("#dt_userInfo"+subComment.mycommentStringJson[i].seqNum).append('<em class="em_right"><a onclick="fn_deleteMyComment();" href="#">삭제</a></em>');
				//div의 border-top을 red색상으로 변환
				$(".allComment").css({'border-top':'1px solid RED'});
			}
			
			//페이징처리 관련//
			//ul을 제외한 나머지 제거
			$("#ul_pageArea").html("");
			
			//'<, <<' 버튼은 시작페이지가 1이상일때.
			if(subComment.startPageNum != "1"){
				$("#ul_pageArea").append('<li><a href="javascript:void(0);" class="img_prev_next goFirst"></a></li>');
				$("#ul_pageArea").append('<li><a href="javascript:void(0);" class="img_prev_next goPrev"></a></li>');
			}
			
			//보여줄 페이지 수만큼 View
			for(i=subComment.startPageNum; i<=subComment.endPageNum; i++){
				$("#ul_pageArea").append('<li><a href="javascript:void(0);" id="a_pageNum_'+i+'">'+i+'</a></li>');
			}
			
			//총페이지가 포함된 구간이면 '>, >>' 버튼 없음
			if(subComment.endPageNum != subComment.totalPageNum){
				$("#ul_pageArea").append('<li><a href="javascript:void(0);" class="img_prev_next goNext"></a></li>');
				$("#ul_pageArea").append('<li><a href="javascript:void(0);" class="img_prev_next goLast"></a></li>');
			}
			
			//현 페이지에 대한 class="active"를 지우기 위함. 선택한 페이지를 서버로부터 전달받아 active해줌
			$("a[id^='a_pageNum_']").removeClass();
			$("#a_pageNum_"+subComment.clickPage).addClass("active");
			
			//페이지 숫자 클릭
			$("a[id^='a_pageNum_']").click(function(){
				var id = $(this).attr('id');
				hidden_clickPage = id.substring(10,id.length);
				fn_allComment();
			})
			
			//제일 처음 구간으로 이동.
			$("a[class^='img_prev_next goFirst']").click(function(){
				hidden_clickPage = 1;
				fn_allComment();
			})
			
			//다음 구간으로 이동. 현페이지의 시작페이지 + 한 화면에 보여줄 페이지 수
			$("a[class^='img_prev_next goNext']").click(function(){
				hidden_clickPage = parseInt(subComment.startPageNum) + parseInt(subComment.pageCountNum);
				fn_allComment();
			})
			
			//이전 구간으로 이동. 현페이지의 시작페이지 - 한 화면에 보여줄 페이지 수
			$("a[class^='img_prev_next goPrev']").click(function(){
				hidden_clickPage = parseInt(subComment.startPageNum) - parseInt(subComment.pageCountNum);
				fn_allComment();
			})
			
			//마지막 구간으로 이동. 최종페이지
			$("a[class^='img_prev_next goLast']").click(function(){
				hidden_clickPage = subComment.totalPageNum;
				fn_allComment();
			})
        }
	})
}

function fn_updatelikeCount(gbn){
	if(!session_mberId){
		alert("로그인이 필요한 서비스입니다.");
		return;
	}
	var seqNum = gbn;
	$.ajax({
		type : 'POST',
		url : '/updateMovieComment.do',
		data : {movieNum:hidden_movieNum, seqNum:seqNum, distinLike:"like"},
		datatype : 'JSON',
		cache : false,
		async : false,
		success : function(check_dis_like){
			//공감을 눌렀지만 비공감이력이 있을 경우
			if(check_dis_like == "like"){
				alert("이미 비공감을 하셨습니다.");
				return;
			}
			fn_allComment(); //해당 모든 댓글 불러오기
		}
	})
}

function fn_updatedislikeCount(gbn){
	if(!session_mberId){
		alert("로그인이 필요한 서비스입니다.");
		return;
	}
	var seqNum = gbn;
	$.ajax({
		type : 'POST',
		url : '/updateMovieComment.do',
		data : {movieNum:hidden_movieNum, seqNum:seqNum, distinLike:"dislike"},
		datatype : 'JSON',
		cache : false,
		async : false,
		success : function(check_dis_like){
			//비공감을 눌렀지만 공감이력이 있을 경우
			if(check_dis_like == "dislike"){
				alert("이미 공감을 하셨습니다.");
				return;
			}
			fn_allComment(); //해당 모든 댓글 불러오기
		}
	})
}

//댓글등록
//Java 서버에서 댓글평을 등록하면 tbl_movieinfo의 영화 평점을 갱신함 
function fn_insertMovieComment(){
	var hidden_writeComment = $("#writeComment").val();
	var hidden_selectedStar = $("#hidden_selectedStar").val();
	if(!session_mberId){
		alert("로그인이 필요한 서비스입니다.");
		return;
	}
	if(!hidden_selectedStar){
		alert("별점을 선택해주세요.");
		return;
	}
	if(!hidden_writeComment || hidden_writeComment.length < 10){
		alert("댓글 내용은 최소 10자를 입력해주세요.");
		return;
	}
	
	$.ajax({
		type : 'POST',
		url : '/insertMovieComment.do',
		data : {movieNum:hidden_movieNum, movieComment:hidden_writeComment, selectedStar:hidden_selectedStar},
		datatype : 'JSON',
		cache : false,
		success : function(data){
			alert("평이 등록되었습니다.");
			fn_selectCommentCount();
        }
	})
}

//댓글삭제
//댓글평을 삭제하면 Java 서버에서 tbl_movieinfo의 영화 평점을 갱신함
function fn_deleteMyComment(){
	$.ajax({
		type : 'POST',
		url : '/deleteMyMovieComment.do',
		data : {movieNum:hidden_movieNum},
		datatype : 'JSON',
		cache : false,
		success : function(data){
			if(data == "none"){
				$("#ul_subComment").html("");
				$("#movieAvgScore").html("");
				$(".allCount").html('<span>총 평수 : 0</span>');
			}
			
			alert("평을 삭제했습니다.");
			
			fn_selectCommentCount();
			
        	$(".allComment").css({'border-top':'1px solid #e5e5e5'});
        	$("#hide_write_comment").append(
        			'<div class="write_comment_wrap">'+
					'<div class="select_star_Wrap">'+
						'<div class="out_wrap">'+
						'<div class="middle_wrap">'+
						'<div class="in_wrap">'+
							'<input type="image" id="selectStar1" src="images/star_mid_off.png">'+
							'<input type="image" id="selectStar2" src="images/star_mid_off.png">'+
							'<input type="image" id="selectStar3" src="images/star_mid_off.png">'+
							'<input type="image" id="selectStar4" src="images/star_mid_off.png">'+
							'<input type="image" id="selectStar5" src="images/star_mid_off.png">'+
							'<input type="hidden" id="hidden_selectedStar" name="hidden_selectedStar">'+
						'</div>'+
						'<div id="div_selectStar"></div>'+
						'</div>'+
						'</div>'+
					'</div>'+
					'<div class="textarea_comment">'+
						'<textarea id="writeComment" name="writeComment" maxlength="140" rows="1" cols="1" placeholder="최소 10자에서 최대 140자까지 입력 가능하며, 부적절한 내용은 제제 사항이 됩니다."></textarea>'+
					'</div>'+
					'<div class="button_comment">'+
						'<button onclick="fn_insertMovieComment();">등록</button>'+
					'</div>'+
				'</div>'
			);
        	
        	$("input[id^='selectStar']").click(function(){
    			var id = $(this).attr('id');
    			subId = id.substring(10,11);
    			
    			//별5개 초기화
    			for(var i=1; i<=5; i++){
    				$("#selectStar"+i).attr("src","images/star_mid_off.png");
    			}
    			
    			//선택한 별까지 색칠
    			for(var i=1; i<=subId; i++){
    				$("#selectStar"+i).attr("src","images/star_mid_on.png");
    			}
    			
    			//별점 클릭시 해당 문구가 나오기 위한 p태그 등장
    			$("#div_selectStar").html("");
    			$("#div_selectStar").append('<p style="margin-top:6px; font-size:13px;" id="starScoreText" name="starScoreText"></p>');
    			if(subId == "1"){				
    				$("#starScoreText").text("그다지 추천은 안해요..");
    			}else if(subId == "2"){
    				$("#starScoreText").text("그냥 그랬어요..");
    			}else if(subId == "3"){
    				$("#starScoreText").text("볼만 했어요.");
    			}else if(subId == "4"){
    				$("#starScoreText").text("재밌지만 조금 아쉬웠어요~");
    			}else if(subId == "5"){
    				$("#starScoreText").text("추천할 만한 영화였어요!");
    			}
    			
    			$("#hidden_selectedStar").val(subId);
    		})
        }
	})
}