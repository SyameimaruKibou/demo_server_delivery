package ts.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import ts.daoBase.BaseDao;
import ts.model.ExpressHistory;
import ts.model.ExpressSheet;

public class ExpressHistoryDao extends BaseDao<ExpressHistory,Integer> {
	public ExpressHistoryDao(){
		super(ExpressHistory.class);
	}
	
	public List<ExpressHistory> getExpressHistory(String eps_id){
		String sql = "{alias}.SN in (select SN from ExpressHistory where ExpressID = '"+eps_id+"')";
		List<ExpressHistory> list = new ArrayList<ExpressHistory>();
		list = findBy("SN", true, Restrictions.sqlRestriction(sql));
		return list;
	}
}
