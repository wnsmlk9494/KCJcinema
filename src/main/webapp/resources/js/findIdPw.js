$(document).ready(function(){
	var distincCode = $("#distincCode").val();
	
	if(distincCode == "id"){
		var id = document.getElementById('id');
		var pw = document.getElementById('pw');
		id.className = 'selectedId';
		pw.className = 'selectPw';
		$("#idContents").show();
		$("#pwContents").hide();
		
	}else if(distincCode == "pw"){
		var id = document.getElementById('id');
		var pw = document.getElementById('pw');
		id.className = 'selectId';
		pw.className = 'selectedPw';
		$("#idContents").hide();
		$("#pwContents").show();
	}
	
	$("#id").click(function(){
		var id = document.getElementById('id');
		var pw = document.getElementById('pw');
		if(id.className == 'selectId'){
			id.className = 'selectedId';
			pw.className = 'selectPw';
			$("#idContents").show();
			$("#pwContents").hide();
		}
	})
	$("#pw").click(function(){
		var id = document.getElementById('id');
		var pw = document.getElementById('pw');
		if(pw.className == 'selectPw'){
			id.className = 'selectId';
			pw.className = 'selectedPw';
			$("#idContents").hide();
			$("#pwContents").show();
		}
	})
	
	$("#mberBirth").keypress(function(event){if (event.which && (event.which <= 47 || event.which >= 58) && event.which != 8){event.preventDefault();}});
	$("#mberTel").keypress(function(event){if (event.which && (event.which <= 47 || event.which >= 58) && event.which != 8){event.preventDefault();}});
	$("#sMberBirth").keypress(function(event){if (event.which && (event.which <= 47 || event.which >= 58) && event.which != 8){event.preventDefault();}});
	$("#sMberTel").keypress(function(event){if (event.which && (event.which <= 47 || event.which >= 58) && event.which != 8){event.preventDefault();}});
})

//아이디 찾기
function fn_idSearch(){
	//유효성 검사
	var mberName = $("#mberName").val();
	var mberBirth= $("#mberBirth").val();
	var mberTel = $("#mberTel").val().replace(/\-/g,'');
	$("#mberTel").val(mberTel);
	
	if(mberName == ""){alert("이름을 입력해주세요."); return;}
	if(mberBirth == ""){alert("생년월일을 입력해주세요."); return;}
	if(mberTel == ""){alert("휴대전화 번호를 입력해주세요."); return;}
	if(mberBirth.length < "8"){alert("생년월일 8자리를 입력해주세요."); return;}
	
	//이름 유효성 검사.
	//한글 및 영문 2글자 이상으로 가능
	var kor = /^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣a-zA-Z]{2,}$/;
	if(!fn_check(kor, mberName, "이름을 올바르게 작성해주세요.")){return;}
	
	//생년월일 유효성 검사.
	if(!fn_checkBirth(mberBirth, "생년월일을 올바르게 작성해주세요.")){return;}
	
	//휴대전화 유효성 검사.
	//숫자만 10자리 이상 가능
	var num = /^[0-9]{10,}$/;
	if(!fn_check(num, mberTel, "휴대전화를 올바르게 작성해주세요.")){return;}
	
	var params = $("#idSearch").serialize();
	$.ajax({
        type : 'POST',
        url : '/idSearch.do',
        data : params,
        dataType : 'JSON',
        success : function(data){
        	if(data.code != "none") {
				alert("고객님의 아이디는 '" + data.code + "' 입니다.");
				close();
				//check=0 -> check=1(hide)로 변경. 로그인 누르면 check=0(show)
				fn_goLogin();
        	}else if(data.code == "none") {
        		alert("회원 정보를 다시 확인해주세요.");        	}
        }
    });
}

