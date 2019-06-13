package com.movie.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.domain.MovieDTO;
import com.movie.service.MovieService;

@Controller
public class movieController {

	private static final Logger logger = LoggerFactory.getLogger(movieController.class);

	@Inject
	private MovieService movieService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String redirectBoxOffice(Model model, MovieDTO movieDTO) throws Exception {
		logger.info("welcome /redirectBoxOffice.do !!!");
		
		return "redirect:/boxOffice.do";
	}
	
	// 박스오피스, 현재상영작, 상영예정작
	// 메인페이지(영화 리스트 뷰)
	@RequestMapping(value = "/boxOffice.do", method = RequestMethod.GET)
	public String boxOffice(Model model, MovieDTO movieDTO) throws Exception {
		logger.info("welcome /boxOffice.do !!!");
		
		return "boxOffice";
	}
	
	@RequestMapping(value = "/showingMovie.do", method = RequestMethod.GET)
	public String showingMovie(Model model, MovieDTO movieDTO) throws Exception {
		logger.info("welcome /showingMovie.do !!!");
		
		return "showingMovie";
	}
	
	@RequestMapping(value = "/soonMovie.do", method = RequestMethod.GET)
	public String soonMovie(Model model, MovieDTO movieDTO) throws Exception {
		logger.info("welcome /soonMovie.do !!!");
		
		return "soonMovie";
	}
	
	
	// 박스오피스, 현재상영작, 상영예정작
	// 메인페이지(영화 리스트 뷰)
	@RequestMapping(value = "/movieList.do", method = RequestMethod.GET)
	public String movieList(Model model, MovieDTO movieDTO, HttpServletRequest request, HttpSession session) throws Exception {
		logger.info("welcome /movieList.do !!!");

		// totalCount : 게시글 총 개수
		// page : 현재 선택한 페이지
		// listCount : 한 페이지당 보일 게시글
		// pageCount : 한 화면에 보일 최대 페이지 수
		// totalPage : 게시글에 따른 최대 페이지 수
		// startPage, endPage : 시작페이지와 끝페이지
		// stratCount, endCount : 페이지당 보일 게시글을 조회할 때 사용
		
		// 박스오피스, 현재상영작, 상영예정작 중 무엇을 조회할지 구분하기 위함
		String distincMovieList = request.getParameter("distincMovieList");
		movieDTO.setDistincMovieList(distincMovieList);
		logger.info("distincMovieList === " + distincMovieList);
		
		
		//총 영화수(totalCount) 조회
		MovieDTO selectMovieListCount = null;
		if(distincMovieList.equals("boxOffice"))		 {selectMovieListCount = movieService.selectMovieListCount(movieDTO);}
		else if(distincMovieList.equals("showingMovie")) {selectMovieListCount = movieService.selectMovieListCount(movieDTO);}
		else if(distincMovieList.equals("soonMovie"))	 {selectMovieListCount = movieService.selectMovieListCount(movieDTO);}
		
		//총 영화수(totalCount) 조회
		int totalCount = Integer.parseInt(selectMovieListCount.getAllCount());
		logger.info("총 영화 개수는 === " + totalCount);
		
		//현재 선택한 페이지
		int page = Integer.parseInt(request.getParameter("page"));
		
		//한 페이지에 보일 영화 수
		int listCount = Integer.parseInt(request.getParameter("listCount"));
		
		//한 화면에 보일 최대 페이지 수
		int pageCount = 1;
		
		// 총 페이지 계산
		int totalPage = totalCount / listCount;
		// (14/10 = 1)이기 때문에 나머지 4개를 위해 1페이지 더 생성
		if (totalCount % listCount > 0) {
			totalPage++;
		}

		// (총페이지 < 현재페이지)이면 현재 페이지를 최대치의 페이지로 대체
		if (totalPage < page) {
			page = totalPage;
		}

		int startPage = ((page - 1) / pageCount) * pageCount + 1;
		int endPage = startPage + pageCount - 1;

		// 1~10, 11~20 이렇게 이어지는데 게시물 부족으로 16페이지가 마지막인 경우 17~20페이지를 나타내면 안되기 때문
		if (endPage > totalPage) {
			endPage = totalPage;
		}
		
		// 1페이지가 1~10의 데이터를 가져올 경우 2페이지에서 startCount는 +1을 하여 11로 맞춰야 한다.
		// 하지만 MySQL의 LIMIT함수는 시작수 다음부터 가져오기 때문에 10을 시작수로 해야 11~20을 가져올 수 있다.
		int startCount = (page - 1) * listCount; //0, 10, 20...
		int endCount = page * listCount;
		
		//영화 리스트를 조회하기 위한 컬럼
		movieDTO.setStartCount(startCount);
		movieDTO.setListCount(listCount);

		// writeValueAsString(value)은 JSON형식의 Object를 JSON형식의 String 으로 변환해주기 위함이다.
		List<MovieDTO> selectBoxOffice = movieService.selectMovieList(movieDTO);
		ObjectMapper mapper = new ObjectMapper();
		String movieListStringJson = mapper.writeValueAsString(selectBoxOffice);
		
		// return_MovieDetailPop_Json에서 JSON형식의 string데이터를 ajax에 전송한다.
		String totalPageNum = String.valueOf(totalPage);
		String startPageNum = String.valueOf(startPage);
		String endPageNum = String.valueOf(endPage);
		String clickPageNum = String.valueOf(page);
		String pageCountNum = String.valueOf(pageCount);
		String startCountNum = String.valueOf(startCount);
		String endCountNum = String.valueOf(endCount);
		
		logger.info("============================================================================");
		logger.info("화면당 보일 페이지 수는(pageCountNum) === '" + pageCountNum + "'페이지입니다.");
		logger.info("조회 가능한 총 페이지 수는(totalPageNum) === '" + totalPageNum + "'페이지입니다.");
		logger.info("선택한 페이지는(clickPageNum) === '" + clickPageNum + "'페이지입니다.");
		logger.info("조회된 첫 페이지는(startPageNum) === '" + startPageNum +"'페이지입니다.");
		logger.info("조회된 끝 페이지는(endPageNum) === '" + endPageNum + "'페이지입니다.");
		logger.info("리스트의 첫 번호는(startCountNum) === '" + startCountNum + "'번입니다. [SQL의 limit함수에서는"+(startCount+1)+"이 됩니다.]");
		logger.info("리스트의 끝 번호는(endCountNum) === '" + endCountNum + "'번입니다.");
		logger.info("============================================================================");
		
		model.addAttribute("totalPageNum", totalPageNum);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("clickPage", clickPageNum);
		model.addAttribute("pageCountNum", pageCountNum);
		model.addAttribute("startCountNum", startCountNum);
		model.addAttribute("endCountNum", endCountNum);
		model.addAttribute("movieListStringJson", movieListStringJson);
		
		return "cmn/return_MovieList_Json";
	}
	
