
package ts.model;

import java.io.Serializable;
import java.util.Date;

public class TransPackage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3050390478904210174L;

	public TransPackage() {
	}

	private String ID;
	
	//出发节点
	private int sourceNode;
	
	//目标节点	
	private int targetNode;
	
	//创建时间
	private Date createTime;
	
	//状态
	private int status;
	
	
	public void setID(String value) {
		this.ID = value;
	}
	
	public String getID() {
		return ID;
	}
	
	public String getORMID() {
		return getID();
	}
	
	public int getSourceNode() {
		return sourceNode;
	}

	public void setSourceNode(int sourceNode) {
		this.sourceNode = sourceNode;
	}

	public int getTargetNode() {
		return targetNode;
	}

	public void setTargetNode(int targetNode) {
		this.targetNode = targetNode;
	}

	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getID());
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("TransPackage[ ");
			sb.append("ID=").append(getID()).append(" ");
			sb.append("SourceNode=").append(getSourceNode()).append(" ");
			sb.append("TargetNode=").append(getTargetNode()).append(" ");
			sb.append("CreateTime=").append(getCreateTime()).append(" ");
			sb.append("Status=").append(getStatus()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
	
	public static final class STATUS{
		public static final int IN_NODE = 0;  //在节点中
		public static final int ON_RODE = 1;  //在运输途中
		public static final int DELIVERIED = 2; //已送达（已失效）
	}
}