//비밀번호 찾기
function fn_pwSearch(){
	//아래 4가지를 본인인증 수단으로 사용
	var sMberId = $("#sMberId").val();
	var sMberName = $("#sMberName").val();
	var sMberBirth= $("#sMberBirth").val();
	var sMberTel = $("#sMberTel").val().replace(/\-/g,'');
	$("#sMberTel").val(sMberTel);
	
	if(sMberId == ""){alert("아이디를 입력해주세요."); return;}
	if(sMberName == ""){alert("이름을 입력해주세요."); return;}
	if(sMberBirth == ""){alert("생년월일을 입력해주세요."); return;}
	if(sMberTel == ""){alert("휴대전화 번호를 입력해주세요."); return;}
	if(sMberBirth.length < "8"){alert("생년월일 8자리를 입력해주세요."); return;}
	
	//아이디 유효성 검사
	var re = /^[a-zA-Z0-9]{5,10}$/;
	if(!fn_check(re, sMberId, "아이디 형식을 올바르게 작성해주세요.")){return;}
	
	//이름 유효성 검사.
	var kor = /^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣a-zA-Z]{2,}$/;
	if(!fn_check(kor, sMberName, "이름을 올바르게 작성해주세요.")){return;}
	
	//생년월일 유효성 검사.
	if(!fn_checkBirth(sMberBirth, "생년월일을 올바르게 작성해주세요.")){return;}
	
	//휴대전화 유효성 검사.
	var num = /^[0-9]{6,}$/;
	if(!fn_check(num, sMberTel, "휴대전화를 올바르게 작성해주세요.")){return;}
	
	//<form></form>태그 내부 id값들을 나열해주기 때문에 data에 params 변수만 넣으면 됨
	var params = $("#pwSearch").serialize();
	
	$.ajax({
        type : 'POST',
        url : '/pwSearch.do',
        data : params,
        dataType : 'JSON',
        error:function(request,status,error){
        	alert("code:"+request.status+"@@@message:"+request.responseText+"@@@error:"+error);
        },
        success : function(data){//반환받은 json데이터. json:[{key:data}, {key:data}]형식
        	if(data.code != "none") {
        		//회원 정보 일치 시 비밀번호 수정
        		$.ajax({
        	        type : 'POST',
        	        url : '/modifyPW.do',
        	        data : {mberId:data.code},
        	        dataType : 'html',
        	        success : function(modifyPage){
        	        	$("#popupFindAct").html(modifyPage);
        	        }
        	    });
        	}else if(data.code == "none") {
        		alert("회원 정보를 다시 확인해주세요.");
        	}
        }
    });
}

//비밀번호 수정
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
	
	//'비밀번호 찾기'로부터 전달받은 유저 아이디 값 
	var mberId = $("#mberId").val();
	
	$.ajax({
        type : 'POST',
        url : '/modifyPWUpdate.do',
        data : {mberId:mberId, mberPwd:mberPwd},
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

function fn_searchClose(){
	close();
}

function post_to_url(path, params, method) {
    method = method || "post";
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);
    for(var key in params) {
        var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", key);
        hiddenField.setAttribute("value", params[key]);
        form.appendChild(hiddenField);
    }
    document.body.appendChild(form);
    form.submit();
}

//생년월일 외 나머지 정규식 검사
function fn_check(re, what, message) {
	if(re.test(what)) {
		return true;
	}
	alert(message);
}

//생년월일 정규식 검사
function fn_checkBirth(what, message){
	var year = parseInt(what.substr(0, 4), 10);
	var month = parseInt(what.substr(4, 2), 10); 
	var date = parseInt(what.substr(6, 2), 10);
	var dt = new Date(year, month-1, date);
	
	var nowDate = new Date();
	var nowYear = parseInt(nowDate.getFullYear());
	
	if(nowYear >= year && dt.getMonth()+1 == month && dt.getDate() == date && what.length == 8){
		return true;
	}else{
		alert(message);	
	}
}

var mberTel = document.getElementById('mberTel');
mberTel.onkeyup = function(event){
        event = event || window.event;
        var _val = this.value.trim();
        this.value = autoHypenPhone(_val) ;
}

//전화번호 입력마다 길이에 따른 하이푼(-) 적용
	function autoHypenPhone(str){
        str = str.replace(/[^0-9]/g, '');
        var tmp = '';
        if( str.length < 4){
            return str;
        }else if(str.length < 7){
            tmp += str.substr(0, 3);
            tmp += '-';
            tmp += str.substr(3);
            return tmp;
        }else if(str.length < 11){
            tmp += str.substr(0, 3);
            tmp += '-';
            tmp += str.substr(3, 3);
            tmp += '-';
            tmp += str.substr(6);
            return tmp;
        }else{              
            tmp += str.substr(0, 3);
            tmp += '-';
            tmp += str.substr(3, 4);
            tmp += '-';
            tmp += str.substr(7);
            return tmp;
        }
        return str;
    }

function fn_autoHypenTel(thisTag){
	var id = document.getElementById(thisTag.getAttribute('id'));
	id.onkeyup = function(event){
		event = event || window.event;
		var _val = this.value.trim();
		this.value = autoHypenPhone(_val);
	}
}
