package ts.daoImpl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import ts.daoBase.BaseDao;
import ts.model.ExpressSheet;
import ts.model.TransNode;

public class TransNodeDao extends BaseDao<TransNode, String>{
	public TransNodeDao(){
		super(TransNode.class);
	}

	public static String getNextRegion(String local_region,String target_region) {
		String next_region = "000000";
		//步骤1，根据当前与目标region值逻辑获取下一站region值
		
		//当local为省级中转时
		if(local_region.substring(2,4).equals("00"))
			//如果本省则下传至目标市级中转
			if(local_region.substring(0,2).equals(target_region.substring(0,2)))
				next_region = target_region.substring(0,4) + "00";
			//如果外省则传至目标的省级中转
			else
				next_region = target_region.substring(0,2) + "0000";
		//当local为市级中转时
		else if(local_region.substring(4,6).equals("00"))
			//如果本市则直接下传至目标节点
			if(local_region.substring(0,4).equals(target_region.substring(0,4)))
				next_region = target_region;
			//如果外市则上传至本省中转
			else
				next_region = local_region.substring(0,2) + "0000";
		//当local为区级网点且正好为目标点时
		else if(local_region.equals(target_region))
			next_region = target_region;
		//当local为非目标点的区级网点时
		else
			next_region = local_region.substring(0,4) + "00";
		
		return next_region;
	}
	
	//根据本节点和包裹信息获取eps下一站节点
	public TransNode getNextNode(String node_id,ExpressSheet eps) {
		String local_region = get(node_id).getRegionCode();
		String target_region = eps.getReceiverregcode();
		String next_region = getNextRegion(local_region,target_region);
		
		//TO:DO：在单个区域存在多个node的情况下，默认使用第一个node
		try {
			return get(next_region + "001");
		}catch(DataAccessException e) {
			//找不到时捕捉错误，返回一个Null对象
			e.printStackTrace();
			return null;
		}
	}
	
	//创建快件时，判断快件是否可达
	public String isTargetReachable(String node_id,ExpressSheet eps) {
		String errRegion = get(node_id).getRegionCode();
		TransNode tnode = getNextNode(node_id,eps);
		while(tnode != null && tnode.getRegionCode().equals(eps.getReceiverregcode())) {
			errRegion = tnode.getRegionCode();
			System.out.println(tnode.toString());
			tnode = getNextNode(tnode.getID(),eps);
		}
		if(tnode == null)
			return errRegion;
		else
			return "OK";
	}
	
	public List<TransNode> findByRegionCode(String region_code) {
        Assert.hasText(region_code);
        return findBy("regionCode", region_code, "nodeName", true);
	}

	public int getNodeCount() {
		String hql = "select count(*) from TransNode as node";
		Integer count = (Integer)getHibernateTemplate().find(hql).listIterator().next();
		return count.intValue();
	}

	public void saveOnly(TransNode node) {
		getHibernateTemplate().save(node);
	}
}