	// 영화 상세보기(movieDetailPop.jsp)
	@RequestMapping(value = "/movieDetailPop.do", method = RequestMethod.GET)
	public String movieDetailPop(MovieDTO movieDTO, Model model, HttpServletRequest request) throws Exception {
		logger.info("welcome /movieDetailPop !!!");

		String movieNum2 = request.getParameter("movieNum");
		movieDTO.setMovieNum(movieNum2);

		MovieDTO list = movieService.selectMovieInfo(movieDTO);

		String movieNum = list.getMovieNum();
		String movieName = list.getMovieName();
		String movieEngName = list.getMovieEngName();
		String movieFilmDate = list.getMovieFilmDate();
		String movieDirector = list.getMovieDirector();
		String movieActor = list.getMovieActor();
		String movieAge = list.getMovieAge();
		String movieGenre = list.getMovieGenre();
		String movieStory = list.getMovieStory();
		String movieImage = list.getMovieImage();
		String movieTime = list.getMovieTime();
		Float movieScore = list.getMovieScore();

		model.addAttribute("movieNum", movieNum);
		model.addAttribute("movieName", movieName);
		model.addAttribute("movieEngName", movieEngName);
		model.addAttribute("movieFilmDate", movieFilmDate);
		model.addAttribute("movieDirector", movieDirector);
		model.addAttribute("movieActor", movieActor);
		model.addAttribute("movieAge", movieAge);
		model.addAttribute("movieGenre", movieGenre);
		model.addAttribute("movieStory", movieStory);
		model.addAttribute("movieImage", movieImage);
		model.addAttribute("movieTime", movieTime);
		model.addAttribute("movieScore", movieScore);

		return "movieDetailPop";
	}

	// 영화 상세보기(movieDetailPop.jsp) - 총 평수 조회
	@RequestMapping(value = "/selectCommentCount.do", method = RequestMethod.GET)
	@ResponseBody
	public String selectCommentCount(Model model, MovieDTO movieDTO, HttpServletRequest request) throws Exception {
		logger.info("welcome /selectCommentCount.do !!!");

		String movieNum = request.getParameter("movieNum");
		movieDTO.setMovieNum(movieNum);

		MovieDTO selectCommentCount = movieService.selectCommentCount(movieDTO);
		String allCount = String.valueOf(selectCommentCount.getAllCount());

		return allCount;
	}

	// 영화 상세보기(movieDetailPop.jsp) - 영화 평점 조회
	@RequestMapping(value = "/selectMovieAvgScore.do", method = RequestMethod.GET)
	@ResponseBody
	public String selectMovieAvgScore(Model model, MovieDTO movieDTO, HttpServletRequest request) throws Exception {
		logger.info("welcome /selectMovieAvgScore.do !!!");

		String movieNum = request.getParameter("movieNum");
		movieDTO.setMovieNum(movieNum);

		//해당 영화에 등록된 댓글이 없다면 (EXISTS 결과가 0) 요청된 ajax로 "none"을 전송 
		String checkComment = "";
		MovieDTO checkValue = movieService.selectExistComment(movieDTO);
		String checkValueString = String.valueOf(checkValue.getCheckValue());
		if(checkValueString.equals("0")) {
			checkComment = "none";
			return checkComment;
		}
		
		MovieDTO selectCommentCount = movieService.selectMovieAvgScore(movieDTO);
		String movieAvgScore = String.valueOf(selectCommentCount.getAvgValue());
		
		return movieAvgScore;
	}
	
