$(document).ready(function(){
	//step1은 날짜, 극장, 시간 선택 ,, step2는 좌석 선택 ,, step3는 결제
	$("#goPrev").click(function(){
		$("#step1").show(); $("#step2").hide(); $("#step3").hide();
	})
	
	$("#goPrev2").click(function(){
		$("#step1").hide(); $("#step2").show(); $("#step3").hide();
	})
	
	$("#goNext").click(function(){
		var locationName = $("#locationName").val();
		var cinemaName = $("#cinemaName").val();
		var cinemaNum = $("#cinemaNum").val();
		var cinemaRoomName = $("#cinemaRoomName").val();
		var movieName = $("#movieName").val();
		var strDate = $("#strDate").val();
		var strTime = $("#strTime").val();
		var endTime = $("#endTime").val();
		var seatNum = $("#seatNum").val();
		if(seatNum == null || seatNum == "" || seatNum == " "){alert("좌석 선택 정보를 확인해주세요."); return;}
		var personCnt = $("#personCnt").val();	$("#personCount").val(personCnt);
		var sub_strDate = strDate.substring(0,4)+"-"+strDate.substring(4,6)+"-"+strDate.substring(6,8);
		var sub_strTime = strTime.substring(0,2)+":"+strTime.substring(2,4)
		var sub_endTime = endTime.substring(0,2)+":"+endTime.substring(2,4)
		var totalAmt = parseInt(personCnt)*10000;	$("#totalAmt").val(totalAmt);
		totalAmt = fn_thousandSeparatorCommas(totalAmt); //돈 3자리 마다 ,를 찍어주기 위한 함수이다.
		
		$("#txt_location").text("* 예매번호 : T201902190101000");
		$("#txt_location").text("KCJ CINEMA, "+locationName+"("+cinemaName+")");
		$("#txt_movieName").text("* 영화 : "+movieName);
		$("#txt_dateTime").text("* 관람일 : "+sub_strDate+", "+sub_strTime+"~"+sub_endTime);
		$("#txt_seat").text("* 관람석 : "+locationName+"("+cinemaName+"), "+cinemaRoomName+"("+seatNum+")");
		$("#txt_ticket").text("* 예매수 : "+personCnt+"매");
		$("#txt_totalAmt").text(totalAmt+"원");
		
		fn_selectBookNum(cinemaNum); //fn_selectBookNum()에서 $("#bookNum").val()를 세팅한다.
		
		$("#step1").hide(); $("#step2").hide(); $("#step3").show();
	})
	
	//극장(서울~제주)를 선택했을 때의 함수
	$("a[id^='mainLocation']").click(function(){	//id값이 mainLocation을 포함하는 모든 a태그가 해당된다.
		var id = $(this).attr('id');				//선택한 a태그의 id값을 불러 온다. ex)id=mainLocation+01 = mainLocation01 
		$("a[id^='mainLocation']").removeClass();	//연관된 a태그는 끝부분의 값만 다르게 설정했기 때문에 공통으로 쓰이는 이름을 가진 a태그 전부 클래스 제거한다. 
		$("#"+id).addClass("active");				//선택한 a태그의 클래스는 지정되어 있지 않기 때문에 class="" -> class="active"가 된다. (css에서는 a.active{}로 구현.)
		$("#ul_subLocation").empty();				//지역에 따라 지점의 정보가 바뀌기 때문에 ul밑 li들을 제거시킨다.
		
		var substringId = String(id.substring(12,14));	//지역 선택 a태그의 id값은 끝 2자리만 다르기 때문에 substring으로 2자리를 잘라준 후 ajax 함수에 전달한다.
		$("#locationNum").val(substringId);				//지역번호를 변수에 담아 준다.
		var hidden_locationName = $("input[id^='"+substringId+"hidden_locationName']").val(); $("#locationName").val(hidden_locationName);
		
		$("#cinemaNum").val("");					//지역을 고를 시 지점번호를 담는 변수를 초기화 시킨다.
		fn_setSubLocation(substringId);				//ajax함수를 실행한다.
	})
	
	//11번자리는 혼자앉는 자리이기에 2명씩 선택할 때는 선택 자체가 불가
	$("a[id^='seat']").click(function(){
		var id = $(this).attr('id'); 						//선택한 좌석의 id값을 가져온다.
		var personCnt = $("#personCnt").val();				//관람 인원 수를 가져온다.
		$("a[id^='seat']").not('.off').removeClass();		//class이름이 off가 아닌 요소들의 class이름을 제거한다.
		
		//1인 선택
		if(personCnt == "1"){ 
			$("#"+id).addClass("on");						//선택한 id값을 가진 요소의 class이름에 on을 붙인다.
			$("#seatNum").val(id.substring(4,7));			//id값이 seatA01일 경우 seat까지는 제외하고 A01를 선택한 좌석으로 변수에 담는다.
			
		//2인 선택
		}else if(personCnt == "2"){
			var idNum = parseInt(id.substring(5,7));		//seatA01의 경우 'idNum = 1 (01은 아니다 parseInt에 의한 정수형 변환)'
			
			if(idNum != 6 && idNum != 10){					//6번 좌석과 10번 좌석은 각각 5,6 9,10으로 선택되어야 하기 때문에 '선택된 수 -1' 한다.
				var idNum2 = "0"+String(idNum+1);
				if(idNum2 == "010"){						//9+1=10인데 앞에 0을 붙이니 010이 된다. 010이 될 경우 10으로 바꾼다.
					idNum2 = "10";
				}
				
				if(idNum == 11){
					alert("해당 좌석은 1인석임으로 예매하실 수 없습니다.");
					return;
				}	
			}else{
				var idNum2 = "0"+String(idNum-1);
			}
			var id2 = id.substring(0,5) + idNum2			//'id2 = seatA + 02 = seatA02', 선택한 좌석의 값과 같이 저장될 두번째 좌석
			
			var className_id2 = $("#"+id2).attr('class');
			if(className_id2 == 'off'){
				$("#seatNum").val("");
				alert("해당 좌석은 선택하실 수 없습니다.");
				return;
			}else{
				$("#"+id).addClass("on");					//seatA01의 class이름 on 추가
				$("#"+id2).addClass("on");					//seatA02의 class이름 on 추가
				$("#seatNum").val(id.substring(4,7)+","+id2.substring(4,7));	//id값이 seatA01일 경우 seat까지는 제외하고 A01를 선택한 좌석으로 변수에 담는다.
			}
		}else{
			alert("관람인원을 선택해주십시오."); return;
		}
	})

	//결제하기 부분이다.
	$("#goPay").click(function(){
		if($("input:checkbox[id='agreeCheck']").is(":checked") == true){
			var params = $("#frmSelect").serialize();
	        $.ajax({
	            type : 'post',
	            url : '/bookFormInsert.do',
	            cache : false,
	            data : params,
	            success : function(){
	            	alert("결제가 완료되었습니다.");
	            	$("#layerPopOpen").html("");
	            	$('#mask').hide();
	        	location.reload();
	        }
	    });
		}else{
			alert("결제사항에 동의하셔야 합니다.");
			return;
		}
	})
})

