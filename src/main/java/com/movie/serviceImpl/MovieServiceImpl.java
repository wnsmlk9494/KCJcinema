package com.movie.serviceImpl;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.movie.domain.MovieDTO;
import com.movie.service.MovieService;

@Repository
public class MovieServiceImpl implements MovieService{
	
	private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);
	
	@Inject
	private SqlSession sqlSession;
	
	private static final String namespace="com.movie.mappers.MovieMapper";
//	영화 정보 조회	
	@Override
	public MovieDTO selectMovieInfo(MovieDTO movieDTO) throws Exception{
		return sqlSession.selectOne(namespace+".selectMovieInfo", movieDTO);
	}
	
//	메인페이지(boxOffice.jsp) - (박스오피스, 현재상영작, 상영예정작)에서 보일 최대 영화 개수
	@Override
	public MovieDTO selectMovieListCount(MovieDTO movieDTO) throws Exception{
		
		String distincMovieList = movieDTO.getDistincMovieList();
		if(distincMovieList.equals("boxOffice"))		 {return sqlSession.selectOne(namespace+".selectboxOfficListCount", movieDTO);}
		else if(distincMovieList.equals("showingMovie")) {return sqlSession.selectOne(namespace+".selectShowingMovieListCount", movieDTO);}
		else if(distincMovieList.equals("soonMovie"))	 {return sqlSession.selectOne(namespace+".selectSoonComingListCount", movieDTO);}
		
		return null;
	}
	
	
//	메인페이지(boxOffice.jsp) - (박스오피스, 현재상영작, 상영예정작)에서 보일 영화 리스트 뷰
	@Override
	public List<MovieDTO> selectMovieList(MovieDTO movieDTO) throws Exception{
		
		String distincMovieList = movieDTO.getDistincMovieList();
		if(distincMovieList.equals("boxOffice"))		 {return sqlSession.selectList(namespace+".selectBoxOffice", movieDTO);}
		else if(distincMovieList.equals("showingMovie")) {return sqlSession.selectList(namespace+".selectShowingMovie", movieDTO);}
		else if(distincMovieList.equals("soonMovie"))	 {return sqlSession.selectList(namespace+".selectSoonMovie", movieDTO);}
		
		return null;
	}
	
	
//	영화 상세보기(movieDetailPop.jsp) - 총 평수 조회
	@Override
	public MovieDTO selectCommentCount(MovieDTO movieDTO) throws Exception{
		return sqlSession.selectOne(namespace+".selectCommentCount", movieDTO);
	}

//	영화 상세보기(movieDetailPop.jsp) - 해당 영화 평점 조회	
	public MovieDTO selectMovieAvgScore(MovieDTO movieDTO) throws Exception{
		return sqlSession.selectOne(namespace+".selectMovieAvgScore", movieDTO);
	}
	
//	영화 상세보기(movieDetailPop.jsp) - 해당 영화에 댓글이 있는지에 대한 여부 검사
	@Override
	public MovieDTO selectExistComment(MovieDTO movieDTO) throws Exception{
		return sqlSession.selectOne(namespace+".selectExistComment", movieDTO);
	}
//	영화 상세보기(movieDetailPop.jsp) - 해당 영화 로그인 유저의 댓글 불러오기
	@Override
	public List<MovieDTO> selectMyComment(MovieDTO movieDTO) throws Exception{
		return sqlSession.selectList(namespace+".selectMyComment", movieDTO);
	}	
	
//	영화 상세보기(movieDetailPop.jsp) - 해당 영화 로그인 유저 외 댓글 전체 불러오기
	@Override
	public List<MovieDTO> selectAllComment(MovieDTO movieDTO) throws Exception{
		//1페이지 당 보여줄 게시물이 6개이므로, 7개 이상이 아니라면 페이징처리를 위한 where문을 사용하지 않는다.
		if(movieDTO.getTotalCount() < 7) {
			logger.info("selectAllComment_few 조회");
			return sqlSession.selectList(namespace+".selectAllComment_few", movieDTO);
		}
		//페이징처리를 위한 where문이 삽인 되어있다.
		return sqlSession.selectList(namespace+".selectAllComment", movieDTO);
	}

