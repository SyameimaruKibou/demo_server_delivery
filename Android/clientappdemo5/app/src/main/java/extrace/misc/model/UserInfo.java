package extrace.misc.model;
import java.io.Serializable;
public class UserInfo implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 6899152987896840262L;

	public UserInfo() {
	}

	private int UID;

	private String Uname;

	private String PWD;

	private String name;

	private Integer URole;

	private String telCode;

	private Integer status;

	private float x;

	private float y;

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setUID(int value) {
		this.UID = value;
	}

	public int getUID() {
		return UID;
	}

	public int getORMID() {
		return getUID();
	}

	public void setPWD(String value) {
		this.PWD = value;
	}

	public String getPWD() {
		return PWD;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return name;
	}

	public void setTelCode(String value) {
		this.telCode = value;
	}

	public String getTelCode() {
		return telCode;
	}

	public void setStatus(Integer value) {
		this.status = value;
	}

	public String getUname() {
		return Uname;
	}

	public void setUname(String uname) {
		Uname = uname;
	}

	public Integer getURole() {
		return URole;
	}

	public void setURole(Integer uRole) {
		URole = uRole;
	}

	public Integer getStatus() {
		return status;
	}

	public String toString() {
		return toString(false);
	}

	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getUID());
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("UserInfo[ ");
			sb.append("UID=").append(getUID()).append(" ");
			sb.append("Uname=").append(getUname()).append(" ");
			sb.append("PWD=").append(getPWD()).append(" ");
			sb.append("Name=").append(getName()).append(" ");
			sb.append("URole=").append(getURole()).append(" ");
			sb.append("TelCode=").append(getTelCode()).append(" ");
			sb.append("Status=").append(getStatus()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}

	public static final class STATUS{
		public static final int OFFLINE = 0;
		public static final int ONLINE = 1;
	}

	public static final class ROLE{
		public static final int NODE_WORKER = 1;
		public static final int CENTER_WORKER = 2;
		public static final int DRIVER = 3;
		public static final int COURIER = 4;
	}

}