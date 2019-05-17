$(document).ready(function() {
	$("#mberId").focus();
	
	$("#loginClick").click(function(){
		var mberId = $("#mberId").val();
		var mberPwd = $("#mberPwd").val();
		if(mberId == ""){alert("아이디를 입력해주세요."); mberId.focus(); return;}
		if(mberPwd == ""){alert("비밀번호를 입력해주세요."); mberPwd.focus(); return;}
		
		//RSA 패스워드 암호화
		var rsa = new RSAKey();
		rsa.setPublic($("#RSAModulus").val(), $("#RSAExponent").val());
		mberPwd = rsa.encrypt(mberPwd);
		
		$.ajax({
			type : 'POST',
			url : '/loginCheck.do',
			data : {mberId:mberId, mberPwd:mberPwd},
			datatype : 'JSON',
			cache : false,
			success : function(loginStatus){
				if(loginStatus == "success"){
					location.reload();
				}else if(loginStatus == "fail"){
					alert("아이디 및 패스워드를 확인해주세요.");
					return;
				}else if(loginStatus == "none"){
					alert("존재하지 않는 아이디입니다.");
					return;
				}else{
					alert("로그인 처리에 오류가 발생하였습니다.");
					return;
				}
			}
		})
	})
})

function fn_findIdPw(gbn) {
	var screenW = screen.availWidth;
	var screenH = screen.availHeight;
	var popW = 607;
	var popH = 643;
	var posL = (screenW - popW) / 2;
	var posT = (screenH - popH) / 2;
	closeMovieDetail = window.open('findIdPw.do?distincCode='+gbn, 'test','width=' + popW + ',height=' + popH+ ',top=' + posT + ',left=' + posL+ ',resizable=no,scrollbars=no');
	
	//top.jsp에 있는 함수로, 로그인 레이어팝업을 열고 닫는 함수
	fn_goLogin();
}

//엔터 누르면 실행
function login_enterkey(){
	if(window.event.keyCode == 13){
		$("#loginClick").click();
	}
}