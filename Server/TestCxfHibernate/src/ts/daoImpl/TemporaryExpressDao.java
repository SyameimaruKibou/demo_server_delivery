package ts.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import ts.daoBase.BaseDao;
import ts.model.TemporaryExpress;
public class TemporaryExpressDao extends BaseDao<TemporaryExpress,Integer>{
	public TemporaryExpressDao(){
		super(TemporaryExpress.class);
	}
	
	public List<TemporaryExpress> getTepsListByTel(String tel) {	
		String sql = "{alias}.SN in (select SN from TemporaryExpress where SenderTel = '"+tel+"')";
		List<TemporaryExpress> list = new ArrayList<TemporaryExpress>();
		list = findBy("SN", true, Restrictions.sqlRestriction(sql));		
		return list;
	}
}
