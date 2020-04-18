package ts.model;

import java.io.Serializable;
import java.util.Date;

public class TemporaryExpress implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4941503468986892397L;

	public TemporaryExpress() {
	}
	
	private int SN;
	
	private int type;
	
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
	
	//【选择建包裹到哪个节点处理】
	private int sourceNode;
	
//	@OneToMany(mappedBy="express", targetEntity=TransPackageContent.class)	
//	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
//	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)	
//	private java.util.Set<TransPackageContent> transPackageContent = new java.util.HashSet<TransPackageContent>();
	
	public void setSN(int value) {
		this.SN = value;
	}
	
	public int getSN() {
		return SN;
	}

	public int getORMID() {
		return getSN();
	}
	
	public String getSenderaddr() {
		return senderaddr;
	}

	public String getSenderregcode() {
		return senderregcode;
	}

	public void setSenderregcode(String senderregcode) {
		this.senderregcode = senderregcode;
	}

	public void setSourceNode(int sourceNode) {
		this.sourceNode = sourceNode;
	}

	public void setSenderaddr(String senderaddr) {
		this.senderaddr = senderaddr;
	}
	
	public void setType(int value) {
		this.type = value;
	}
	
	public int getType() {
		return type;
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
			return String.valueOf(getSN());
		}
		else 	
			return "ExpressSheet [SN=" + SN + ", type=" + type + ", sendername=" + sendername + ", sendertel=" + sendertel
					+ ", senderregcode=" + senderregcode + ", senderaddr=" + senderaddr + ", recievername=" + recievername
					+ ", recievertel=" + recievertel + ", recieverregcode=" + recieverregcode + ", recieveraddr="
					+ recieveraddr + "]";
	}
	
}
