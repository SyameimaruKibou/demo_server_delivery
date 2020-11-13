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
package ts.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="ExpressHistory")
@XmlRootElement(name="ExpressHistory")
public class ExpressHistory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6973429316324711538L;

	public ExpressHistory() {
	}
	
	@Column(name="SN", nullable=false)	
	@Id	
	@GeneratedValue(generator="MODEL_EXPRESSHISTORY_SN_GENERATOR")	
	@org.hibernate.annotations.GenericGenerator(name="MODEL_EXPRESSHISTORY_SN_GENERATOR", strategy="native")	
	private int SN;
	
	@ManyToOne(targetEntity=ExpressSheet.class, fetch=FetchType.LAZY)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="ExpressID", referencedColumnName="ID", nullable=false) })	
	private ExpressSheet eps;

	@Column(name="ActId", nullable=false, length=4)	
	private int actId;
	
	@ManyToOne(targetEntity=UserInfo.class, fetch=FetchType.LAZY)
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.LOCK})
	@JoinColumns({ @JoinColumn(name="UserId", referencedColumnName="UID",nullable=false) })
	private UserInfo user;
	
	@Column(name="ActTime", nullable=false)	
	private Date actTime;
	
	@ManyToOne(targetEntity=TransNode.class, fetch=FetchType.LAZY)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="SourceNode", referencedColumnName="ID", nullable=true) })	
	private TransNode sourcenode;
	
	@ManyToOne(targetEntity=TransNode.class, fetch=FetchType.LAZY)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="TargetNode", referencedColumnName="ID", nullable=true) })	
	private TransNode targetnode;
	
	@Column(name="x", nullable=true)	
	private Float x;
	
	@Column(name="y", nullable=true)	
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
