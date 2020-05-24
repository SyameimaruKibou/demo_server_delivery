package model;

import java.io.Serializable;


public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2926242598503406098L;

	private int ID;
	
	private String Uname;
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	private String PWD;

	private String nickname;
	
	public String getUname() {
		return Uname;
	}

	public void setUname(String uname) {
		Uname = uname;
	}

	public String getPWD() {
		return PWD;
	}

	public void setPWD(String pWD) {
		PWD = pWD;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getTelCode() {
		return telCode;
	}

	public void setTelCode(String telCode) {
		this.telCode = telCode;
	}

	private String telCode;
}
