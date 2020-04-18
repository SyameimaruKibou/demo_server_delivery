package ts.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

public class ExpressSheet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4941503468986892397L;

	public ExpressSheet() {
	}
	//唯一标识码
	private String ID;
	
	//发件人姓名，电话号码，省市区Code，详细地址
	private String sendername;
	
	private String sendertel;

	private String senderregcode;
	
	private String senderaddr;

	//收件人姓名，电话号码，省市区Code，详细地址
	private String recievername;

	private String recievertel;
	
	private String recieverregcode;
	
	private String recieveraddr;
	
	//以下属性可以暂时忽略
	private Float weight;

	private int tranway;
	
	private Float tranFee;
	
	private Date createTime;
	
	private Date finishTime;
	
	private int status;
	
	private String Note;
	
	private int type;
	
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
	
	public void setTranFee(Float value) {
		this.tranFee = value;
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

	
	public String getRecievername() {
		return recievername;
	}

	public void setRecievername(String recievername) {
		this.recievername = recievername;
	}

	public String getRecievertel() {
		return recievertel;
	}

	public void setRecievertel(String recievertel) {
		this.recievertel = recievertel;
	}

	public String getRecieverregcode() {
		return recieverregcode;
	}

	public void setRecieverregcode(String recieverregcode) {
		this.recieverregcode = recieverregcode;
	}

	public String getRecieveraddr() {
		return recieveraddr;
	}

	public void setRecieveraddr(String recieveraddr) {
		this.recieveraddr = recieveraddr;
	}

	public int getTranway() {
		return tranway;
	}

	public void setTranway(int tranway) {
		this.tranway = tranway;
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

	public java.util.Set<ExpressHistory> getHistory() {
		return history;
	}

	public void setHistory(java.util.Set<ExpressHistory> history) {
		this.history = history;
	}

	public Float getTranFee() {
		return tranFee;
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
				+ ", senderregcode=" + senderregcode + ", senderaddr=" + senderaddr + ", recievername=" + recievername
				+ ", recievertel=" + recievertel + ", recieverregcode=" + recieverregcode + ", recieveraddr="
				+ recieveraddr + ", weight=" + weight + ", tranway=" + tranway + ", tranFee=" + tranFee
				+ ", createTime=" + createTime + ", finishTime=" + finishTime + ", status=" + status + ", Note=" + Note
				+ ", history=" + history + ", _saved=" + _saved + "]";
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
		public static final int CREATING = -1; //创建中
		public static final int UNPACKED = 0; //未在包裹中
		public static final int PACKED = 1;  //已在包裹中
		public static final int DELIVERIED = 2; //已经送达
	}
}