	// 영화 상세보기(movieDetailPop.jsp) - 댓글 등록 기록 확인(댓글 작성 창 가리기 위함)
	@RequestMapping(value = "/selectAlreadyInsertComment.do", method = RequestMethod.GET)
	@ResponseBody
	public String selectAlreadyInsertComment(Model model, MovieDTO movieDTO, HttpServletRequest request, HttpSession session) throws Exception {
		logger.info("welcome /selectAlreadyInsertComment.do !!!");

		String mberId = (String) session.getAttribute("session_mberId");
		String movieNum = request.getParameter("movieNum");
		movieDTO.setMberId(mberId);
		movieDTO.setMovieNum(movieNum);

		MovieDTO checkValue = movieService.selectAlreadyInsertComment(movieDTO);
		String alreadyComment = String.valueOf(checkValue.getCheckValue());

		return alreadyComment;
	}

	// 영화 상세보기(movieDetailPop.jsp) - 해당 영화 댓글 전체 불러오기
	@RequestMapping(value = "/redirectSelectAllComment.do", method = RequestMethod.GET)
	@ResponseBody
	public String redirectSelectAllComment() throws Exception {
		logger.info("welcome /redirectSelectAllComment.do !!!");
		String checkComment = "";
		checkComment = "none";
		return checkComment;
	}
	// 영화 상세보기(movieDetailPop.jsp) - 해당 영화 댓글 전체 불러오기
	@RequestMapping(value = "/selectAllComment.do", method = RequestMethod.GET)
	public String selectAllComment(Model model, MovieDTO movieDTO, HttpServletRequest request, HttpSession session) throws Exception {
		logger.info("welcome /selectAllComment.do !!!");
		
		String mberId = (String) session.getAttribute("session_mberId");
		if(mberId == null) {
			mberId = "**no*ne**";
		}
		
		String movieNum = request.getParameter("movieNum");
		movieDTO.setMberId(mberId);
		movieDTO.setMovieNum(movieNum);
		
		//해당 영화에 댓글이 없다면 하단 로직을 실행하지 않도록 요청된 ajax로 "none"을 전송하는 메소드로 redirect
		MovieDTO checkValue = movieService.selectExistComment(movieDTO);
		String checkValueString = String.valueOf(checkValue.getCheckValue());
		if(checkValueString.equals("0")) {
			return "redirect:/redirectSelectAllComment.do";
		}

		// totalCount : 게시글 총 개수
		// page : 현재 선택한 페이지
		// listCount : 한 페이지당 보일 게시글
		// pageCount : 한 화면에 보일 최대 페이지 수
		// totalPage : 게시글에 따른 최대 페이지 수
		// startPage, endPage : 시작페이지와 끝페이지
		// stratCount, endCount : 페이지당 보일 게시글을 조회할 때 사용

		MovieDTO selectCommentCount = movieService.selectCommentCount(movieDTO);
		int totalCount = Integer.parseInt(selectCommentCount.getAllCount());
		int page = Integer.parseInt(request.getParameter("page"));
		int listCount = Integer.parseInt(request.getParameter("listCount"));
		int pageCount = Integer.parseInt(request.getParameter("pageCount"));

		// 총 페이지 계산
		int totalPage = totalCount / listCount;
		// (14/10 = 1)이기 때문에 나머지 4개를 위해 1페이지 더 생성
		if (totalCount % listCount > 0) {
			totalPage++;
		}

		// (총페이지 < 현재페이지)이면 현재 페이지를 최대치의 페이지로 대체
		if (totalPage < page) {
			page = totalPage;
		}

		int startPage = ((page - 1) / pageCount) * pageCount + 1;
		int endPage = startPage + pageCount - 1;

		// 1~10, 11~20 이렇게 이어지는데 게시물 부족으로 16페이지가 마지막인 경우 17~20페이지를 나타내면 안되기 때문
		if (endPage > totalPage) {
			endPage = totalPage;
		}

		// 1페이지가 1~10의 데이터를 가져올 경우 2페이지에서 startCount는 +1을 하여 11로 맞춰야 한다.
		// 하지만 MySQL의 LIMIT함수는 시작수 다음부터 가져오기 때문에 10을 시작수로 해야 11~20을 가져올 수 있다.
		int startCount = (page - 1) * listCount;
		int endCount = page * listCount;
		movieDTO.setTotalCount(totalCount);
		movieDTO.setStartCount(startCount);
		movieDTO.setEndCount(endCount);

		// writeValueAsString(value)은 JSON형식의 Object를 JSON형식의 String 으로 변환해주기 위함이다.
		List<MovieDTO> selectMyComment = movieService.selectMyComment(movieDTO);
		List<MovieDTO> selectAllComment = movieService.selectAllComment(movieDTO);
		ObjectMapper mapper = new ObjectMapper();
		String mycommentStringJson = mapper.writeValueAsString(selectMyComment);
		String commentStringJson = mapper.writeValueAsString(selectAllComment);

		// return_MovieDetailPop_Json에서 JSON형식의 string데이터를 ajax에 전송한다.
		String totalPageNum = String.valueOf(totalPage);
		String startPageNum = String.valueOf(startPage);
		String endPageNum = String.valueOf(endPage);
		String clickPageNum = String.valueOf(page);
		String pageCountNum = String.valueOf(pageCount);
		String startCountNum = String.valueOf(startCount);
		String endCountNum = String.valueOf(endCount);
		
		logger.info("============================================================================");
		logger.info("화면당 보일 페이지 수는(pageCountNum) === '" + pageCountNum + "'페이지입니다.");
		logger.info("조회 가능한 총 페이지 수는(totalPageNum) === '" + totalPageNum + "'페이지입니다.");
		logger.info("선택한 페이지는(clickPageNum) === '" + clickPageNum + "'페이지입니다.");
		logger.info("조회된 첫 페이지는(startPageNum) === '" + startPageNum +"'페이지입니다.");
		logger.info("조회된 끝 페이지는(endPageNum) === '" + endPageNum + "'페이지입니다.");
		logger.info("리스트의 첫 번호는(startCountNum) === '" + startCountNum + "'번입니다. [SQL의 limit함수에서는"+(startCount+1)+"이 됩니다.]");
		logger.info("리스트의 끝 번호는(endCountNum) === '" + endCountNum + "'번입니다.");
		logger.info("============================================================================");
		
		model.addAttribute("mycommentStringJson", mycommentStringJson);
		model.addAttribute("commentStringJson", commentStringJson);
		model.addAttribute("totalPageNum", totalPageNum);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("clickPage", clickPageNum);
		model.addAttribute("pageCountNum", pageCountNum);
		model.addAttribute("startCountNum", startCountNum);
		model.addAttribute("endCountNum", endCountNum);
		
		return "cmn/return_MovieDetailPop_Json";
	}

