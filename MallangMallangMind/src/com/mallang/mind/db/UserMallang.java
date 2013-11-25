package com.mallang.mind.db;

public class UserMallang {
	private String userID;
	private int mediType;
	private int mediCount;
	private int mediTime;
	private int chakra;
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserID() {
		return userID;
	}
	public void setMediType(int mediType) {
		this.mediType = mediType;
	}
	public int getMediType() {
		return mediType;
	}
	public void setMediCount(int mediCount) {
		this.mediCount = mediCount;
	}
	public int getMediCount() {
		return mediCount;
	}
	public void setMediTime(int mediTime) {
		this.mediTime = mediTime;
	}
	public int getMediTime() {
		return mediTime;
	}
	public void setChakra(int chakra) {
		this.chakra = chakra;
	}
	public int getChakra() {
		return chakra;
	}
}
