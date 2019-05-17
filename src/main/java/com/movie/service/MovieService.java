package com.movie.service;

import java.util.List;

import com.movie.domain.MovieDTO;

public interface MovieService {
	//영화 정보 조회
	public MovieDTO selectMovieInfo(MovieDTO movieDTO) throws Exception;
	
	//메인페이지(home.jsp) - 영화 리스트 뷰
	public List<MovieDTO> selectMovieList(MovieDTO movieDTO) throws Exception;
	
	//메인페이지(boxOffice.jsp) - (박스오피스, 현재상영작, 상영예정작)에서 보일 최대 영화 개수
	public MovieDTO selectMovieListCount(MovieDTO movieDTO) throws Exception;
	
	//영화 상세보기(movieDetailPop.jsp) - 총 평수 조회
	public MovieDTO selectCommentCount(MovieDTO movieDTO) throws Exception;
	
	//영화 상세보기(movieDetailPop.jsp) - 영화 평점 조회
	public MovieDTO selectMovieAvgScore(MovieDTO movieDTO) throws Exception;
	
	//영화 상세보기(movieDetailPop.jsp) - 해당 영화에 댓글이 있는지에 대한 여부 검사
	public MovieDTO selectExistComment(MovieDTO movieDTO) throws Exception;
	//영화 상세보기(movieDetailPop.jsp) - 해당 영화 로그인 유저의 댓글 불러오기
	public List<MovieDTO> selectMyComment(MovieDTO movieDTO) throws Exception;
	//영화 상세보기(movieDetailPop.jsp) - 해당 영화 로그인 유저 외 댓글 전체 불러오기
	public List<MovieDTO> selectAllComment(MovieDTO movieDTO) throws Exception;
	
	//영화 상세보기(movieDetailPop.jsp) - 댓글 등록 기록 확인(댓글 작성 창 가리기 위함)
	public MovieDTO selectAlreadyInsertComment(MovieDTO movieDTO) throws Exception;
	
	
	//영화 상세보기(movieDetailPop.jsp) - 댓글 삭제
	public void deleteMyMovieComment(MovieDTO movieDTO);
	
	//영화 상세보기(movieDetailPop.jsp) - 댓글 등록
	public void insertMovieComment(MovieDTO movieDTO);
	//영화 상세보기(movieDetailPop.jsp) - 댓글 등록 시 tbl_movieInfo 테이블의 영화 평점 계산하여 update
	public void updatetMovieAvgScore(MovieDTO movieDTO);
	
	//영화 상세보기(movieDetailPop.jsp) - 댓글마다의 seqNum를 가지고 해당 댓글의 mberId값을 조회
	public MovieDTO selectCommentId(MovieDTO movieDTO) throws Exception;
	
	//영화 상세보기(movieDetailPop.jsp) - 공감관련 테이블 조회 후 count값이 1이면 이미 공감, 0이면 공감 이력 없음
	public MovieDTO selectLikeAlreadyAccessment(MovieDTO movieDTO) throws Exception;
	
	//영화 상세보기(movieDetailPop.jsp) - 비공감관련 테이블 조회 후 count값이 1이면 이미 비공감, 0이면 비공감 이력 없음
	public MovieDTO selectDislikeAlreadyAccessment(MovieDTO movieDTO) throws Exception;
	
	//영화 상세보기(movieDetailPop.jsp) - 선택된 공감 또는 비공감 정보로 테이블 업데이트(+1/-1)
	public void updateLikeMovieComment(MovieDTO movieDTO);
	
	//영화예약(movieBookPop.jsp) - 서울, 경기, 강원 등등 지역을 선택하고 각 지역마다의 지점 조회
	public List<MovieDTO> selectMainLocation(MovieDTO movieDTO) throws Exception;
	public List<MovieDTO> selectSubLocation(MovieDTO movieDTO) throws Exception;
	
	//영화예약(movieBookPop.jsp) - 영화 상영정보
	//영화 상영시간 20분 전 까지는 예매가 가능하도록 상영정보 조회가 가능하다
	public List<MovieDTO> selectMovieShowInfo(MovieDTO movieDTO) throws Exception;
	
	//영화예약(movieBookPop.jsp) - step2->step3 때 예약번호를 생성
	public MovieDTO selectFindBookNum(MovieDTO movieDTO) throws Exception;
	
	//영화예약(movieBookPop.jsp) - 시간대 조회하면서 해당 시간에 남은 좌석 수 제공
	public MovieDTO selectAvailableSeatNum(MovieDTO movieDTO) throws Exception;

	//영화예약(movieBookPop.jsp) - 시간대 조회하면서 해당 시간에 예약된 좌석 정보 제공(step2 그림)
	public List<MovieDTO> selectBookedSeatNum(MovieDTO movieDTO) throws Exception;
	
	//영화예약(movieBookPop.jsp) - 영화예매 정보 저장(영화예약)
	public void InsertBookForm(MovieDTO movieDTO);
	
	//나의영화내역(myMovieList.jsp) - 총 예매 수 조회
	public MovieDTO selectMyMovieListCount(MovieDTO movieDTO) throws Exception;
	
	//나의영화내역(myMovieList.jsp) - 로그인 된 회원의 영화 관람 내역 조회
	public List<MovieDTO> selectMyMovieList(MovieDTO movieDTO) throws Exception;
	
	//나의영화내역(myMovieList.jsp) - 영화 예매 취소신청
	public void updateBookStatus(MovieDTO movieDTO);
}
	