	// 영화 상세보기(movieDetailPop.jsp) - 댓글 등록
	@RequestMapping(value = "/insertMovieComment.do", method = RequestMethod.POST)
	public String insertMovieComment(MovieDTO movieDTO, HttpServletRequest request, HttpSession session) throws Exception {
		logger.info("Welcome /insertMovieComment.do !!!!!");

		String mberId = (String) session.getAttribute("session_mberId");
		String movieNum = request.getParameter("movieNum");
		String movieComment = request.getParameter("movieComment");
		String selectedStar = request.getParameter("selectedStar");
		int movieScore = Integer.parseInt(selectedStar);
		movieDTO.setMberId(mberId);
		movieDTO.setMovieNum(movieNum);
		movieDTO.setMovieComment(movieComment);
		movieDTO.setMovieScore(movieScore);
		
		//로그인 유저의 댓글 및 평점 등록
		movieService.insertMovieComment(movieDTO);
		
		//영화 상세보기(movieDetailPop.jsp) - 댓글 등록 시 tbl_movieInfo 테이블의 영화 평점 계산하여 update
		MovieDTO selectCommentCount = movieService.selectMovieAvgScore(movieDTO);
		float movieAvgScore = Float.valueOf(selectCommentCount.getAvgValue());
		movieDTO.setMovieScore(movieAvgScore);
		movieService.updatetMovieAvgScore(movieDTO);

		logger.info("Welcome save OK!");

		return "movieDetailPop";
	}

	// 영화 상세보기(movieDetailPop.jsp) - 해당 영화 댓글 전체 불러오기
	@RequestMapping(value = "/redirectUpdateMovieComment.do", method = RequestMethod.GET)
	@ResponseBody
	public String redirectUpdateMovieComment(HttpServletRequest request) throws Exception {
		logger.info("welcome /redirectSelectAllComment.do !!!");
		String check_dis_like = request.getParameter("dis_like");
		return check_dis_like;
	}
	// 영화 상세보기(movieDetailPop.jsp) - 공감/비공감 관련
	@RequestMapping(value = "/updateMovieComment.do", method = RequestMethod.POST)
	public String updateMovieComment(MovieDTO movieDTO, HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
		logger.info("Welcome /updateMovieComment.do !!!!!");

		String mberId = (String) session.getAttribute("session_mberId");
		int seqNum = Integer.parseInt(request.getParameter("seqNum"));
		String movieNum = request.getParameter("movieNum");
		movieDTO.setMberId(mberId);
		movieDTO.setSeqNum(seqNum);
		movieDTO.setMovieNum(movieNum);

		//댓글마다의 seqNum를 가지고 해당 댓글의 mberId값을 조회
		MovieDTO postMberIdDTO = movieService.selectCommentId(movieDTO);
		String postMberId = postMberIdDTO.getMberId();
		
		//댓글 작성자의 글을 공감/비공감 하기 위해 set()
		movieDTO.setPostMberId(postMberId);

		//JSP로부터 전달받은 공감/비공감 구분
		String distinLike = request.getParameter("distinLike");
		
		//공감이라면
		if (distinLike.equals("like")) {
			//비공감내역 EXIST 조회, '공감'을 눌렀지만 이미 '비공감'을 했다면 차단하기 위함
			MovieDTO dislikeValueDTO = movieService.selectDislikeAlreadyAccessment(movieDTO);
			String dislikeValue = String.valueOf(dislikeValueDTO.getCheckValue());
			if (dislikeValue.equals("1")){
				//Redirect하며 parameter를 전달하기 위함(RedirectAttributes import)
				redirectAttributes.addAttribute("dis_like","like");
				return "redirect:/redirectUpdateMovieComment.do";
			}
			
			//공감내역 EXIST 조회
			MovieDTO likeValueDTO = movieService.selectLikeAlreadyAccessment(movieDTO);
			//조회된 likeValue값이 "1"이면 이미 공감, "0"이면 공감이력 없음
			String likeValue = String.valueOf(likeValueDTO.getCheckValue());
			if (likeValue.equals("1")){
				//이미 공감했다면 취소 로직
				movieDTO.setDistinLike("unlike");
			}else{
				//공감하지 않았다면 공감 로직
				movieDTO.setDistinLike("like");
			}
			
		//비공감이라면
		} else if (distinLike.equals("dislike")){
			//공감내역 EXIST 조회, '비공감'을 눌렀지만 이미 '공감'을 했다면 차단하기 위함
			MovieDTO likeValueDTO = movieService.selectLikeAlreadyAccessment(movieDTO);
			String likeValue = String.valueOf(likeValueDTO.getCheckValue());
			if (likeValue.equals("1")){
				//Redirect하며 parameter를 전달하기 위함(RedirectAttributes import)
				redirectAttributes.addAttribute("dis_like","dislike");
				return "redirect:/redirectUpdateMovieComment.do";
			}
			
			//비공감내역 EXIST 조회
			MovieDTO dislikeValueDTO = movieService.selectDislikeAlreadyAccessment(movieDTO);
			//조회된 dislikeValue값이 "1"이면 이미 비공감, "0"이면 비공감이력 없음
			String dislikeValue = String.valueOf(dislikeValueDTO.getCheckValue());
			if (dislikeValue.equals("1")) {
				//이미 공감했다면 취소 로직
				movieDTO.setDistinLike("undislike");
			}else{
				//공감하지 않았다면 비공감 로직
				movieDTO.setDistinLike("dislike");
			}
		}
		
		//결과 UPDATE
		movieService.updateLikeMovieComment(movieDTO);

		logger.info("Welcome update OK!");

		return "movieDetailPop";
	}
	
