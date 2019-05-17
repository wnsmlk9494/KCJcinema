var check = 0;
	
//로그인 버튼 클릭 시 check의 값으로 열고 닫기 구분
function fn_goLogin() {
	if(check == 0){
		check=1; $("#loginPopOpen").show(); fn_loginFormPop();
	}else if(check == 1){
		check=0; $("#loginPopOpen").hide();
	}
}

//로그인 컨트롤러로 Ajax 처리
function fn_loginFormPop() {
    $.ajax({
        type : 'get',
        url : '/loginFormPop.do',
        dataType : 'html',
        cache : false,
        success : function(data){
            $("#loginPopOpen").html(data);
        }
    });
}

//BOX_OFFICE, SHOWING_MOVIE, SOON_MOVIE <a>태그 클릭 시 해당 링크로 이동
// 1. /example.do(페이지조회) -> /exampleList.do(페이지에 view될 리스트 조회)
function fn_movieList(gbn){
	if(gbn == 'soon'){
		location.href = "/soonMovie.do";
	}else if(gbn == 'showing'){
		location.href = "/showingMovie.do";
	}else if(gbn == 'box'){
		location.href = "/boxOffice.do";
	}
}