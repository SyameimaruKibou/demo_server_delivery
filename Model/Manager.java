package model;

import java.io.Serializable;
import java.util.Date;

public class Manager implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3623315447826851447L;

	private int ID;
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	private String username;
	
	private String PWD;
	
	private Date lastLoginTime;
	
	private String lastLoginIP;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPWD() {
		return PWD;
	}

	public void setPWD(String pWD) {
		PWD = pWD;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIP() {
		return lastLoginIP;
	}

	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}
	
	
	
}
