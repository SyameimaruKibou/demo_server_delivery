/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: 
 * License Type: Evaluation
 */
package model;

import java.io.Serializable;
import java.util.Date;

import model.*;

public class TransHistory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6973429316324711538L;

	public TransHistory() {
	}
	private int SN;
	
	private TransPackage pkg;
	
	private int actId;
	
	private int userId;
	
	private Date actTime;
	
	private TransNode sourcenode;
	
	private TransNode targetnode;
	
	private Float x;
	
	private Float y;
	
	public void setSN(int value) {
		this.SN = value;
	}
	
	public int getSN() {
		return SN;
	}
	
	public int getORMID() {
		return getSN();
	}
	
	public void setActTime(Date value) {
		this.actTime = value;
	}
	
	public Date getActTime() {
		return actTime;
	}
	
	
	public void setX(Float value) {
		this.x = value;
	}
	
	public Float getX() {
		return x;
	}
	
	public void setY(Float value) {
		this.y = value;
	}
	
	public Float getY() {
		return y;
	}
	
	public void setPkg(TransPackage value) {
		this.pkg = value;
	}
	
	public TransPackage getPkg() {
		return pkg;
	}

	public int getActId() {
		return actId;
	}

	public void setActId(int actId) {
		this.actId = actId;
	}


	public TransNode getSourcenode() {
		return sourcenode;
	}

	public void setSourcenode(TransNode sourcenode) {
		this.sourcenode = sourcenode;
	}

	public TransNode getTargetnode() {
		return targetnode;
	}

	public void setTargetnode(TransNode targetnode) {
		this.targetnode = targetnode;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getSN());
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("TransHistory[ ");
			sb.append("SN=").append(getSN()).append(" ");
			if (getPkg() != null)
				sb.append("Pkg.Persist_ID=").append(getPkg().toString(true)).append(" ");
			else
				sb.append("Pkg=null ");
			sb.append("ActId=").append(getActId()).append(" ");
			sb.append("ActTime=").append(getActTime()).append(" ");
			//
			sb.append("X=").append(getX()).append(" ");
			sb.append("Y=").append(getY()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}

	public static final class ACTID{
		public static final int ACT_CREATE = 0; 
		public static final int ACT_TRANPORTING_LEAVE = 3;
		public static final int ACT_TRANPORTING_REACH = 4;
		public static final int ACT_FINISH = 6; 
	}
	
}