//날짜 선택		
function fn_clickDate(gbn){
	var date1 = document.getElementById('date1');
	var date2 = document.getElementById('date2');
	var date3 = document.getElementById('date3');
	var date4 = document.getElementById('date4');
	var date5 = document.getElementById('date5');
	if(gbn == "1"){$("#strDate").val($("#bookDate1").val()); date1.className = 'active'; date2.className = 'none'; date3.className = 'none'; date4.className = 'none'; date5.className = 'none'; fn_setMovieShowInfo();} //날짜를 선택 시 영화 상영 정보를 조회한다.
	if(gbn == "2"){$("#strDate").val($("#bookDate2").val()); date1.className = 'none'; date2.className = 'active'; date3.className = 'none'; date4.className = 'none'; date5.className = 'none'; fn_setMovieShowInfo();}
	if(gbn == "3"){$("#strDate").val($("#bookDate3").val()); date1.className = 'none'; date2.className = 'none'; date3.className = 'active'; date4.className = 'none'; date5.className = 'none'; fn_setMovieShowInfo();}
	if(gbn == "4"){$("#strDate").val($("#bookDate4").val()); date1.className = 'none'; date2.className = 'none'; date3.className = 'none'; date4.className = 'active'; date5.className = 'none'; fn_setMovieShowInfo();}
	if(gbn == "5"){$("#strDate").val($("#bookDate5").val()); date1.className = 'none'; date2.className = 'none'; date3.className = 'none'; date4.className = 'none'; date5.className = 'active'; fn_setMovieShowInfo();}
}

