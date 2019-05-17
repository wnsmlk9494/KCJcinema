$(function() {
})
		
function fn_modifyPw(){
	//필수 입력값 검사
	var mberPwd = $("#mberPwd").val();
	var mberPwdCheck= $("#mberPwdCheck").val();
	if(mberPwd == ""){alert("새로운 비밀번호를 입력해주세요."); return;}
	if(mberPwdCheck == ""){alert("새로운 비밀번호 확인을 입력하세요."); return;}
	
	//비밀번호 유효성 검사.
	//영문, 특수문자, 숫자가 무조건 포함되는 조합으로 10자~20자 가능
	var pwd = /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{10,20}$/;
	if(!fn_check(pwd, mberPwd, "비밀번호를 올바르게 작성해주세요.")){return;}
	
	//비밀번호와 비밀번호 확인란 일치 여부 검사
	if(mberPwd != mberPwdCheck){ alert("비밀번호가 일치하지 않습니다."); return;}
	
	//RSA 패스워드 암호화
	var mberPwd = $("#mberPwd").val();
	var rsa = new RSAKey();
	rsa.setPublic($("#RSAModulus").val(), $("#RSAExponent").val());
	mberPwd = rsa.encrypt(mberPwd);
	alert(mberPwd);
	//폼값 전송을 위해 담아 둠
	$("#mberPwd").val(mberPwd);
	
	//<input type="hidden"/>으로 findIdPw.jsp로부터 전달받은 mberId값 보유 및 update 컨트롤러로 전송
	var params = $("#form_modifyPw").serialize();
	$.ajax({
        type : 'POST',
        url : '/modifyPWUpdate.do',
        data : params,
        dataType : 'JSON',
        success : function(data){
        	if(data.code == "success") {
				alert("비밀번호가 성공적으로 변경되었습니다.");
				close();
				//check=0 -> check=1(hide)로 변경. 로그인 누르면 check=0(show)
				fn_goLogin();
	    	}else{
	    		alert("비밀번호 변경 처리에 오류가 발생하였습니다.");
	    		location.reload();
	    	}
        }
    });
}

//비밀번호 유효성 검사
function fn_check(re, what, message) {
	if(re.test(what)) {
		return true;
	}
	alert(message);
}