//	영화 상세보기(movieDetailPop.jsp) - 댓글 등록 기록 확인(댓글 작성 창 가리기 위함)
	@Override
	public MovieDTO selectAlreadyInsertComment(MovieDTO movieDTO) throws Exception{
		return sqlSession.selectOne(namespace+".selectAlreadyInsertComment", movieDTO);
	}
	
//	영화 상세보기(movieDetailPop.jsp) - 댓글 삭제
	@Override
	public void deleteMyMovieComment(MovieDTO movieDTO) {
		sqlSession.delete(namespace+".deleteMyMovieComment", movieDTO);
	}
	
//	영화 상세보기(movieDetailPop.jsp) - 댓글 등록
	@Override
	public void insertMovieComment(MovieDTO movieDTO) {
		sqlSession.insert(namespace+".insertMovieComment", movieDTO);
	}
//	영화 상세보기(movieDetailPop.jsp) - 댓글 등록 시 tbl_movieInfo 테이블의 영화 평점 계산하여 update
	@Override
	public void updatetMovieAvgScore(MovieDTO movieDTO) {
		sqlSession.update(namespace+".updatetMovieAvgScore", movieDTO);
	}
	
//	영화 상세보기(movieDetailPop.jsp) - 댓글마다의 seqNum를 가지고 해당 댓글의 mberId값을 조회
	@Override
	public MovieDTO selectCommentId(MovieDTO movieDTO) throws Exception{
		return sqlSession.selectOne(namespace+".selectCommentId", movieDTO);
	}
	
//	영화 상세보기(movieDetailPop.jsp) - 공감관련 테이블 조회 후 count값이 1이면 이미 공감, 0이면 공감 이력 없음
	@Override
	public MovieDTO selectLikeAlreadyAccessment(MovieDTO movieDTO) throws Exception{
		return sqlSession.selectOne(namespace+".selectLikeAlreadyAccessment", movieDTO);
	}
	
//	영화 상세보기(movieDetailPop.jsp) - 비공감관련 테이블 조회 후 count값이 1이면 이미 비공감, 0이면 비공감 이력 없음
	@Override
	public MovieDTO selectDislikeAlreadyAccessment(MovieDTO movieDTO) throws Exception{
		return sqlSession.selectOne(namespace+".selectDislikeAlreadyAccessment", movieDTO);
	}
	
//	영화 상세보기(movieDetailPop.jsp)
//	선택된 공감 또는 비공감 정보로 테이블 업데이트(+1/-1)
//	로그인한 유저가 해당 영화에 '공감/비공감'을 누른다면 공감/비공감과 그 이력을 관리하는 테이블에 등록
	@Override
	public void updateLikeMovieComment(MovieDTO movieDTO) {
		String distinLike = movieDTO.getDistinLike();
		if(distinLike.equals("like")){
			sqlSession.update(namespace+".updateMovieComment_like", movieDTO);
			sqlSession.update(namespace+".insertMovieComment_like", movieDTO);
		}else if(distinLike.equals("dislike")){
			sqlSession.update(namespace+".updateMovieComment_dislike", movieDTO);
			sqlSession.update(namespace+".insertMovieComment_dislike", movieDTO);	
		}else if(distinLike.equals("unlike")){
			sqlSession.update(namespace+".updateMovieComment_UnLike", movieDTO);
			sqlSession.delete(namespace+".deleteMovieComment_like", movieDTO);
		}else if(distinLike.equals("undislike")){
			sqlSession.update(namespace+".updateMovieComment_Undislike", movieDTO);
			sqlSession.delete(namespace+".deleteMovieComment_dislike", movieDTO);
		}
	}
	
