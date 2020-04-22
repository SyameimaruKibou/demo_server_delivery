package ts.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import ts.daoImpl.ExpressHistoryDao;
import ts.daoImpl.ExpressSheetDao;
import ts.daoImpl.TemporaryExpressDao;
import ts.daoImpl.TransHistoryDao;
import ts.daoImpl.TransNodeDao;
import ts.daoImpl.TransPackageContentDao;
import ts.daoImpl.TransPackageDao;
import ts.daoImpl.UserInfoDao;
import ts.model.ArrayOfString;
import ts.model.ExpressHistory;
import ts.model.ExpressSheet;
import ts.model.TemporaryExpress;
import ts.model.TransHistory;
import ts.model.TransNode;
import ts.model.TransPackage;
import ts.model.TransPackageContent;
import ts.model.UserInfo;
import ts.serviceInterface.IDomainService;

public class DomainService implements IDomainService {
	
	public TransNodeDao getTransNodeDao() {
		return transNodeDao;
	}

	public void setTransNodeDao(TransNodeDao transNodeDao) {
		this.transNodeDao = transNodeDao;
	}

	private ExpressSheetDao expressSheetDao;
	private ExpressHistoryDao expressHistoryDao;
	private TransPackageDao transPackageDao;
	private TransHistoryDao transHistoryDao;
	private TransPackageContentDao transPackageContentDao;
	private UserInfoDao userInfoDao;
	private TransNodeDao transNodeDao;
	private TemporaryExpressDao temporaryExpressDao;
	
	public ExpressSheetDao getExpressSheetDao() {
		return expressSheetDao;
	}

	public void setExpressSheetDao(ExpressSheetDao dao) {
		this.expressSheetDao = dao;
	}

	public TransPackageDao getTransPackageDao() {
		return transPackageDao;
	}

	public void setTransPackageDao(TransPackageDao dao) {
		this.transPackageDao = dao;
	}

	public TransHistoryDao getTransHistoryDao() {
		return transHistoryDao;
	}

	public void setTransHistoryDao(TransHistoryDao dao) {
		this.transHistoryDao = dao;
	}

	public TransPackageContentDao getTransPackageContentDao() {
		return transPackageContentDao;
	}

	public ExpressHistoryDao getExpressHistoryDao() {
		return expressHistoryDao;
	}

	public void setExpressHistoryDao(ExpressHistoryDao expressHistoryDao) {
		this.expressHistoryDao = expressHistoryDao;
	}

	public TemporaryExpressDao getTemporaryExpressDao() {
		return temporaryExpressDao;
	}

	public void setTemporaryExpressDao(TemporaryExpressDao temporaryExpressDao) {
		this.temporaryExpressDao = temporaryExpressDao;
	}

