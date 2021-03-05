package extrace.misc.model;

import java.io.Serializable;
public class Customer implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2926242598503406098L;

    private int ID;

    private String Uname;

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        this.ID = iD;
    }

    private String nickname;

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        this.Uname = uname;
    }

    private String pwd;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTelCode() {
        return telCode;
    }

    public void setTelCode(String telCode) {
        this.telCode = telCode;
    }

    private String telCode;
}