//지역을 선택한 후 지점을 SELECT한 후 지점을 선택했을 때의 액션을 정의한다. 
function fn_setSubLocation(gbn){
	$.ajax({
		type : 'get',
		url : '/subLocation.do',
		datatype : 'JSON',
		data : 'locationNum='+gbn,
		cache : false,
		success : function(data){
			var subLocation = JSON.parse(data);
			//영화관 조회
			for(var i=0; i<subLocation.length; i++){
				$("#ul_subLocation").append(
						'<li><a id="subLocation'+subLocation[i].cinemaNum+'" href="#">'+subLocation[i].cinemaName+'</a>'+
						'<input type="hidden" id="'+subLocation[i].cinemaNum+'hidden_cinemaName" value="'+subLocation[i].cinemaName+'"/>'+
						'</li>');	
			}
			//영화관 클릭 시 영화관의 상영관 및 영화 상영 정보 조회
			$("a[id^='subLocation']").click(function(){
	 			var subId = $(this).attr('id');
	 			$("a[id^='subLocation']").removeClass();	
	 			$("#"+subId).addClass("active");
	 			
	 			var substringId = String(subId.substring(11,15));
	 			$("#cinemaNum").val(substringId);
	 			var hidden_cinemaName = $("input[id^='"+substringId+"hidden_cinemaName']").val(); $("#cinemaName").val(hidden_cinemaName);
	 			fn_setMovieShowInfo(); //지점을 선택 시 영화 상영 정보를 조회한다.
			})
		}
	})
}