	// 영화 상세보기(movieDetailPop.jsp) - 댓글 삭제
	@RequestMapping(value = "/deleteMyMovieComment.do", method = RequestMethod.POST)
	public String deleteMyMovieComment(MovieDTO movieDTO, HttpServletRequest request, HttpSession session) throws Exception {
		logger.info("Welcome /deleteMyMovieComment.do !!!!!");

		String mberId = (String) session.getAttribute("session_mberId");
		String movieNum = request.getParameter("movieNum");
		movieDTO.setMberId(mberId);
		movieDTO.setMovieNum(movieNum);
		
		//댓글 삭제
		movieService.deleteMyMovieComment(movieDTO);
		
		//해당 영화에 등록된 댓글이 없다면 (EXISTS 결과가 0) 요청된 ajax로 "none"을 전송
		MovieDTO checkValue = movieService.selectExistComment(movieDTO);
		String checkValueString = String.valueOf(checkValue.getCheckValue());
		if(checkValueString.equals("0")) {
			movieDTO.setMovieScore(0);
			movieService.updatetMovieAvgScore(movieDTO);
			
			return "redirect:/redirectDeleteMyMovieComment.do";
		}
		
		//댓글 삭제 시 tbl_movieInfo 테이블의 영화 평점 계산하여 update
		MovieDTO selectCommentCount = movieService.selectMovieAvgScore(movieDTO);
		float movieAvgScore = Float.valueOf(selectCommentCount.getAvgValue());
		movieDTO.setMovieScore(movieAvgScore);
		movieService.updatetMovieAvgScore(movieDTO);
		
		logger.info("Welcome delete OK!");

		return "movieDetailPop";
	}
	
	// 영화 상세보기(movieDetailPop.jsp) - 해당 영화에 등록된 댓글이 없다면 (EXISTS 결과가 0) 요청된 ajax로 "none"을 전송
	@RequestMapping(value = "/redirectDeleteMyMovieComment.do", method = RequestMethod.GET)
	@ResponseBody
	public String redirectDeleteMyMovieComment() throws Exception {
		logger.info("welcome /redirectSelectAllComment.do !!!");
		String checkComment = "none";
		
		return checkComment;
	}

