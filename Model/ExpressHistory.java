package ts.model;

import java.io.Serializable;
import java.util.Date;

public class ExpressHistory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6973429316324711538L;

	public ExpressHistory() {
	}
	//唯一标识符

	private int SN;
	
	//对应快件ID
	private String ExpressID;

	//发生操作的ID
	private int actId;
	
	//执行该操作的用户id
	private int userId;
	
	//操作发生的时间
	private Date actTime;
	
	//操作发生的节点位置（如果有的话）
	private int sourcenode;
	
	//操作的目标节点位置（如果有的话，比如）
	private int targetnode;
	

	private String userName;
	
	
	private String userTel;
	
	
	private String sourceNodeName;
	
	
	private String targetNodeName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getSourceNodeName() {
		return sourceNodeName;
	}

	public void setSourceNodeName(String sourceNodeName) {
		this.sourceNodeName = sourceNodeName;
	}

	public String getTargetNodeName() {
		return targetNodeName;
	}

	public void setTargetNodeName(String targetNodeName) {
		this.targetNodeName = targetNodeName;
	}

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
	
	
	public void setY(Float value) {
		this.y = value;
	}
	
	public Float getY() {
		return y;
	}
	
	
	public int getActId() {
		return actId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}


	public void setActId(int actId) {
		this.actId = actId;
	}

	public void setExpressID(String expressID){
		this.ExpressID = expressID;
	}

	public String getExpressID(){
		return this.ExpressID;
	}

	public int getSourcenode() {
		return sourcenode;
	}

	public void setSourcenode(int sourcenode) {
		this.sourcenode = sourcenode;
	}

	public int getTargetnode() {
		return targetnode;
	}

	public void setTargetnode(int targetnode) {
		this.targetnode = targetnode;
	}

	public String toString() {
		return toString(false);
	}
	
	public ExpressSheet getEps() {
		return eps;
	}

	public void setEps(ExpressSheet eps) {
		this.eps = eps;
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
			sb.append("TransHistory[ ");
			sb.append("SN=").append(getSN()).append(" ");
			sb.append("ExpressID=").append(getExpressID()).append(" ");
			sb.append("ActId=").append(getActId()).append(" ");
			sb.append("UserId").append(getUserId()).append(" ");
			sb.append("ActTime=").append(getActTime()).append(" ");
			sb.append("Sourcenode=").append(getSourcenode()).append(" ");
			sb.append("Targetnode=").append(getTargetnode()).append(" ");
			//
			sb.append("X=").append(getX()).append(" ");
			sb.append("Y=").append(getY()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}

	public static final class ACTID{
		public static final int ACT_CREATE = 0;	//揽件
		public static final int ACT_PACK = 1; //打包
		public static final int ACT_UNPACK = 2; //拆包
		public static final int ACT_TRANPORTING_LEAVE = 3; //离开节点
		public static final int ACT_TRANPORTING_REACH = 4; //到达节点
		public static final int ACT_DELIVERING = 5; //快递员派送中
		public static final int ACT_ASSIGNED = 6; //已签收
	}
	
}
