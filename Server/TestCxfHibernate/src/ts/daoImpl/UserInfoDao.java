package ts.daoImpl;

import java.util.List;

import ts.daoBase.BaseDao;
import ts.model.UserInfo;


public class UserInfoDao extends BaseDao<UserInfo, Integer> {
	public UserInfoDao(){
		super(UserInfo.class);
	}
	
	public UserInfo login(String id, String passwd) {
		List<UserInfo> users = findBy("PWD", passwd, "UID", true);
			//List<UserInfo> users = findBy("UID", true, Restrictions.eq("PWD", passwd));
		for (UserInfo userInfo : users) {
			if (userInfo.getUname().equals(id)) return userInfo;
		}
		return null;
	}
}