	// 영화예약(movieBookPop.jsp)
	@RequestMapping(value = "/movieBookPop.do", method = RequestMethod.GET)
	public String movieBookPop(MovieDTO movieDTO, Model model, String date, String dateType, HttpSession session,
			HttpServletRequest request) throws Exception {
		logger.info("welcome /movieBookPop !!!");

		/**** 오늘날짜부터 4일 뒤 날짜까지 구하기 ****/
		// 달력 라이브러리로부터 오늘 날짜 GET
		Calendar cal = new GregorianCalendar(Locale.KOREA);
		cal.setTime(new Date());
		// 받는 날짜 형식을 '년월일'로 설정
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");
		String today = fm.format(cal.getTime());
		// day는 오늘의 요일
		int day = cal.get(Calendar.DAY_OF_WEEK);
		String dayName = "";
		// day값은 1~7로 일~토요일 순서로 세팅
		switch (day) { case 1: dayName = "일"; break; case 2: dayName = "월"; break; case 3: dayName = "화"; break; case 4: dayName = "수"; break; case 5: dayName = "목"; break; case 6: dayName = "금"; break; case 7: dayName = "토"; break; }
		// JSP에서 사용할 이름 선언
		model.addAttribute("dayName", dayName);
		model.addAttribute("today", today);

		// (오늘 날짜)+1일
		cal.add(Calendar.DAY_OF_YEAR, 1);
		SimpleDateFormat fm2 = new SimpleDateFormat("yyyyMMdd");
		String today2 = fm2.format(cal.getTime());
		int day2 = cal.get(Calendar.DAY_OF_WEEK);
		String dayName2 = "";
		switch (day2) { case 1: dayName2 = "일"; break; case 2: dayName2 = "월"; break; case 3: dayName2 = "화"; break; case 4: dayName2 = "수"; break; case 5: dayName2 = "목"; break; case 6: dayName2 = "금"; break; case 7: dayName2 = "토"; break; }
		model.addAttribute("dayName2", dayName2);
		model.addAttribute("today2", today2);

		// ((오늘 날짜)+1일)+1일
		cal.add(Calendar.DAY_OF_YEAR, 1);
		SimpleDateFormat fm3 = new SimpleDateFormat("yyyyMMdd");
		String today3 = fm3.format(cal.getTime());
		int day3 = cal.get(Calendar.DAY_OF_WEEK);
		String dayName3 = "";
		switch (day3) { case 1: dayName3 = "일"; break; case 2: dayName3 = "월"; break; case 3: dayName3 = "화"; break; case 4: dayName3 = "수"; break; case 5: dayName3 = "목"; break; case 6: dayName3 = "금"; break; case 7: dayName3 = "토"; break; }
		model.addAttribute("dayName3", dayName3);
		model.addAttribute("today3", today3);

		// (((오늘 날짜)+1일)+1일)+1일
		cal.add(Calendar.DAY_OF_YEAR, 1);
		SimpleDateFormat fm4 = new SimpleDateFormat("yyyyMMdd");
		String today4 = fm4.format(cal.getTime());
		int day4 = cal.get(Calendar.DAY_OF_WEEK);
		String dayName4 = "";
		switch (day4) { case 1: dayName4 = "일"; break; case 2: dayName4 = "월"; break; case 3: dayName4 = "화"; break; case 4: dayName4 = "수"; break; case 5: dayName4 = "목"; break; case 6: dayName4 = "금"; break; case 7: dayName4 = "토"; break; }
		model.addAttribute("dayName4", dayName4);
		model.addAttribute("today4", today4);

		// ((((오늘 날짜)+1일)+1일)+1일)+1일
		cal.add(Calendar.DAY_OF_YEAR, 1);
		SimpleDateFormat fm5 = new SimpleDateFormat("yyyyMMdd");
		String today5 = fm5.format(cal.getTime());
		int day5 = cal.get(Calendar.DAY_OF_WEEK);
		String dayName5 = "";
		switch (day5) { case 1: dayName5 = "일"; break; case 2: dayName5 = "월"; break; case 3: dayName5 = "화"; break; case 4: dayName5 = "수"; break; case 5: dayName5 = "목"; break; case 6: dayName5 = "금"; break; case 7: dayName5 = "토"; break; }
		model.addAttribute("dayName5", dayName5);
		model.addAttribute("today5", today5);

		// 영화코드 하나 받기
		String movieNum = request.getParameter("movieNum");
		model.addAttribute("movieNum", movieNum);

		// 영화코드 하나 받고 그 영화정보 조회
		MovieDTO movieInfo = movieService.selectMovieInfo(movieDTO);
		model.addAttribute("movieInfo", movieInfo);

		// 영화예약 페이지에서 극장선택 select
		List<MovieDTO> mainLocationList = movieService.selectMainLocation(movieDTO);
		model.addAttribute("mainLocationList", mainLocationList);

		return "movieBookPop";
	}

	// 영화예약(movieBookPop.jsp) - 서울, 경기, 강원 등등 지역을 선택하고 각 지역마다의 지점 조회
	@RequestMapping(value = "/subLocation.do", produces = "application/text; charset=utf8", method = RequestMethod.GET)
	@ResponseBody
	public String subLocation(MovieDTO movieDTO, HttpServletRequest request) throws Exception {
		logger.info("welcome /subLocation.do !!!");

		String locationNum = request.getParameter("locationNum");
		movieDTO.setLocationNum(locationNum);

		List<MovieDTO> subLocationList = movieService.selectSubLocation(movieDTO);
		String str = "";
		ObjectMapper mapper = new ObjectMapper();
		str = mapper.writeValueAsString(subLocationList);

		return str;
	}

	// 영화예약(movieBookPop.jsp) - 영화 상영정보
	// 영화 상영시간 20분 전 까지는 예매가 가능하도록 상영정보 조회가 가능하다
	// [현재시간+20분 < 영화시작날짜및시간(strDate+strTime)]
	@RequestMapping(value = "/movieShowInfo.do", produces = "application/text; charset=utf8", method = RequestMethod.GET)
	@ResponseBody
	public String movieShowInfo(MovieDTO movieDTO, HttpServletRequest request) throws Exception {
		logger.info("welcome /movieShowInfo.do !!!");

		String movieNum = request.getParameter("movieNum");
		String cinemaNum = request.getParameter("cinemaNum");
		String strDate = request.getParameter("strDate");
		
		movieDTO.setMovieNum(movieNum);
		movieDTO.setCinemaNum(cinemaNum);
		movieDTO.setStrDate(strDate);

		List<MovieDTO> movieShowInfo = movieService.selectMovieShowInfo(movieDTO);
		
		String str = "";
		ObjectMapper mapper = new ObjectMapper();
		str = mapper.writeValueAsString(movieShowInfo);
		
		return str;
	}

