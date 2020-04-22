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

public class ExpressHistory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6973429316324711538L;

	public ExpressHistory() {
	}
	
	private int SN;
	
	private ExpressSheet eps;

	private int actId;
	
	private UserInfo user;
	
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
	
	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
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

	public void setX(Float value) {
		this.x = value;
	}
	
	
	public void setY(Float value) {
		this.y = value;
	}
	
	public Float getY() {
		return y;
	}
	

	public int getActId() {
		return actId;
	}


	public void setActId(int actId) {
		this.actId = actId;
	}
	public String toString() {
		return toString(false);
	}
	
	public Float getX() {
		return x;
	}

	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getSN());
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("ExpressHistory[ ");
			sb.append("SN=").append(getSN()).append(" ");
			sb.append("ActId=").append(getActId()).append(" ");
			sb.append("ActTime=").append(getActTime()).append(" ");
			sb.append("UserId=").append(user.getUID()).append(" ");
			sb.append("SourceNode=").append(sourcenode.getNodeName()).append(" ");
			sb.append("TargetNode=").append(targetnode.getNodeName()).append(" ");
			sb.append("X=").append(getX()).append(" ");
			sb.append("Y=").append(getY()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
	public ExpressSheet getEps() {
		return eps;
	}

	public void setEps(ExpressSheet eps) {
		this.eps = eps;
	}

	public static final class ACTID{
		public static final int ACT_CREATE = 0;		//快件刚建立
		public static final int ACT_PACK = 1;		//快件被打包
		public static final int ACT_UNPACK = 2;		//快件被拆包
		public static final int ACT_TRANPORTING_LEAVE = 3;	//快件（在包裹中）离开某节点
		public static final int ACT_TRANPORTING_REACH = 4;	//快件（在包裹中）到达某节点
		public static final int ACT_DELIVERING = 5;	//快件在派送中
		public static final int ACT_ASSIGNED = 6;	//快件被签收
	}
	
}