	public void setTransPackageContentDao(TransPackageContentDao dao) {
		this.transPackageContentDao = dao;
	}

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao dao) {
		this.userInfoDao = dao;
	}

	public Date getCurrentDate() {
		//����һ�����������ʱ��,��Ȼ,SQLʱ���JAVAʱ���ʽ��һ��
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date tm = new Date();
		try {
			tm= sdf.parse(sdf.format(new Date()));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return tm;
	}

	@Override
	public List<ExpressSheet> getExpressList(String property,
			String restrictions, String value) {
		try {
		List<ExpressSheet> list = new ArrayList<ExpressSheet>();
		switch(restrictions.toLowerCase()){
		case "eq":
			list = expressSheetDao.findBy(property, value, "ID", true);
			break;
		case "like":
			list = expressSheetDao.findLike(property, value+"%", "ID", true);
			break;
		}
		return list;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	//节点内发生的快件操作的记录历史
	public void UpdateExpressHistory(String nodeid,int userid,int actid,ExpressSheet express) throws Exception{
		//根据操作更改快件状态
		if(actid == ExpressHistory.ACTID.ACT_PACK) {
			express.setStatus(ExpressSheet.STATUS.PACKED);
			expressSheetDao.save(express);
		}
		else if(actid == ExpressHistory.ACTID.ACT_UNPACK) {
			express.setStatus(ExpressSheet.STATUS.UNPACKED);
			expressSheetDao.save(express);
		}
		//当快件抵达某个节点后，更新它的下一站信息
		else if(actid == ExpressHistory.ACTID.ACT_TRANPORTING_REACH) {
			express.setNextnode(transNodeDao.getNextNode(nodeid, express));
			expressSheetDao.save(express);
		}
		ExpressHistory epsHis = new ExpressHistory();
		epsHis.setActId(actid);
		epsHis.setEps(express);
		epsHis.setSourcenode(transNodeDao.get(nodeid));
		epsHis.setTargetnode(express.getNextnode());
		epsHis.setUser(userInfoDao.get(userid));
		epsHis.setActTime(getCurrentDate());
		System.out.println(epsHis.toString());
		expressHistoryDao.save(epsHis);
	}
	
	//节点外发生的快件操作的记录历史
	public void UpdateExpressHistory(int userid,int actid,ExpressSheet express) throws Exception{
		
		ExpressHistory epsHis = new ExpressHistory();
		epsHis.setActId(actid);
		epsHis.setEps(express);
		epsHis.setUser(userInfoDao.get(userid));
		epsHis.setActTime(getCurrentDate());
		System.out.println(epsHis.toString());
		expressHistoryDao.save(epsHis);
	}
	
	public int TransPkgActToEpsAct(int pkgact) {
		if(pkgact == TransHistory.ACTID.ACT_CREATE)
			return ExpressHistory.ACTID.ACT_PACK;
		else if(pkgact == TransHistory.ACTID.ACT_TRANPORTING_LEAVE)
			return ExpressHistory.ACTID.ACT_TRANPORTING_LEAVE;
		else if(pkgact == TransHistory.ACTID.ACT_TRANPORTING_REACH)
			return ExpressHistory.ACTID.ACT_TRANPORTING_REACH;
		else if(pkgact  == TransHistory.ACTID.ACT_FINISH)
			return ExpressHistory.ACTID.ACT_UNPACK;
		else
			return -1;
	}
	
	public void UpdateTransHistory(String nodeid,int userid,int pkgact,TransPackage pkg) throws Exception{
		TransHistory pkgHis = new TransHistory();
		int epsact = TransPkgActToEpsAct(pkgact);
		pkgHis.setActId(pkgact);
		pkgHis.setPkg(pkg);
		pkgHis.setSourcenode(pkg.getSourcenode());
		pkgHis.setTargetnode(pkg.getTargetnode());
		pkgHis.setUserId(userid);
		pkgHis.setActTime(getCurrentDate());
		transHistoryDao.save(pkgHis);
		List<ExpressSheet> epsList = expressSheetDao.getListInPackage(pkg.getID());
		for(ExpressSheet eps:epsList) {
			UpdateExpressHistory(nodeid,userid,epsact,eps);
		}
	}
//	@Override
//	public List<ExpressSheet> getExpressList(String property,
//			String restrictions, String value) {
//		Criterion cr1;
//		Criterion cr2 = Restrictions.eq("Status", 0);
//
//		List<ExpressSheet> list = new ArrayList<ExpressSheet>();
//		switch(restrictions.toLowerCase()){
//		case "eq":
//			cr1 = Restrictions.eq(property, value);
//			break;
//		case "like":
//			cr1 = Restrictions.like(property, value);
//			break;
//		default:
//			cr1 = Restrictions.like(property, value);
//			break;
//		}
//		list = expressSheetDao.findBy("ID", true,cr1,cr2);		
//		return list;
//	}
	
	
	@Override
	public List<ExpressHistory> getExpressHistory(String eps_id){
		try {
			List<ExpressHistory> list = new ArrayList<>();
			list = expressHistoryDao.getExpressHistory(eps_id);
			return list;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<ExpressSheet> getExpressListInPackage(String packageId){
		List<ExpressSheet> list = new ArrayList<ExpressSheet>();
		list = expressSheetDao.getListInPackage(packageId);
		return list;		
	}

	@Override
	public Response getExpressSheet(String id) {
		ExpressSheet es = expressSheetDao.get(id);
		return Response.ok(es).header("EntityClass", "ExpressSheet").build(); 
	}

	//用户发起预约寄件
	@Override
	public Response newTempExpress(TemporaryExpress teps) {
		try {
			temporaryExpressDao.save(teps);
			return Response.ok("OK").header("EntityClass", "Temp").build();
		}catch(Exception e)
		{
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}
	
	//完成用户的预约寄件
	public Response deleteTempExpress(int SN) {
		try {
			temporaryExpressDao.removeById(SN);
			return Response.ok("OK").header("EntityClass", "Temp").build();
		}catch(Exception e)
		{
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}
	
	//获得用户预约寄件单
	public List<TemporaryExpress> getUnhandleEpsList(String tel) {
		try {
			List <TemporaryExpress> tepsList = new ArrayList<>();
			tepsList = temporaryExpressDao.getTepsListByTel(tel);
			return tepsList;
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	//���ս׶�2�����Express���ϴ�
	@Override
	public Response newExpress(ExpressSheet obj,String nodeid,int userid) {
		try{
			int actid = ExpressHistory.ACTID.ACT_CREATE;
			obj.setID(null);
			obj.setCreateTime(getCurrentDate());
			obj.setStatus(ExpressSheet.STATUS.UNPACKED);
			
			String flag = transNodeDao.isTargetReachable(nodeid, obj);
			if(!flag.equals("OK")) {
				//如果快件不可达，发送不可达的Region号
				return Response.ok(flag).header("EntityClass", "R_UnReachable").build(); 
			}
			//首次创建时设置其下一站信息
			obj.setNextnode(transNodeDao.getNextNode(nodeid, obj));
			
			System.out.println(obj.toString());
			expressSheetDao.save(obj);
			System.out.println(obj.toString());
			UpdateExpressHistory(nodeid,userid,actid,obj);
			
			return Response.ok(obj.getID()).header("EntityClass", "R_ExpressSheet").build(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}
	
	//������ݶ���Ҫ�ֶ��ṩĿ��ڵ�
	@Override
	public Response pack(ArrayOfString array,String nodeid,String targetnode,int userid) {
		try{
			int pkgAct = TransHistory.ACTID.ACT_CREATE;
			TransPackage pkg = new TransPackage();
			pkg.setID(null);
			pkg.setCreateTime(getCurrentDate());
			pkg.setSourcenode(transNodeDao.get(nodeid));
			pkg.setTargetnode(transNodeDao.get(targetnode));
			pkg.setStatus(TransPackage.STATUS.IN_NODE);
			//transPackageDao.save(pkg);
			
			ExpressSheet eps;
			TransPackageContent content;
			
			
			for(String id : array.getList()) {
				eps = expressSheetDao.get(id);
				
				if(eps.getStatus() != ExpressSheet.STATUS.UNPACKED) {
					String errMsg = null;
					if(eps.getStatus() == ExpressSheet.STATUS.PACKED){
						errMsg = "存在已经打包的快件，ID号为" + eps.getID();
					}
					else if(eps.getStatus() == ExpressSheet.STATUS.DELIVERIED) {
						errMsg = "存在已经送达的快件：ID号为" + eps.getID();
					}
					else {
						errMsg = "存在状态异常快件：ID号为" + eps.getID();
					}
					return Response.ok(errMsg).header("EntityClass", "R_TransPackage").build(); 
				}
				
				if(!eps.getNextnode().getID().equals(targetnode)) {
					String errMsg = "存在下一站位置与其他快件不相同的快件：ID号为" + eps.getID();
					Response.ok(errMsg).header("EntityClass", "R_TransPackage").build();
				}
				
				
				content = new TransPackageContent();
				content.setExpress(eps);
				content.setPkg(pkg);
				content.setStatus(TransPackageContent.STATUS.STATUS_ACTIVE);
				pkg.getContent().add(content);
				//利用映射联级存储
			}
			
			transPackageDao.save(pkg);
			UpdateTransHistory(nodeid,userid,pkgAct,pkg);
			return Response.ok(pkg.getID()).header("EntityClass", "TransPackage").build(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}
	
	//���
	@Override
	public Response unpack(String packageid,String nodeid,int userid) {
		try {
			int pkgAct = TransHistory.ACTID.ACT_FINISH;
			TransPackage pkg = transPackageDao.get(packageid);
			pkg.setStatus(TransPackage.STATUS.DELIVERIED);
			transPackageDao.save(pkg);
			UpdateTransHistory(nodeid,userid,pkgAct,pkg);
			return Response.ok("OK").header("EntityClass", "TransPackage").build(); 
		}catch(Exception e)
		{
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}
	
	//�������գ�˾����
	@Override
	public Response loadTranspack(String packageid,int userid) {
		try {
			int pkgAct = TransHistory.ACTID.ACT_TRANPORTING_LEAVE;
			TransPackage pkg = transPackageDao.get(packageid);
			
			if(pkg.getStatus() != TransPackage.STATUS.IN_NODE) {
				String errMsg = null;
				if(pkg.getStatus() == TransPackage.STATUS.ON_RODE){
					errMsg = "该包裹已经在运输途中";
				}
				else if(pkg.getStatus() == TransPackage.STATUS.DELIVERIED) {
					errMsg = "该包裹已经送达并销毁";
				}
				else {
					errMsg = "该包裹状态异常";
				}
				return Response.ok(errMsg).header("EntityClass", "E_TransPackage").build(); 
			}
			
			pkg.setStatus(TransPackage.STATUS.ON_RODE);
			transPackageDao.save(pkg);
			UpdateTransHistory(pkg.getSourcenode().getID(),userid,pkgAct,pkg);
			return Response.ok("OK").header("EntityClass", "TransPackage").build();
		}catch(Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}
	
	//��������
	@Override
	public Response unloadTranspack(String packageid,int userid) {
		try {
			int pkgAct = TransHistory.ACTID.ACT_TRANPORTING_REACH;
			TransPackage pkg = transPackageDao.get(packageid);
			
			if(pkg.getStatus() != TransPackage.STATUS.ON_RODE) {
				String errMsg = null;
				if(pkg.getStatus() == TransPackage.STATUS.IN_NODE){
					errMsg = "该包裹尚未运输";
				}
				else if(pkg.getStatus() == TransPackage.STATUS.DELIVERIED) {
					errMsg = "该包裹已经送达并销毁" + pkg.getID();
				}
				else {
					errMsg = "该包裹状态异常" + pkg.getID();
				}
				return Response.ok(errMsg).header("EntityClass", "E_TransPackage").build(); 
			}
			
			pkg.setStatus(TransPackage.STATUS.IN_NODE);
			transPackageDao.save(pkg);
			UpdateTransHistory(pkg.getTargetnode().getID(),userid,pkgAct,pkg);
			return Response.ok("OK").header("EntityClass", "TransPackage").build();
		}catch(Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}
	
	//快递员送快递
	@Override
	public Response deliver(String expressid,String nodeid,int userid) {
		try {
			int epsAct = ExpressHistory.ACTID.ACT_DELIVERING;
			ExpressSheet eps = expressSheetDao.get(expressid);
			expressSheetDao.save(eps);
			UpdateExpressHistory(nodeid,userid,epsAct,eps);
			return Response.ok("OK").header("EntityClass", "ExpressSheet").build();
		}catch(Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}
	
	
	//签收
	@Override
	public Response assign(String expressid,int userid) {
		try {
			int epsAct = ExpressHistory.ACTID.ACT_ASSIGNED;
			ExpressSheet eps = expressSheetDao.get(expressid);
			eps.setStatus(ExpressSheet.STATUS.DELIVERIED);
			eps.setFinishTime(getCurrentDate());
			expressSheetDao.save(eps);
			UpdateExpressHistory(userid,epsAct,eps);
			return Response.ok("OK").header("EntityClass", "ExpressSheet").build();
		}catch(Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}
	

	public boolean MoveExpressIntoPackage(String id, String targetPkgId) {
		TransPackage targetPkg = transPackageDao.get(targetPkgId);
		if((targetPkg.getStatus() > 0) && (targetPkg.getStatus() < 3)){	//������״̬��㶨��,�򿪵İ������߻������ܲ���==================================================================
			return false;
		}

		TransPackageContent pkg_add = new TransPackageContent();
		pkg_add.setPkg(targetPkg);
		pkg_add.setExpress(expressSheetDao.get(id));
		pkg_add.setStatus(TransPackageContent.STATUS.STATUS_ACTIVE);
		transPackageContentDao.save(pkg_add); 
		return true;
	}

	public boolean MoveExpressFromPackage(String id, String sourcePkgId) {
		TransPackage sourcePkg = transPackageDao.get(sourcePkgId);
		if((sourcePkg.getStatus() > 0) && (sourcePkg.getStatus() < 3)){
			return false;
		}

		TransPackageContent pkg_add = transPackageContentDao.get(id, sourcePkgId);
		pkg_add.setStatus(TransPackageContent.STATUS.STATUS_OFFLINE);
		transPackageContentDao.save(pkg_add); 
		return true;
	}

	public boolean MoveExpressBetweenPackage(String id, String sourcePkgId, String targetPkgId) {
		//��Ҫ�����������
		MoveExpressFromPackage(id,sourcePkgId);
		MoveExpressIntoPackage(id,targetPkgId);
		return true;
	}


	@Override
	public List<TransPackage> getTransPackageList(String property,
			String restrictions, String value) {
		List<TransPackage> list = new ArrayList<TransPackage>();
		switch(restrictions.toLowerCase()){
		case "eq":
			list = transPackageDao.findBy(property, value, "ID", true);
			break;
		case "like":
			list = transPackageDao.findLike(property, value+"%", "ID", true);
			break;
		}
		return list;
	}

	@Override
	public Response getTransPackage(String id) {
		TransPackage es = transPackageDao.get(id);
		return Response.ok(es).header("EntityClass", "TransPackage").build(); 
	}
	
}
