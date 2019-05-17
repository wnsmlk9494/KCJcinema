//id중복확인이 통과하면 "1"
var checkIdSuccess = "0";

//documnet.ready함수이다. html 로딩 시 자동으로 이 함수구문이 실행된다
$(function() {
	//이메일 도메인 부분 빠른 입력 셀렉트 박스
	$("#mberEmailOpt").change(function(){
		$("#mberEmail2").val($("#mberEmailOpt").val());
		$("#mberEmail2").text($("#mberEmailOpt").val());
	})
	
	//이전 페이지로 이동
	$('#btn_form_cancle').click(function() {
		location.href="/joinAgree.do";
	})
	
	//저장 기능 수행
	$('#btn_success').click(function() {
		//필수 입력값 검사
		var mberName = $("#mberName").val();
		var mberId = $("#mberId").val();
		var mberPwd = $("#mberPwd").val();
		var mberPwdCheck = $("#mberPwdCheck").val();
		var mberBirth = $("#mberBirth").val();
		var mberTel1 = $("#mberTel1").val();
		var mberTel2 = $("#mberTel2").val();
		var mberTel3 = $("#mberTel3").val();
		var mberEmail1 = $("#mberEmail1").val();
		var mberEmail2 = $("#mberEmail2").val();
		
		if(mberName == ""){alert("이름을 입력해주세요."); return;}
		if(mberName.length > "20"){alert("이름은 20자까지만 가능합니다."); return;}
		if(mberId == ""){alert("아이디를 입력해주세요."); return;}
		if(mberId.length < "5"){alert("아이디는 5~10자만 가능합니다."); return;}
		if(mberId.length > "10"){alert("아이디는 5~10자만 가능합니다."); return;}
		if(mberPwd == ""){alert("비밀번호를 입력해주세요."); return;}
		if(mberPwd.length < "10"){alert("비밀번호는 10~20자만 가능합니다."); return;}
		if(mberPwd.length > "20"){alert("비밀번호는 10~20자만 가능합니다."); return;}
		if(mberBirth == ""){alert("생년월일 8자리를 입력해주세요."); return;}
		if(mberBirth.length < "8"){alert("생년월일 8자리를 입력해주세요."); return;}
		if(mberEmail1 == ""){alert("이메일 닉네임을 입력해주세요."); return;}
		if(mberEmail2 == ""){alert("이메일 도메인를 입력해주세요."); return;}
		if(mberTel2 == ""){alert("휴대전화 앞 번호를 입력해주세요."); return;}
		if(mberTel3 == ""){alert("휴대전화 뒷 번호를 입력해주세요."); return;}
		if(mberTel2.length < "3"){alert("휴대전화 앞 번호를 3자리 이상 입력해주세요."); return;}
		if(mberTel3.length < "3"){alert("휴대전화 뒷 번호를 3자리 이상 입력해주세요."); return;}
		
		//휴대전화 번호 결합 = 01011112222
		//이메일닉네임과 이메일도메인 결합 = aaa@bbb
		var mberTel = $("#mberTel1").val()+$("#mberTel2").val()+$("#mberTel3").val();
		var email = $("#mberEmail1").val()+"@"+$("#mberEmail2").val();
		$("#mberTel").val(mberTel);
		$("#mberEmail").val(email);
		
		//입력값 전부 공백이 포함되어 있으면 제거
		fn_replaceBlank(mberName, mberId, mberPwd, mberBirth, mberTel, email);
		
		//이름 유효성 검사.
		//한글 및 영문 2글자 이상으로 가능
		var kor = /^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣a-zA-Z]{2,20}$/;
		if(!fn_check(kor, mberName, "이름을 올바르게 작성해주세요.")){return;}
		
		//비밀번호 유효성 검사.
		//영문, 특수문자, 숫자가 무조건 포함되는 조합으로 10자~20자 가능
		var pwd = /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{10,20}$/;
		if(!fn_check(pwd, mberPwd, "비밀번호를 올바르게 작성해주세요.")){return;}

		//생년월일 유효성 검사.
		//년도와 월에 맞춰 일의 유효성 검사, 년도와 월의 유효성 검사
		if(!fn_checkBirth(mberBirth, "생년월일을 올바르게 작성해주세요.")){return;}

		//휴대전화 유효성 검사.
		//숫자만 10자리 이상 가능
		var num = /^[0-9]{10,}$/;
		if(!fn_check(num, mberTel, "휴대전화를 올바르게 작성해주세요.")){return;}
		
		//이메일 유효성 검사.
		var eng_num_spe = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		if(!fn_check(eng_num_spe, email, "이메일 형식을 올바르게 작성해주세요.")){return;}
		
		//남, 여 선택에 따라 성별코드 1/2/3/4 저장
		var sex = $("#mberSex").val();
		if(sex == "M"){
			if(mberBirth.substring(0,1) == "1"){
				$("#mberSexCode").val("1");	
			}else if(mberBirth.substring(0,1) == "2"){
				$("#mberSexCode").val("3");
			}
		}else if(sex == "W"){
			if(mberBirth.substring(0,1) == "1"){
				$("#mberSexCode").val("2");	
			}else if(mberBirth.substring(0,1) == "2"){
				$("#mberSexCode").val("4");
			}
		}
		
		//비밀번호와 비밀번호 확인란 일치 여부 검사
		if(mberPwd != mberPwdCheck){ alert("비밀번호가 일치하지 않습니다."); return;}
		
		//이름, 휴대전화를 조회해 가입된 정보가 있는지 확인, 없다면 저장 함수 실행
		//fn_alreadyInsertJoin() -> fn_join()에서 RSA 비밀번호 암호화
		fn_alreadyInsertJoin();
	})
	
	//document.ready() 내부 선언
	//숫자와 한글만 입력이 가능(특수,영어 x) 한글이 입력되는 문제는 style="ime-mode:disabled"
	$("#mberBirth").keypress(function(event){
		if (event.which && (event.which <= 47 || event.which >= 58) && event.which != 8){
			event.preventDefault();
		}
	});
	$("#mberTel2").keypress(function(event){if (event.which && (event.which <= 47 || event.which >= 58) && event.which != 8){event.preventDefault();}});
	$("#mberTel3").keypress(function(event){if (event.which && (event.which <= 47 || event.which >= 58) && event.which != 8){event.preventDefault();}});
})