	// 영화예약(movieBookPop.jsp) - step2->step3 때 예약번호를 생성
	@RequestMapping(value = "/findBookNum.do", method = RequestMethod.GET)
	public String findBookNum(Model model, MovieDTO movieDTO, HttpServletRequest request) throws Exception {
		logger.info("welcome /findBookNum.do !!!");

		Calendar cal = new GregorianCalendar(Locale.KOREA);
		cal.setTime(new Date());
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");
		String today = fm.format(cal.getTime());

		String cinemaNum = request.getParameter("cinemaNum");
		movieDTO.setCinemaNum(cinemaNum);

		MovieDTO findBookNum = movieService.selectFindBookNum(movieDTO);
		String number = "";
		if (findBookNum != null) {
			number = findBookNum.getBookNum();
		} else if (findBookNum == null) {
			number = "T" + today + cinemaNum + "null";
		}
		model.addAttribute("code", number);

		return "cmn/returnJson";
	}

	// 영화예약(movieBookPop.jsp) - 영화 예매 정보 저장
	@RequestMapping(value = "/bookFormInsert.do", method = RequestMethod.POST)
	public String bookFormInsert(MovieDTO movieDTO, HttpServletRequest request) {
		logger.info("Welcome /bookFormInsert.do !!!!!");

		String session_mberId = request.getParameter("session_mberId");
		movieDTO.setMberId(session_mberId);

		movieService.InsertBookForm(movieDTO);

		logger.info("Welcome save OK!");

		return "movieBookPop";
	}

	// 영화예약내역(myMovieList.jsp) - 나의 영화예매내역으로 이동
	@RequestMapping(value = "/myMovieList.do", method = RequestMethod.GET)
	public String myMovieList(Locale locale, MovieDTO movieDTO, Model model, String date, String dateType,
			HttpSession session, HttpServletRequest request) throws Exception {
		logger.info("welcome /myMovieList !!!");

		return "myMovieList";
	}
	
