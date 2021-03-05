package extrace.loader;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;

import extrace.misc.model.Customer;
import extrace.net.HttpAsyncTask;
import extrace.net.HttpResponseParam.RETURN_STATUS;
import extrace.net.IDataAdapter;
import extrace.net.JsonUtils;
import extrace.ui.main.ExTraceApplication;

public class CustomerLoader  extends HttpAsyncTask {

    String url;
    IDataAdapter<Customer> adapter;
    private Activity context;

    public CustomerLoader(IDataAdapter<Customer> adpt, Activity context) {
        super(context);
        this.context = context;
        adapter = adpt;
        url = ((ExTraceApplication)context.getApplication()).getMiscServiceUrl();
    }

    @Override
    public void onDataReceive(String class_data, String json_data) {
        if(class_data.equals("Lo_Customer"))//登录
        {
            Customer ci = JsonUtils.fromJson(json_data, new TypeToken<Customer>(){});
            adapter.setData(ci);
            adapter.notifyDataSetChanged();
            Toast.makeText(context, "登录成功!欢迎"+ci.getNickname(), Toast.LENGTH_SHORT).show();
        }
        else if(class_data.equals("E_Customer"))		//错误
        {
            String ci = JsonUtils.fromJson(json_data, new TypeToken<String>(){});
            Toast.makeText(context, "发生错误!"+ci, Toast.LENGTH_SHORT).show();
        }
        else if(class_data.equals("R_Customer"))		//注册
        {
            String ci = JsonUtils.fromJson(json_data, new TypeToken<String>(){});
            if(ci.equals("OK"))
                Toast.makeText(context, "注册成功!", Toast.LENGTH_SHORT).show();
        }
        if(class_data.equals("Chg_Customer"))		//错误
        {
            String ci = JsonUtils.fromJson(json_data, new TypeToken<String>(){});
            Toast.makeText(context, "修改成功!"+ci, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStatusNotify(RETURN_STATUS status, String str_response) {
        Log.i("onStatusNotify", "onStatusNotify: " + str_response);
    }

    public void LoadCustomer(String uname,String pwd)//账号密码登录
    {
        url = "https://kuaidi.cy1999.cn/TestCxfHibernate/REST/Misc/doCusLogin/uname/"+uname+"/pwd/"+pwd+"?_type=json";
        try {
            execute(url, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LoadCustomerByTele(String tele)//验证码登录
    {
        url = "https://kuaidi.cy1999.cn/TestCxfHibernate/REST/Misc/doCusTelLogin/"+tele+"?_type=json";
        try {
            execute(url, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SaveCustomer(Customer cstm)//注册
    {
        String jsonObj = JSON.toJSONString(cstm, true);
        url = "https://kuaidi.cy1999.cn/TestCxfHibernate/REST/Misc/newCustomer/";
        try {
            execute(url, "POST", jsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ChangeCustomerNick(int id,String nick)//昵称 修改
    {
        url = "https://kuaidi.cy1999.cn/TestCxfHibernate/REST/Misc/changeCustomerNick/id/"+id+"/nick/"+nick+"?_type=json";
        try {
            execute(url, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ChangeCustomerPwd(int id,String pwd)//密码 修改
    {
        url = "https://kuaidi.cy1999.cn/TestCxfHibernate/REST/Misc/changeCustomerPwd/id/"+id+"/pwd/"+pwd+"?_type=json";
        try {
            execute(url, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ChangeCustomerTel(int id,String tel)//电话 修改
    {
        url = "https://kuaidi.cy1999.cn/TestCxfHibernate/REST/Misc/changeCustomerTel/id/"+id+"/tel/"+tel+"?_type=json";
        try {
            execute(url, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
