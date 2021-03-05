package extrace.misc.model;
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

    private String sendername;

    private String sendertel;

    private String senderregcode;

    private String senderaddr;

    private String receivername;

    private String receivertel;

    private String receiverregcode;

    private String receiveraddr;

    private String note;

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
                    + ", senderregcode=" + senderregcode + ", senderaddr=" + senderaddr + ", receivername=" + receivername
                    + ", receivertel=" + receivertel + ", receiverregcode=" + receiverregcode + ", receiveraddr="
                    + receiveraddr + "]";
    }

}