//'날짜', '지역', '영화관' 선택 시 영화 상영 정보를 조회한다. 
function fn_setMovieShowInfo(){
	var movieNum = $("#movieNum").val();
	var cinemaNum = $("#cinemaNum").val();
	var strDate = $("#strDate").val();
	
	if(cinemaNum != "" && movieNum != "" && strDate != ""){
		$("#ul_movieShowInfo").empty();
		var params = $("#frmSelect").serialize();
		$.ajax({
			type : 'get',
			url : '/movieShowInfo.do',
			datatype : 'JSON',
			data : params,
			cache : false,
			success : function(data){
				var movieShowInfo = JSON.parse(data);
				var all = "'all'";
				for(var i=0; i<movieShowInfo.length; i++){
					//전체관람가 or 15/19/12세관람가 의 문구가 다름을 결정해주기 위해 movieAgeText에 담음 
					var movieAge = movieShowInfo[i].movieAge;
					var movieAgeText
					if (movieAge != "all")
						{movieAgeText = "세관람가"}
					else
						{movieAgeText = "관람가"}
					
					$("#ul_movieShowInfo").append(
						'<li id="li_movieShowInfo'+i+'">'+
							'<div class="viewing_time">'+
								'<p class="time_table"><strong>'+movieShowInfo[i].strTime.substring(0,2)+':'+movieShowInfo[i].strTime.substring(2,4)+'</strong><span>~ '+movieShowInfo[i].endTime.substring(0,2)+':'+movieShowInfo[i].endTime.substring(2,4)+'</span></p>'+
							'</div>'+
							'<div class="movie">'+
								'<p class="title">'+
									'<span class="age age_'+movieShowInfo[i].movieAge+'">'+movieShowInfo[i].movieAge + movieAgeText+'</span>'+
									'<a href="javascript:void(0);">'+movieShowInfo[i].movieName+'</a>'+
								'</p>'+
								'<p class="subtitle">'+movieShowInfo[i].movieGenre+'</p>'+
							'</div>'+
							'<div class="theater_wrap">'+
								'<p class="cinemaName">'+movieShowInfo[i].cinemaName+'</p>'+
								'<p class="cinemaRoom">'+movieShowInfo[i].cinemaRoomName+'</p>'+
								'<p class="cinemaSeat'+i+'"></p>'+
							'</div>'+
							'<input type="hidden" id="'+i+'hidden_cinemaRoomNum" value="'+movieShowInfo[i].cinemaRoomNum+'"/>'+
							'<input type="hidden" id="'+i+'hidden_cinemaRoomName" value="'+movieShowInfo[i].cinemaRoomName+'"/>'+
							'<input type="hidden" id="'+i+'hidden_strTime" value="'+movieShowInfo[i].strTime+'"/>'+
							'<input type="hidden" id="'+i+'hidden_endTime" value="'+movieShowInfo[i].endTime+'"/>'+
						'</li>'
					);
					//해당 '날짜/지점/상영관/상영시간' 에 따른 이용가능한 좌석 수 조회
					$.ajax({
						type : 'get',
						url : '/availableSeatNum.do',
						data : {strDate:movieShowInfo[i].strDate, cinemaNum:movieShowInfo[i].cinemaNum, cinemaRoomNum:movieShowInfo[i].cinemaRoomNum, strTime:movieShowInfo[i].strTime, bookStatus:'01'},
						datatype : 'JSON',
						async : false,
						cache : false,
						success : function(availableSeatNum){
							$(".cinemaSeat"+i).append(availableSeatNum + ' / 99');
						}
					})
				}
				
				//좌석선택으로 이동
				$("li[id^='li_movieShowInfo']").click(function(){
					var id = $(this).attr('id');	 
					$("li[id^='li_movieShowInfo']").removeClass(); 
					$("#"+id).addClass("active");
					
					var substringId = id.substring(16,id.length);
					var hidden_cinemaRoomNum = $("input[id^='"+substringId+"hidden_cinemaRoomNum']").val(); $("#cinemaRoomNum").val(hidden_cinemaRoomNum);
					var hidden_cinemaRoomName = $("input[id^='"+substringId+"hidden_cinemaRoomName']").val(); $("#cinemaRoomName").val(hidden_cinemaRoomName);
					var hidden_strTime = $("input[id^='"+substringId+"hidden_strTime']").val(); $("#strTime").val(hidden_strTime);
					var hidden_endTime = $("input[id^='"+substringId+"hidden_endTime']").val(); $("#endTime").val(hidden_endTime);
					
					var params = $("#frmSelect").serialize();
					$.ajax({
						type : 'get',
						url : '/bookedSeatNum.do',
						data : params,
						dataType : 'JSON',
						cache : false,
						success : function(data){
							var stringData = JSON.stringify(data);
							var bookedSeatNum = JSON.parse(stringData);
							$("a[id^='seat']").removeClass();
							$("#seatNum").val("");
							for(var i=0; i<bookedSeatNum.length; i++){
								var seatId = bookedSeatNum[i].seatNum;
								$("#seat"+seatId).addClass("off");
							}
						}
					})
					
					$("#step1").hide(); $("#step2").show();
				})
			}
		})
	}
}

//20000 -> 20,000 식의 ','를 찍어준다.
function fn_thousandSeparatorCommas(number){
	 var string = "" + number;						//문자로 바꾸기.
	 string = string.replace( /[^-+\.\d]/g, "" )	//±기호와 소수점, 숫자들만 남기고 전부 지우기.
	 var regExp = /^([-+]?\d+)(\d{3})(\.\d+)?/;		//필요한 정규식.
	 while(regExp.test(string)) 
		 string = string.replace( regExp, "$1" + "," + "$2" + "$3" );	//쉼표 삽입.
	 
	 return string; 
}

//조건에 맞는 가장 마지막 순번의 bookNum을 조회한다.
function fn_selectBookNum(cinemaNum){
	$.ajax({
        type : 'get',
        url : '/findBookNum.do',
        cache : false,
        data : 'cinemaNum='+cinemaNum,
        dataType : 'JSON',
        success : function(findBookNum){
        	var jsonValue = findBookNum.code;
        	if(jsonValue.substring(13,17) == "null"){
        		$("#bookNum").val(jsonValue.substring(0,13) + "0000");
        	}else{
        		var jsonStrValue = jsonValue.substring(0, 1); //T
            	var jsonIntValue = jsonValue.substring(1, jsonValue.length); //201902220101000
            	var jsonResultValue = jsonStrValue + String(parseInt(jsonIntValue)+1); //T + 20190220101001
            	$("#bookNum").val(jsonResultValue);
        	}
        }
    })
}