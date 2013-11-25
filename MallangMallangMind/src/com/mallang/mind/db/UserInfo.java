package com.mallang.mind.db;

public class UserInfo {
	private String userID;
	private String name;
	private String nick;
	private String gender;
	private int regDate;
	private int birthDate;
	private String national;
	private String city;
	//now getter and setter...
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserID() {
		return userID;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setNick(String nick){
		this.nick = nick;
	}
	public String getNick() {
		return nick;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getGender() {
		return gender;
	}
	public void setRegDate(int regDate) {
		this.regDate = regDate;
	}
	public int getRegDate() {
		return regDate;
	}
	public void setBirthDate(int birthDate) {
		this.birthDate = birthDate;
	}
	public int getBirthDate() {
		return birthDate;
	}
	public void setNational(String national) {
		this.national = national;
	}
	public String getNational() {
		return national;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCity() {
		return city;
	}
}
