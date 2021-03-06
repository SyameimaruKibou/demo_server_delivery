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
package extrace.misc.model;
import java.io.Serializable;
public class TransNode implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -3696487402698365947L;

	public TransNode() {
	}
	private String ID;

	private String nodeName;

	private Integer nodeType;

	private String regionCode;

	private String telCode;

	private int status;

	private Float x;

	private Float y;


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public boolean is_saved() {
		return _saved;
	}

	public void set_saved(boolean _saved) {
		this._saved = _saved;
	}

	public String getORMID() {
		return getID();
	}

	public void setNodeName(String value) {
		this.nodeName = value;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeType(Integer value) {
		this.nodeType = value;
	}

	public Integer getNodeType() {
		return nodeType;
	}

	public void setRegionCode(String value) {
		this.regionCode = value;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setTelCode(String value) {
		this.telCode = value;
	}

	public String getTelCode() {
		return telCode;
	}

	public void setX(Float value) {
		this.x = value;
	}

	public Float getX() {
		return x;
	}

	public void setY(Float value) {
		this.y = value;
	}

	public Float getY() {
		return y;
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
			sb.append("TransNode[ ");
			sb.append("ID=").append(getID()).append(" ");
			sb.append("NodeName=").append(getNodeName()).append(" ");
			sb.append("NodeType=").append(getNodeType()).append(" ");
			sb.append("RegionCode=").append(getRegionCode()).append(" ");
			sb.append("TelCode=").append(getTelCode()).append(" ");
			sb.append("X=").append(getX()).append(" ");
			sb.append("Y=").append(getY()).append(" ");
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
		public static final int OFFLINE = 0;
		public static final int ONLINE = 1;
	}

	public static final class TYPE {
		public static final int NODE = 1;
		public static final int CITY_CENTER = 2;
		public static final int PROVINCE_CENTER = 3;
	}

}