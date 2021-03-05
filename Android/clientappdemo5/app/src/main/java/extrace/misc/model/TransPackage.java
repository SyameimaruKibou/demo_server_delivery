
package extrace.misc.model;
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

	private TransNode sourcenode;

	private TransNode targetnode;

	private Date createTime;

	private Integer status;

	private java.util.Set<TransPackageContent> content = new java.util.HashSet<TransPackageContent>();

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
	public java.util.Set<TransPackageContent> getContent() {
		return content;
	}


	public void setHistory(java.util.Set<TransHistory> value) {
		this.history = value;
	}
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