	// 영화예약내역(myMovieList.jsp) - 나의 영화예매내역 조회
	@RequestMapping(value = "/selectMoreMyBook.do", method = RequestMethod.GET)
	public String selectMoreMyBook(
			Model model, MovieDTO movieDTO, HttpServletRequest request, HttpSession session
		)throws Exception {
		logger.info("welcome /selectMoreMyBook.do !!!");
		
		String mberId = (String) session.getAttribute("session_mberId");
		movieDTO.setMberId(mberId);
		
		// totalCount : 게시물 총 개수
		// page : 현재 선택한 페이지
		// listCount : 한 페이지당 보일 게시물
		// pageCount : 한 화면에 보일 최대 페이지 수
		// totalPage : 게시글에 따른 최대 페이지 수
		// startPage, endPage : 시작페이지와 끝페이지
		// stratCount, endCount : 페이지당 보일 게시글을 조회할 때 사용

		// * 총 영화 수
		MovieDTO selectMyMovieListCount = movieService.selectMyMovieListCount(movieDTO);
		int totalCount = Integer.parseInt(selectMyMovieListCount.getAllCount());
		logger.info("totalCount === " + totalCount);
		
		// * 현재 선택한 페이지
		int page = Integer.parseInt(request.getParameter("page"));
		
		// * 한 페이지에 보일 게시물 수
		int listCount = Integer.parseInt(request.getParameter("listCount"));
		
		// * 한 화면에 보일 최대 페이지 수
		int pageCount = 1;
		
		// * 총 페이지 계산 = 총 요소개수 / 한 화면에 보일 요소개수
		int totalPage = totalCount / listCount;
		// (14/10 = 1)이기 때문에 나머지 4개를 위해 1페이지 더 생성
		if (totalCount % listCount > 0) {
			totalPage++;
		}

		// (총페이지 < 현재페이지)이면 현재 페이지를 최대치의 페이지로 대체
		if (totalPage < page) {
			page = totalPage;
		}

		// * 첫페이지 계산 = (현재페이지-1 / 한 화면에 보일 최대페이지 수) * 한 화면에 보일 최대페이지 수 + 1
		// * 끝페이지 계산 = 첫페이지 + 한 화면에 보일 최대 페이지 수 - 1 
		int startPage = ((page - 1) / pageCount) * pageCount + 1;
		int endPage = startPage + pageCount - 1;

		// 1~10, 11~20 이렇게 이어지는데 게시물 부족으로 16페이지가 마지막인 경우 17~20페이지를 나타내면 안되기 때문
		if (endPage > totalPage) {
			endPage = totalPage;
		}
		
		// * 최소번호, 최대번호
		// 1페이지가 1~10의 데이터를 가져올 경우 2페이지에서 startCount는 +1을 하여 11로 맞춰야 한다.
		// 하지만 MySQL의 LIMIT함수는 시작수 다음부터 가져오기 때문에 10을 시작수로 해야 11~20을 가져올 수 있다.
		int startCount = (page - 1) * listCount; //0, 10, 20...
		int endCount = page * listCount;
		
		//예매 리스트를 조회하기 위한 컬럼
		movieDTO.setTotalCount(totalCount);
		movieDTO.setStartCount(startCount);
		movieDTO.setListCount(listCount);

		// writeValueAsString(value)은 JSON형식의 Object를 JSON형식의 String 으로 변환해주기 위함이다.
		List<MovieDTO> myMovieList = movieService.selectMyMovieList(movieDTO);
		ObjectMapper mapper = new ObjectMapper();
		String myMovieListStringJson = mapper.writeValueAsString(myMovieList);
		
		// return_MovieDetailPop_Json에서 JSON형식의 string데이터를 ajax에 전송한다.
		String totalPageNum = String.valueOf(totalPage);
		String startPageNum = String.valueOf(startPage);
		String endPageNum = String.valueOf(endPage);
		String clickPageNum = String.valueOf(page);
		String pageCountNum = String.valueOf(pageCount);
		String startCountNum = String.valueOf(startCount);
		String endCountNum = String.valueOf(endCount);
		
		logger.info("============================================================================");
		logger.info("화면당 보일 게시물 수는(listCount) === '" + listCount + "'개입니다.");
		logger.info("화면당 보일 페이지 수는(pageCountNum) === '" + pageCountNum + "'페이지입니다.");
		logger.info("조회 가능한 총 게시물 수는(totalCount) === '" + totalCount + "'개입니다.");
		logger.info("조회 가능한 총 페이지 수는(totalPageNum) === '" + totalPageNum + "'페이지입니다.");
		logger.info("선택한 페이지는(clickPageNum) === '" + clickPageNum + "'페이지입니다.");
		logger.info("조회된 첫 페이지는(startPageNum) === '" + startPageNum +"'페이지입니다.");
		logger.info("조회된 끝 페이지는(endPageNum) === '" + endPageNum + "'페이지입니다.");
		logger.info("리스트의 최소 번호는(startCountNum) === '" + startCountNum + "'번부터입니다. [SQL의 limit함수에서는 "+(startCount+1)+"이 됩니다.]");
		logger.info("리스트의 최대 번호는(endCountNum) === '" + endCountNum + "'번까지입니다.");
		logger.info("============================================================================");
		
		// 실질적으로 JSP에 필요한 정보
		model.addAttribute("totalPageNum", totalPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("myMovieListStringJson", myMovieListStringJson);
		
		return "cmn/return_MyMovieList_Json";
	}
	
	// 영화예약내역(myMovieList.jsp) - 취소신청하기
	@RequestMapping(value = "/updateBookStatus.do", method = RequestMethod.POST)
	public String updateBookStatus(Locale locale, MovieDTO movieDTO, Model model, String date, String dateType,
			HttpSession session, HttpServletRequest request) throws Exception {
		logger.info("welcome /updateBookStatus !!!");

		String mberId = (String) session.getAttribute("session_mberId");
		String bookStatus = request.getParameter("bookStatus");
		String bookNum = request.getParameter("bookNum");
		movieDTO.setMberId(mberId);
		movieDTO.setBookStatus(bookStatus);
		movieDTO.setBookNum(bookNum);

		movieService.updateBookStatus(movieDTO);

		return "myMovieList";
	}

	// 영화예약(movieBookPop.jsp) - 시간대 조회하면서 해당 시간에 남은 좌석 수 제공
	@RequestMapping(value = "/availableSeatNum.do", method = RequestMethod.GET)
	@ResponseBody
	public String availableSeatNum(Model model, MovieDTO movieDTO, HttpServletRequest request) throws Exception {
		logger.info("welcome /availableSeatNum.do !!!");

		String strDate = request.getParameter("strDate");
		String cinemaNum = request.getParameter("cinemaNum");
		String cinemaRoomNum = request.getParameter("cinemaRoomNum");
		String strTime = request.getParameter("strTime");
		String bookStatus = request.getParameter("bookStatus");
		movieDTO.setStrDate(strDate);
		movieDTO.setCinemaNum(cinemaNum);
		movieDTO.setCinemaRoomNum(cinemaRoomNum);
		movieDTO.setStrTime(strTime);
		movieDTO.setBookStatus(bookStatus);

		MovieDTO selectSeatNum = movieService.selectAvailableSeatNum(movieDTO);
		String availableSeatNum = String.valueOf(selectSeatNum.getAvailableSeatNum());

		return availableSeatNum;
	}

	// 영화예약(movieBookPop.jsp) - 시간대 조회하면서 해당 시간에 예약된 좌석 정보 제공(step2 그림)
	@RequestMapping(value = "/bookedSeatNum.do", produces = "application/text; charset=utf8", method = RequestMethod.GET)
	@ResponseBody
	public String bookedSeatNum(Model model, MovieDTO movieDTO, HttpServletRequest request) throws Exception {
		logger.info("welcome /bookedSeatNum.do !!!");

		String strDate = request.getParameter("strDate");
		String cinemaNum = request.getParameter("cinemaNum");
		String cinemaRoomNum = request.getParameter("cinemaRoomNum");
		String strTime = request.getParameter("strTime");
		String bookStatus = request.getParameter("bookStatus");
		movieDTO.setStrDate(strDate);
		movieDTO.setCinemaNum(cinemaNum);
		movieDTO.setCinemaRoomNum(cinemaRoomNum);
		movieDTO.setStrTime(strTime);
		movieDTO.setBookStatus(bookStatus);

		List<MovieDTO> selectSeatNum = movieService.selectBookedSeatNum(movieDTO);

		String str = "";
		ObjectMapper mapper = new ObjectMapper();
		str = mapper.writeValueAsString(selectSeatNum);

		return str;
	}
}