package ts.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import ts.daoBase.BaseDao;
import ts.model.ExpressSheet;

public class ExpressSheetDao extends BaseDao<ExpressSheet,String> {
	private RegionDao regionDao;
	public RegionDao getRegionDao() {
		return regionDao;
	}
	public void setRegionDao(RegionDao dao) {
		this.regionDao = dao;
	}

	public ExpressSheetDao(){
		super(ExpressSheet.class);
	}

	
	//根据packageId获得包裹中所有的快件列表
	public List<ExpressSheet> getListInPackage(String pkg_id) {	
		String sql = "{alias}.ID in (select ExpressID from TransPackageContent where PackageID = '"+pkg_id+"')";
		List<ExpressSheet> list = new ArrayList<ExpressSheet>();
		list = findBy("ID", true, Restrictions.sqlRestriction(sql));		
		return list;
	}
	
	
}
