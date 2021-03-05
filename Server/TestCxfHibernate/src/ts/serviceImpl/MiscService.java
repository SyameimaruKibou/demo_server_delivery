package ts.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import ts.daoImpl.RegionDao;
import ts.daoImpl.TransNodeDao;
import ts.daoImpl.UserInfoDao;
import ts.model.CodeNamePair;
import ts.model.Region;
import ts.model.TransHistory;
import ts.model.TransNode;
import ts.model.TransPackage;
import ts.model.UserInfo;
import ts.serviceInterface.IMiscService;

public class MiscService implements IMiscService{
	//TransNodeCatalog nodes;	//�Լ����Ļ�����ض����Ȳ�Ҫ��,��Hibernate����Ը�һ�£��Ժ����ȥ
	//RegionCatalog regions;
	private TransNodeDao transNodeDao;
	private RegionDao regionDao;
	private UserInfoDao userInfoDao;
	

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public TransNodeDao getTransNodeDao() {
		return transNodeDao;
	}

	public void setTransNodeDao(TransNodeDao dao) {
		this.transNodeDao = dao;
	}

	public RegionDao getRegionDao() {
		return regionDao;
	}

	public void setRegionDao(RegionDao dao) {
		this.regionDao = dao;
	}


	public MiscService(){
//		nodes = new TransNodeCatalog();
//		nodes.Load();
//		regions = new RegionCatalog();
//		regions.Load();
	}

	@Override
	public TransNode getNode(String codeid) {
		// TODO Auto-generated method stub
		return transNodeDao.get(codeid);
	}

	@Override
	public List<TransNode> getNodesList() {
		// TODO Auto-generated method stub
		return transNodeDao.getAll();
	}
	
	@Override
	public List<TransNode> getNodesListByRegion(String region_code){
		return transNodeDao.findByRegionCode(region_code);
	}
	
	@Override
	public Response newNode(TransNode node) {
		try {
			int t_code = 0;
			try {
				List<TransNode> list = transNodeDao.findByRegionCode(node.getRegionCode());
				t_code = list.size();
			}catch(Exception e) {
				t_code = 0;
			}
			t_code++;
			String node_num = Integer.toString(t_code);
			node_num = "00" + node_num;
			System.out.println("row:" + node.getRegionCode() + node_num);
			node.setID(node.getRegionCode() + node_num);
			System.out.println("node:" +node.getID());
			node.setStatus(TransNode.STATUS.ONLINE);
			transNodeDao.saveOnly(node);
			return Response.ok("OK").header("EntityClass", "TransNode").build();
		}catch(Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}
	
	@Override
	public List<CodeNamePair> getProvinceList() {		
		List<Region> listrg = regionDao.getProvinceList();
		List<CodeNamePair> listCN = new ArrayList<CodeNamePair>();
		for(Region rg : listrg){
			CodeNamePair cn = new CodeNamePair(rg.getORMID(),rg.getPrv());
			listCN.add(cn);
		}
		return listCN;
	}

	@Override
	public List<CodeNamePair> getCityList(String prv) {
		List<Region> listrg = regionDao.getCityList(prv);
		List<CodeNamePair> listCN = new ArrayList<CodeNamePair>();
		for(Region rg : listrg){
			CodeNamePair cn = new CodeNamePair(rg.getORMID(),rg.getCty());
			listCN.add(cn);
		}
		return listCN;
	}

	@Override
	public List<CodeNamePair> getTownList(String city) {
		List<Region> listrg = regionDao.getTownList(city);
		List<CodeNamePair> listCN = new ArrayList<CodeNamePair>();
		for(Region rg : listrg){
			CodeNamePair cn = new CodeNamePair(rg.getORMID(),rg.getTwn());
			listCN.add(cn);
		}
		return listCN;
	}

	@Override
	public String getRegionString(String code) {
		return regionDao.getRegionNameByID(code);
	}

	@Override
	public Region getRegion(String code) {
		return regionDao.getFullNameRegionByID(code);
	}

	@Override
	public void CreateWorkSession(int uid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Response doLogin(String uname, String pwd) {
		UserInfo userInfo = userInfoDao.login(uname, pwd);
		try {
			if(userInfo != null)
				return Response.ok(userInfo).header("EntityClass", "UserInfo").build();
			else
				return Response.ok(null).header("EntityClass", "E_UserInfo").build();
		}catch(Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	
	@Override
	public Response newUser(UserInfo user) {
		try {
			user.setStatus(UserInfo.STATUS.ONLINE);
			userInfoDao.save(user);
			return Response.ok("OK").header("EntityClass", "UserInfo").build();
		}catch(Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}
	
	@Override
	public Response updateUserLoc(int userid,Float x,Float y) {
		try {
			UserInfo user = userInfoDao.get(userid);
			user.setX(x);
			user.setY(y);
			userInfoDao.save(user);
			return Response.ok("OK").header("EntityClass", "UserInfo").build();
		}catch(Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}
	
	@Override
	public Response getUser(int userid) {
		try {
			UserInfo user = userInfoDao.get(userid);
			return Response.ok(user).header("EntityClass", "UserInfo").build();
		}catch(Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}
	
	@Override
	public void doLogOut(int uid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void RefreshSessionList() {
		// TODO Auto-generated method stub
		
	}
}
