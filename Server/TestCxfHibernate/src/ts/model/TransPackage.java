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
import javax.xml.bind.annotation.XmlTransient;
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="TransPackage")
@XmlRootElement(name="TransPackage")
public class TransPackage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3050390478904210174L;

	public TransPackage() {
	}
	
	@Column(name="ID", nullable=false)	
	@Id	
	@GeneratedValue(generator="MODEL_TRANSPACKAGE_ID_GENERATOR")	
	@org.hibernate.annotations.GenericGenerator(name="MODEL_TRANSPACKAGE_ID_GENERATOR", strategy="uuid")	
	private String ID;
	
	@ManyToOne(targetEntity=TransNode.class, fetch=FetchType.LAZY)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="SourceNode", referencedColumnName="ID", nullable=false) })	
	private TransNode sourcenode;
	
	@ManyToOne(targetEntity=TransNode.class, fetch=FetchType.LAZY)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="TargetNode", referencedColumnName="ID", nullable=true) })	
	private TransNode targetnode;
	
	@Column(name="CreateTime", nullable=true)	
	private Date createTime;
	
	@Column(name="Status", nullable=true, length=4)	
	private Integer status;
	
	
	@OneToMany(mappedBy="pkg", targetEntity=TransPackageContent.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)	
	private java.util.Set<TransPackageContent> content = new java.util.HashSet<TransPackageContent>();
	
	@OneToMany(mappedBy="pkg", targetEntity=TransHistory.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)	
	private java.util.Set<TransHistory> history = new java.util.HashSet<TransHistory>();
	
	public void setID(String value) {
		this.ID = value;
	}
	
	public String getID() {
		return ID;
	}
	
	public String getORMID() {
		return getID();
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
	
	
	
	public void setContent(java.util.Set<TransPackageContent> value) {
		this.content = value;
	}
	@XmlTransient
	public java.util.Set<TransPackageContent> getContent() {
		return content;
	}
	
	
	public void setHistory(java.util.Set<TransHistory> value) {
		this.history = value;
	}
	@XmlTransient
	public java.util.Set<TransHistory> getHistory() {
		return history;
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
			sb.append("SourceNode=").append(getSourcenode().getNodeName()).append(" ");
			sb.append("TargetNode=").append(getTargetnode().getNodeName()).append(" ");
			sb.append("CreateTime=").append(getCreateTime()).append(" ");
			sb.append("Status=").append(getStatus()).append(" ");
			sb.append("Content.size=").append(getContent().size()).append(" ");
			sb.append("History.size=").append(getHistory().size()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
	
	@Transient	
	private boolean _saved = false;
	
	public void onSave() {
		_saved=true;
	}
	
	
	public void onLoad() {
		_saved=true;
	}
	
	
	public boolean isSaved() {
		return _saved;
	}
	
	public static final class STATUS{
		public static final int IN_NODE = 0;
		public static final int ON_RODE = 1;
		public static final int DELIVERIED = 2;
	}
}