//	영화예약(movieBookPop.jsp) - 서울, 경기, 강원 등등 지역을 선택하고 각 지역마다의 지점 조회
	@Override
	public List<MovieDTO> selectMainLocation(MovieDTO movieDTO) throws Exception{
		return sqlSession.selectList(namespace+".selectMainLocation", movieDTO);
	}
	
//	영화예약(movieBookPop.jsp) - 서울, 경기, 강원 등등 지역을 선택하고 각 지역마다의 지점 조회
	@Override
	public List<MovieDTO> selectSubLocation(MovieDTO movieDTO) throws Exception{
		return sqlSession.selectList(namespace+".selectSubLocation", movieDTO);
	}
	
//	영화예약(movieBookPop.jsp) - 영화 상영정보
//	영화 상영시간 20분 전 까지는 예매가 가능하도록 상영정보 조회가 가능하다
	@Override
	public List<MovieDTO> selectMovieShowInfo(MovieDTO movieDTO) throws Exception{
		return sqlSession.selectList(namespace+".selectMovieShowInfo", movieDTO);
	}
	
//	영화예약(movieBookPop.jsp) - step2->step3 때 예약번호를 생성
	@Override
	public MovieDTO selectFindBookNum(MovieDTO movieDTO) throws Exception{
		return sqlSession.selectOne(namespace+".selectFindBookNum", movieDTO);
	}
	
//	영화예약(movieBookPop.jsp) - 시간대 조회하면서 해당 시간에 남은 좌석 수 제공
	@Override
	public MovieDTO selectAvailableSeatNum(MovieDTO movieDTO) throws Exception{
		return sqlSession.selectOne(namespace+".selectAvailableSeatNum", movieDTO);
	}
	
//	영화예약(movieBookPop.jsp) - 시간대 조회하면서 해당 시간에 예약된 좌석 정보 제공(step2 그림)
	@Override
	public List<MovieDTO> selectBookedSeatNum(MovieDTO movieDTO) throws Exception{
		return sqlSession.selectList(namespace+".selectBookedSeatNum", movieDTO);
	}
	
//	영화예약(movieBookPop.jsp) - 영화예매 정보 저장(영화예약)
	//선택한 좌석이 2개 이상인 경우 (A01,A02)의 형태로 값이 저장되기 때문에 split()로 이를 찢어 각각 배열에 담아준다.
	//배열의 내부 개수 만큼 반복문을 통해 DB에 저장할 수 있도록 한다.
	@Override
	public void InsertBookForm(MovieDTO movieDTO) {
		String seatNum = movieDTO.getSeatNum();
		String[] seatNumArray = seatNum.split(",");
		
		for(int i=0; i<seatNumArray.length; i++){
			movieDTO.setSeatNum(seatNumArray[i]);
			sqlSession.insert(namespace+".insertBooktForm", movieDTO);
		}
	}
	
	//나의영화내역(myMovieList.jsp) - 총 예매 수 조회
	@Override
	public MovieDTO selectMyMovieListCount(MovieDTO movieDTO) throws Exception{
		return sqlSession.selectOne(namespace+".selectMyMovieListCount", movieDTO);
	}
	
//	나의영화내역(myMovieList.jsp) - 로그인 된 회원의 영화 관람 내역 조회
	@Override
	public List<MovieDTO> selectMyMovieList(MovieDTO movieDTO) throws Exception{
		//1페이지 당 보여줄 게시물이 6개이므로, 7개 이상이 아니라면 페이징처리를 위한 where문을 사용하지 않는다.
		if(movieDTO.getTotalCount() < 7) {
			return sqlSession.selectList(namespace+".selectMyMovieList_few", movieDTO);		
		}
		//페이징처리를 위한 where문이 삽인 되어있다.
		return sqlSession.selectList(namespace+".selectMyMovieList", movieDTO);
	}
	
//	나의영화내역(myMovieList.jsp) - 영화 예매 취소신청
	@Override
	public void updateBookStatus(MovieDTO movieDTO) {
	   sqlSession.update(namespace+".updateBookStatus", movieDTO);
	}
}