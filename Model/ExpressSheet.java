package model;

import java.io.Serializable;
import java.util.Date;
import model.*;

public class ExpressSheet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4941503468986892397L;

	public ExpressSheet() {
	}
	
	private String ID;
	
	private int type;
	
	private String sendername;
	
	private String sendertel;
	
	private String senderregcode;
	
	private String senderaddr;
	
	private String receivername;
	
	private String receivertel;
	
	private String receiverregcode;
	
	private String receiveraddr;
	
	private java.util.Set<ExpressHistory> history = new java.util.HashSet<ExpressHistory>();
	
	public java.util.Set<ExpressHistory> getHistory() {
		return history;
	}

	public void setHistory(java.util.Set<ExpressHistory> history) {
		this.history = history;
	}

	private Float weight;

	private int transway;
	
	private Float transFee;
	
	private Date createTime;
	
	private Date finishTime;
	
	private int status;
	
	private TransNode nextnode;
	
	public TransNode getNextnode() {
		return nextnode;
	}

	public void setNextnode(TransNode nextnode) {
		this.nextnode = nextnode;
	}


	private String Note;
	
	
	
//	@OneToMany(mappedBy="express", targetEntity=TransPackageContent.class)	
//	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
//	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)	
//	private java.util.Set<TransPackageContent> transPackageContent = new java.util.HashSet<TransPackageContent>();
	
	public void setID(String value) {
		this.ID = value;
	}
	
	public String getID() {
		return ID;
	}
	
	public String getORMID() {
		return getID();
	}
	
	public void setType(int value) {
		this.type = value;
	}
	
	public int getType() {
		return type;
	}

	public String getSenderregcode() {
		return senderregcode;
	}

	public void setSenderregcode(String senderregcode) {
		this.senderregcode = senderregcode;
	}

	
	public void setWeight(Float value) {
		this.weight = value;
	}
	
	public Float getWeight() {
		return weight;
	}
	
	public void setTransFee(Float value) {
		this.transFee = value;
	}
	
	public String getSendername() {
		return sendername;
	}

	public void setSendername(String sendername) {
		this.sendername = sendername;
	}

	public String getSendertel() {
		return sendertel;
	}

	public void setSendertel(String sendertel) {
		this.sendertel = sendertel;
	}

	public String getSenderaddr() {
		return senderaddr;
	}

	public void setSenderaddr(String senderaddr) {
		this.senderaddr = senderaddr;
	}

	public boolean is_saved() {
		return _saved;
	}

	public void set_saved(boolean _saved) {
		this._saved = _saved;
	}

	
	public String getReceivername() {
		return receivername;
	}

	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}

	public String getReceivertel() {
		return receivertel;
	}

	public void setReceivertel(String receivertel) {
		this.receivertel = receivertel;
	}

	public String getReceiverregcode() {
		return receiverregcode;
	}

	public void setReceiverregcode(String receiverregcode) {
		this.receiverregcode = receiverregcode;
	}

	public String getReceiveraddr() {
		return receiveraddr;
	}

	public void setReceiveraddr(String receiveraddr) {
		this.receiveraddr = receiveraddr;
	}

	public int getTransway() {
		return transway;
	}

	public void setTransway(int transway) {
		this.transway = transway;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getNote() {
		return Note;
	}

	public void setNote(String note) {
		Note = note;
	}
	public Float getTransFee() {
		return transFee;
	}
	
	
//	public void setTransPackageContent(java.util.Set<TransPackageContent> value) {
//		this.transPackageContent = value;
//	}
//	
//	public java.util.Set<TransPackageContent> getTransPackageContent() {
//		return transPackageContent;
//	}
	
	
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getID());
		}
		else {
		return "ExpressSheet [ID=" + ID + ", type=" + type + ", sendername=" + sendername + ", sendertel=" + sendertel
				+ ", senderregcode=" + senderregcode + ", senderaddr=" + senderaddr + ", receivername=" + receivername
				+ ", receivertel=" + receivertel + ", receiverregcode=" + receiverregcode + ", receiveraddr="
				+ receiveraddr + ", weight=" + weight + ", transway=" + transway + ", transFee=" + transFee
				+ ", createTime=" + createTime + ", finishTime=" + finishTime + ", status=" + status + ", Note=" + Note
				+ ", _saved=" + _saved + "]";
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
		public static final int CREATING = -1; //����
		public static final int UNPACKED = 0;
		public static final int PACKED = 1; 
		public static final int DELIVERIED = 2;
	}
}
