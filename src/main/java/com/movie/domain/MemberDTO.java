package com.movie.domain;

public class MemberDTO {
	
	private String mberName;
	private String mberId;
	private String mberPwd;
	private String mberBirth;
	private String mberSexCode;
	private String mberSex;
	private String mberTel;
	private String mberEmail;
	private String authorization;
	private String movieNum;
	private String cmtContent;
	private String salt;
	private String insertTime;
	private String updateTime;
	
	private int cmtNum;
	private int starCnt;
	
	//데이터베이스와 관련없는 변수
	private String checkValue;
	
	
	public String getMberName() {
		return mberName;
	}
	public void setMberName(String mberName) {
		this.mberName = mberName;
	}
	public String getMberId() {
		return mberId;
	}
	public void setMberId(String mberId) {
		this.mberId = mberId;
	}
	public String getMberPwd() {
		return mberPwd;
	}
	public void setMberPwd(String mberPwd) {
		this.mberPwd = mberPwd;
	}
	public String getMberBirth() {
		return mberBirth;
	}
	public void setMberBirth(String mberBirth) {
		this.mberBirth = mberBirth;
	}
	public String getMberTel() {
		return mberTel;
	}
	public void setMberTel(String mberTel) {
		this.mberTel = mberTel;
	}
	public String getMberSexCode() {
		return mberSexCode;
	}
	public void setMberSexCode(String mberSexCode) {
		this.mberSexCode = mberSexCode;
	}
	public String getAuthorization() {
		return authorization;
	}
	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}
	public String getMberEmail() {
		return mberEmail;
	}
	public void setMberEmail(String mberEmail) {
		this.mberEmail = mberEmail;
	}
	public String getMberSex() {
		return mberSex;
	}
	public void setMberSex(String mberSex) {
		this.mberSex = mberSex;
	}
	public int getCmtNum() {
		return cmtNum;
	}
	public void setCmtNum(int cmtNum) {
		this.cmtNum = cmtNum;
	}
	public String getCmtContent() {
		return cmtContent;
	}
	public void setCmtContent(String cmtContent) {
		this.cmtContent = cmtContent;
	}
	public int getStarCnt() {
		return starCnt;
	}
	public void setStarCnt(int starCnt) {
		this.starCnt = starCnt;
	}
	public String getMovieNum() {
		return movieNum;
	}
	public void setMovieNum(String movieNum) {
		this.movieNum = movieNum;
	}
	
	//toString
	@Override
	public String toString() {
		return "MemberDTO ["+ 
				"mberName=" + mberName + "," +
				" mberId=" + mberId + "," + 
				" mberPwd=" + mberPwd + "," + 
				" mberBirth=" + mberBirth + "," + 
				" mberSex=" + mberSex + "," + 
				" mberTel=" + mberTel + "," + 
				" mberEmail=" + mberEmail + "]";
	}
	public String getCheckValue() {
		return checkValue;
	}
	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