//id 중복검사용 함수
function fn_idCheck(){
	var mberId = $("#mberId").val();
	
	if(mberId == ""){
		alert("아이디를 입력해주세요.");
		return;
	}
	
	//아이디 유효성 검사
	var re = /^[0-9a-zA-Z]{5,10}$/;
	if(!fn_check(re, mberId, "아이디 형식을 올바르게 작성해주세요.")){return;}
	
	//code값을 반환하여 사용가능 아이디 문구 및 회원가입에 사용될 코드 1 사용
	var params = $("#form_join").serialize();
	$.ajax({
        type : 'POST',
        url : '/selectAlreadyIdCheck.do',
        data : params,
        dataType : 'JSON',
        //반환받은 json데이터. json:[{key:data}, {key:data}]형식
        success : function(alreadyJoin){
        	if(alreadyJoin == "1") {	
        		alert("중복된 아이디입니다.");
        		checkIdSuccess = "0";
        	}else{
        		alert("사용가능한 아이디입니다.");
        		checkIdSuccess = "1";
        	}
        }
    });
}

//이름, 휴대전화를 조회해 가입된 정보가 있는지 확인, 없다면 저장 함수 실행
function fn_alreadyInsertJoin(){
	var params = $("#form_join").serialize();
	$.ajax({	
		type : 'POST',
		url : '/selectAlreadyInsertJoin.do',
		data : params,
		datatype : 'JSON',
		cache : false,
		async : false,
		success : function(alreadyJoin){
			if(alreadyJoin == "1"){
				alert("이미 가입된 정보가 있습니다.");
				return;
			}else{
				//가입하는 함수 호출
				fn_joinForm();	
			}
        }
	})
}

//입력 양식 저장(회원가입)
function fn_joinForm(){
	if(checkIdSuccess != "1"){
		alert("아이디 중복확인이 필요합니다.");
		return;
	}
	
	//RSA <공개키>로 패스워드 암호화
	var mberPwd = $("#mberPwd").val();
	var rsa = new RSAKey();
	rsa.setPublic($("#RSAModulus").val(), $("#RSAExponent").val());
	mberPwd = rsa.encrypt(mberPwd);
	$("#mberPwd").val(mberPwd);
	
	var params = $("#form_join").serialize();
    $.ajax({
        type : 'post',
        url : '/joinFormInsert.do',
        data : params,
        success : function(){
            location.href="/joinSuccess.do";
        }
    });
}

//생년월일 외 나머지 유효성 검사
function fn_check(re, what, message) {
	if(re.test(what)) {
		return true;
	}
	alert(message);
}

//문자열에 포함된 공백 정규식으로 제거
function fn_replaceBlank(mberName, mberId, mberPwd, mberBirth, mberTel){
	var array = [mberName, mberId, mberPwd, mberBirth, mberTel];
	var array;
	for(i=0; i<array.length; i++){
		array[i] = array[i].replace(/ /gi, "");
	}
}

//생년월일 유효성 검사
function fn_checkBirth(what, message){
	var year = parseInt(what.substr(0, 4), 10);
	var month = parseInt(what.substr(4, 2), 10); 
	var date = parseInt(what.substr(6, 2), 10);
	var dt = new Date(year, month-1, date);
	
	//현재 시스템 시간 조회
	var nowDate = new Date();
	var nowYear = parseInt(nowDate.getFullYear());
	
	alert(nowYear);
	if(nowYear >= year && year > 1900 && dt.getMonth()+1 == month && dt.getDate() == date && what.length == 8){
		return true;
	}else{
		alert(message);	
	}
}