package extrace.ui.main;

import extrace.misc.model.Customer;
import extrace.misc.model.UserInfo;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

public class ExTraceApplication extends Application {

	private static final String PREFS_NAME = "ExTrace.cfg";
    SharedPreferences settings;// = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
	String mServerUrl;
	String mMiscService,mDomainService;
    UserInfo userInfo;
    Customer customerInfo;

    public String getServerUrl() {
        return mServerUrl;
    }

    public void setServerUrl(String url) {
        mServerUrl = url;
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("ServerUrl", mServerUrl);
        editor.commit();
    }


    public String getMiscServiceUrl() {
        return mServerUrl+mMiscService;
    }
    public String getDomainServiceUrl() {
        return mServerUrl+mDomainService;
    }

    public void setUserInfo(UserInfo user) {
        userInfo = user;
    }

    public UserInfo getLoginUser(){
    	return userInfo;
    }

    public Customer getLoginCustomer(){
        return customerInfo;
    }

    public void setCustomerInfo(Customer customerInfo) {
        this.customerInfo = customerInfo;
    }



    @Override  
    public void onCreate() {  
        super.onCreate();

        customerInfo = new Customer();

        SharedPreferences  settings = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        mServerUrl = settings.getString("ServerUrl", "");
        mMiscService = settings.getString("MiscService", "/REST/Misc/");
        mDomainService = settings.getString("DomainService", "/REST/Domain/");
        if(mServerUrl == null || mServerUrl.length() == 0)
        {
            mServerUrl = "http://47.93.246.164:8080/TestCxfHibernate/REST";
            //mServerUrl = "http://192.168.1.105:8080/TestCxfHibernate";

            mMiscService = "/Misc/";
            mDomainService = "/Domain/";
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("ServerUrl", mServerUrl);
            editor.putString("MiscService", mMiscService);
            editor.putString("DomainService", mDomainService);
            editor.commit();
        }


       // settings = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        //SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        //临时造一个用户
        customerInfo.setID(1);
        customerInfo.setUname("983043192");
        customerInfo.setNickname("啵啵崽");
        customerInfo.setPwd("123456");
        customerInfo.setTelCode("15881163389");

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

    }  
      
    public void onTerminate() {  
        super.onTerminate();  
          
        //save data of the map  
    }

    public void saveCustomer( Customer customer){
        this.customerInfo = customer;
    }

}
