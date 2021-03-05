package extrace.ui.main;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.baidu.mapapi.SDKInitializer;

import java.util.ArrayList;
import java.util.List;

import extrace.misc.model.ExpressSheet;
import extrace.misc.model.TransNode;
import extrace.misc.model.TransPackage;
import extrace.misc.model.UserInfo;

/**
 *
 */
public class ExTraceApplication extends Application {

    String mServerUrl;
    private static final String PREFS_NAME = "ExTrace.cfg";

	//private static final String PREFS_NAME = "ExTrace.cfg";
    SharedPreferences settings;// = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
//	String mServerUrl;
//	String mMiscService,mDomainService;
    UserInfo userInfo;//登录人的信息
    TransNode node;//快递员，网点人员，分拣区人员选择节点
    TransNode begnode;//司机的起点
    TransNode endnode;//司机的终点
    List<TransPackage> traceList= new ArrayList<>(1);//专给司机使用，用于记录司机揽收的包裹！！！！之后需要服务端记录
    List<ExpressSheet> expreseeList= new ArrayList<>(1);//专给快递员使用，用于记录快递员接收的快递！！！！之后需要服务端记录

    public List<ExpressSheet> getExpreseeList() {
        return expreseeList;
    }

    public void setExpreseeList(List<ExpressSheet> expreseeList) {
        this.expreseeList = expreseeList;
    }

    public TransNode getNode() {
        return node;
    }

    public void setNode(TransNode node) {
        this.node = node;
    }


    public List<TransPackage> getTraceList() {
        return traceList;
    }

    public void setTraceList(List<TransPackage> traceList) {
        this.traceList = traceList;
    }



    public void setBegnode(TransNode begnode) {
        this.begnode = begnode;
    }

    public void setEndnode(TransNode endnode) {
        this.endnode = endnode;
    }



    public TransNode getBegnode() {
        return begnode;
    }

    public TransNode getEndnode() {
        return endnode;
    }


    
    public String getServerUrl() {  
        return settings.getString("ServerUrl", "");  
    }

    public String getMiscServiceUrl() {  
        return getServerUrl() + settings.getString("MiscService", ""); 
    }  
    public String getDomainServiceUrl() {  
        return getServerUrl() + settings.getString("DomainService", ""); 
    }  
  
    public UserInfo getLoginUser(){
    	return userInfo;
    }


    public void setUserInfo(UserInfo user) {
        userInfo = user;
    }

    public void setServerUrl(String url) {
        mServerUrl = url;
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("ServerUrl", mServerUrl);
        editor.commit();
    }


    @Override  
    public void onCreate() {  
        super.onCreate();
        //baiduMap初始化
        SDKInitializer.initialize(this);

        mServerUrl="http://47.93.246.164:8080/TestCxfHibernate/REST";
        settings = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        //SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);


    }  
      
    public void onTerminate() {  
        super.onTerminate();  
          
        //save data of the map  
    }  
}
