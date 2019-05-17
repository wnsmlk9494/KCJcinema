package com.movie.domain;

public class MovieDTO {
	private String distincMovieList;
	private String mberId;
	private String date;
	private String movieNum;
	private String movieName;
	private String movieEngName;
	private String movieFilmDate;
	private String movieCountry;
	private String movieDirector;
	private String movieActor;
	private String movieAge;
	private String movieGenre;
	private String movieStory;
	private String movieImage;
	private String movieTime;
	private String subMberId;
	private String allCount;
	private String locationNum;
	private String locationName;
	private String cinemaNum;
	private String cinemaName;
	private String cinemaRoomNum;
	private String cinemaRoomName;
	private String strDate;
	private String endDate;
	private String strTime;
	private String endTime;
	private String seatNum;
	private String totalAmt;
	private String bookNum;
	private String bookStatus;
	private String movieComment;
	private String postMberId;
	private String bookDate;
	private String insertTime;
	private String updateTime;
	
	private int seqNum;
	private int availableSeatNum;
	private float movieScore;
	private int likeComment;
	private int dislikeComment;

	private float AvgValue;
	
	//데이터베이스와 관련없는 변수
	private String personCount;
	private String checkValue;
	private String distinLike;
	
	public String getSeatNum() {
		return seatNum;
	}
	public void setSeatNum(String seatNum) {
		this.seatNum = seatNum;
	}
	public String getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}
	public String getMberId() {
		return mberId;
	}
	public void setMberId(String mberId) {
		this.mberId = mberId;
	}
	public String getMovieNum() {
		return movieNum;
	}
	public void setMovieNum(String movieNum) {
		this.movieNum = movieNum;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getMovieEngName() {
		return movieEngName;
	}
	public void setMovieEngName(String movieEngName) {
		this.movieEngName = movieEngName;
	}
	public String getMovieFilmDate() {
		return movieFilmDate;
	}
	public void setMovieFilmDate(String movieFilmDate) {
		this.movieFilmDate = movieFilmDate;
	}
	public String getMovieCountry() {
		return movieCountry;
	}
	public void setMovieCountry(String movieCountry) {
		this.movieCountry = movieCountry;
	}
	public String getMovieDirector() {
		return movieDirector;
	}
	public void setMovieDirector(String movieDirector) {
		this.movieDirector = movieDirector;
	}
	public String getMovieActor() {
		return movieActor;
	}
	public void setMovieActor(String movieActor) {
		this.movieActor = movieActor;
	}
	public String getMovieAge() {
		return movieAge;
	}
	public void setMovieAge(String movieAge) {
		this.movieAge = movieAge;
	}
	public String getMovieGenre() {
		return movieGenre;
	}
	public void setMovieGenre(String movieGenre) {
		this.movieGenre = movieGenre;
	}
	public String getMovieStory() {
		return movieStory;
	}
	public void setMovieStory(String movieStory) {
		this.movieStory = movieStory;
	}
	public String getMovieImage() {
		return movieImage;
	}
	public void setMovieImage(String movieImage) {
		this.movieImage = movieImage;
	}
	public String getMovieTime() {
		return movieTime;
	}
	public void setMovieTime(String movieTime) {
		this.movieTime = movieTime;
	}
	public float getMovieScore() {
		return movieScore;
	}
	public void setMovieScore(float movieAvgScore) {
		this.movieScore = movieAvgScore;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLocationNum() {
		return locationNum;
	}
	public void setLocationNum(String locationNum) {
		this.locationNum = locationNum;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getCinemaNum() {
		return cinemaNum;
	}
	public void setCinemaNum(String cinemaNum) {
		this.cinemaNum = cinemaNum;
	}
	public String getCinemaName() {
		return cinemaName;
	}
	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
	}
	public String getCinemaRoomNum() {
		return cinemaRoomNum;
	}
	public void setCinemaRoomNum(String cinemaRoomNum) {
		this.cinemaRoomNum = cinemaRoomNum;
	}
	public String getCinemaRoomName() {
		return cinemaRoomName;
	}
	public void setCinemaRoomName(String cinemaRoomName) {
		this.cinemaRoomName = cinemaRoomName;
	}
	public String getStrDate() {
		return strDate;
	}
	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStrTime() {
		return strTime;
	}
	public void setStrTime(String strTime) {
		this.strTime = strTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getBookNum() {
		return bookNum;
	}
	public void setBookNum(String bookNum) {
		this.bookNum = bookNum;
	}
	public String getPersonCount() {
		return personCount;
	}
	public void setPersonCount(String personCount) {
		this.personCount = personCount;
	}
	public String getBookStatus() {
		return bookStatus;
	}
	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}
	public int getAvailableSeatNum() {
		return availableSeatNum;
	}
	public void setAvailableSeatNum(int availableSeatNum) {
		this.availableSeatNum = availableSeatNum;
	}
	public String getSubMberId() {
		return subMberId;
	}
	public void setSubMberId(String subMberId) {
		this.subMberId = subMberId;
	}
	public String getAllCount() {
		return allCount;
	}
	public void setAllCount(String allCount) {
		this.allCount = allCount;
	}
	public String getMovieComment() {
		return movieComment;
	}
	public void setMovieComment(String movieComment) {
		this.movieComment = movieComment;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getCheckValue() {
		return checkValue;
	}
	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}
	public int getLikeComment() {
		return likeComment;
	}
	public void setLikeComment(int likeComment) {
		this.likeComment = likeComment;
	}
	public int getDislikeComment() {
		return dislikeComment;
	}
	public void setDislikeComment(int dislikeComment) {
		this.dislikeComment = dislikeComment;
	}
	public int getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}
	public String getDistinLike() {
		return distinLike;
	}
	public void setDistinLike(String distinLike) {
		this.distinLike = distinLike;
	}
	public String getPostMberId() {
		return postMberId;
	}
	public void setPostMberId(String postMberId) {
		this.postMberId = postMberId;
	}
	
//	toString
//	@Override
//	public String toString() {
//		return "movieDTO [movieNum=" + movieNum+ "," + 
//						 "movieName=" + movieName + "," +
//						 "movieEngName=" + movieEngName + "," +
//						 "movieImage=" + movieImage +"]";
//	}
	
	//페이징처리를 위한 변수
	private int totalCount;
	private int listCount;
	private int totalPage;
	private int page; //현재 보고있는 페이지
	private int pageCount;
	private int startPage;
	private int endPage;
	private int startCount;
	private int endCount;
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getListCount() {
		return listCount;
	}
	public void setListCount(int listCount) {
		this.listCount = listCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getStartCount() {
		return startCount;
	}
	public void setStartCount(int startCount) {
		this.startCount = startCount;
	}
	public int getEndCount() {
		return endCount;
	}
	public void setEndCount(int endCount) {
		this.endCount = endCount;
	}
	public float getAvgValue() {
		return AvgValue;
	}
	public void setAvgValue(float avgValue) {
		AvgValue = avgValue;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getBookDate() {
		return bookDate;
	}
	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}
	public String getDistincMovieList() {
		return distincMovieList;
	}
	public void setDistincMovieList(String distincMovieList) {
		this.distincMovieList = distincMovieList;
	}
	
	//toString
	//	@Override
	//	public String toString() {
	//		return "movieDTO [movieNum=" + movieNum+ "," + 
	//						 "movieName=" + movieName + "," +
	//						 "movieEngName=" + movieEngName + "," +
	//						 "movieFilmDate=" + movieFilmDate + "," +
	//						 "movieCountry=" + movieCountry + "," +
	//						 "movieDirector=" + movieDirector + "," +
	//						 "movieActor=" + movieActor + "," +
	//						 "movieAge=" + movieAge + "," +
	//						 "movieGenre=" + movieGenre + "," +
	//						 "movieTime=" + movieTime + "," +
	//						 "movieStory=" + movieStory + "," +
	//						 "cinemaNum=" + cinemaNum + "," +
	//						 "cinemaName=" + cinemaName + "," +
	//						 "cinemaLocate=" + cinemaLocate + "," +
	//						 "roomNum=" + roomNum + "," +
	//						 "roomName=" + roomName + "," +
	//						 "movieStr=" + movieStr + "," +
	//						 "movieEnd=" + movieEnd + "," +
	//						 "seatMaxCnt=" + seatMaxCnt + "," +
	//						 "seatNowCnt=" + seatNowCnt + "," +
	//						 "movieImage=" + movieImage +"]";
	//	}
}
