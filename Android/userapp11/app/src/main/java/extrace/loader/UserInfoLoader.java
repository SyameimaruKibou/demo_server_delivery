package extrace.loader;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import extrace.misc.model.UserInfo;
import extrace.net.HttpAsyncTask;
import extrace.net.HttpResponseParam.RETURN_STATUS;
import extrace.net.IDataAdapter;
import extrace.net.JsonUtils;
import extrace.ui.main.ExTraceApplication;

public class UserInfoLoader extends HttpAsyncTask {
    String url;// = "http://192.168.7.100:8080/TestCxfHibernate/REST/Misc/";
    IDataAdapter<UserInfo> adapter;
    private Activity context;

    public UserInfoLoader(Activity context) {
        super(context);
        adapter = new defaultAdapter();
        this.context = context;
        url = ((ExTraceApplication)context.getApplication()).getMiscServiceUrl();
    }

    public UserInfoLoader(IDataAdapter<UserInfo> adpt, Activity context) {
        super(context);
        this.context = context;
        adapter = adpt;
        url="http://47.93.246.164:8080/TestCxfHibernate/REST";
       // url = ((ExTraceApplication)context.getApplication()).getMiscServiceUrl();
    }

    @Override
    public void onDataReceive(String class_name, String json_data) {


        if(class_name.equals("E_UserInfo")){
            //对应服务器中数据("账号或密码错误，请重新输入！").header("EntityClass", "W_UserInfo").build();
            Toast.makeText(context, "用户信息不存在!", Toast.LENGTH_SHORT).show();
        }
        else if(class_name.equals("Lo_UserInfo")) {		//更新用户信息

            UserInfo user = JsonUtils.fromJson(json_data, UserInfo.class);
         //   Toast.makeText(context, user.getUname()+"+"+user.getPWD()+"+"+user.getURole()+"更新成功!", Toast.LENGTH_SHORT).show();
            adapter.setData(user);
            adapter.notifyDataSetChanged();
            ((ExTraceApplication)context.getApplication()).setUserInfo(user); //给application传入用户信息
        }
    }

    @Override
    public void onStatusNotify(RETURN_STATUS status, String str_response) {
        Log.i("onStatusNotify", "onStatusNotify: " + str_response);
    }

    public void LoadUserInfoByID(String ID, String PWD)
    {
        //url += "getUserInfo/"+ ID + "?_type=json";
        url += "doLogin/"+ ID + "/" + PWD + "?_type=json";
        try {
            execute(url, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void LoadUserInfoByUName(String UName, String PWD)
    {
        try {
          //  Toast.makeText(context, "好好!!!!!+"+UName+"--"+PWD, Toast.LENGTH_SHORT).show();http://47.93.246.164:8080/TestCxfHibernate/REST
            execute((String)("http://47.93.246.164:8080/TestCxfHibernate/REST/Misc/doLogin/"+UName+"/"+PWD+"?_type=json"),"GET");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }







    public void SaveUser(UserInfo user)
    {
        Gson gson = new Gson();
        String jsonObj = gson.toJson(user);

        url += "saveUserInfo";
        try {
            execute(url, "POST", jsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    class defaultAdapter implements IDataAdapter<UserInfo> {		//提供一个默认adapter供无参数的构造函数使用

        @Override
        public UserInfo getData() {
            return new UserInfo();
        }

        @Override
        public void setData(UserInfo data) {

        }

        @Override
        public void notifyDataSetChanged() {

        }
    }

}
