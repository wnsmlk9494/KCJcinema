$(document).ready(function(){
   $('#Progress_Loading').hide(); //첫 시작시 로딩바를 숨겨준다.
   $(document).ajaxStart(function(){
//		$('#Progress_Loading').show(); //얘 동작을 안해서 밑에 코드 사용
		$('#Progress_Loading').css({'display':'block'}); //ajax실행시 로딩바를 보여준다.
	})
	$(document).ajaxStop(function(){
		$('#Progress_Loading').hide(); //ajax종료시 로딩바를 숨겨준다.
	});
})