$(function() {
	//회원가입(양식작성)으로 이동하기 위해 약관동의를 검사 후 true시 이동
	$('#btn_form').click(function(){
		checkTerms = $(':radio[name="agreeornot"]:checked').val();
		if(checkTerms=="agree"){
			location.href="/joinForm.do";
		}else{
			alert("약관에 동의하셔야 회원가입이 가능합니다.");
		}
	})
	$('#btn_agree_cancle').click(function() {
		location.href="/boxOffice.do";
	})
})