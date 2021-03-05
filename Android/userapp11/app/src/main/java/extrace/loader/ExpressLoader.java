package extrace.loader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;

import extrace.misc.model.ExpressSheet;
import extrace.net.HttpAsyncTask;
import extrace.net.HttpResponseParam.RETURN_STATUS;
import extrace.net.IDataAdapter;
import extrace.net.JsonUtils;
import extrace.ui.main.ExTraceApplication;

public class ExpressLoader extends HttpAsyncTask {

	String url;
	IDataAdapter<ExpressSheet> adapter;
	private Activity context;

	public ExpressLoader(IDataAdapter<ExpressSheet> adpt, Activity context) {
		super(context);
		this.context = context;
		adapter = adpt;
		url = ((ExTraceApplication)context.getApplication()).getDomainServiceUrl();
	}

	@SuppressLint("LongLogTag")
	@Override
	public void onDataReceive(String class_name, String json_data) {
		if(class_name.equals("Get_ExpressSheet"))//根据ID精准查询快递
		{
			Toast.makeText(context, "到了第一个!", Toast.LENGTH_SHORT).show();
			//Log.i("777777777777777777777777777777777777777777777777777777777777777777711111111111","");
			ExpressSheet ci = JsonUtils.fromJson(json_data, new TypeToken<ExpressSheet>(){});
			adapter.setData(ci);
			adapter.notifyDataSetChanged();
		}

		else if(class_name.equals("As_ExpressSheet"))		//签收快递
		{
			Toast.makeText(context, json_data, Toast.LENGTH_SHORT).show();
//			Toast.makeText(context, "快件运单信息已经存在!", Toast.LENGTH_SHORT).show();
		}

		else if(class_name.equals("E_ExpressSheet"))		//已经存在
		{
			//Log.i("77777777777777777777777777777777777777777777777777777777777777777772222222222","");
			Toast.makeText(context, "到了第二个!", Toast.LENGTH_SHORT).show();
			Toast.makeText(context, json_data, Toast.LENGTH_SHORT).show();
//			Toast.makeText(context, "快件运单信息已经存在!", Toast.LENGTH_SHORT).show();
		}

		else if(class_name.equals("R_ExpressSheet"))		//保存完成
		{
			Log.i("77777777777777777777777777777777777777777777777777777777777777777773333333333","");
			ExpressSheet es=adapter.getData();
			es.setID(json_data);
			adapter.setData(es);
			Toast.makeText(context, "快件运单信息保存完成!", Toast.LENGTH_SHORT).show();
			adapter.notifyDataSetChanged();
		}


	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {
		// TODO Auto-generated method stub

	}

	@SuppressLint("LongLogTag")
	public void Load(String id)//根据ID查询快递
	{
		//Log.i("7777777777777777777777777777777777777777777777777777777777777777777gangload",""+id);
		//	url += "getExpressSheet/"+ id + "?_type=json";
		try {
			execute("http://47.93.246.164:8080/TestCxfHibernate/REST/Domain/getExpressSheet/"+id+"?_type=json", "GET");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mohu_Load(String id)//根据ID模糊查询快递
	{
		//Log.i("7777777777777777777777777777777777777777777777777777777777777777777gangload",""+id);
		//	url += "getExpressSheet/"+ id + "?_type=json";
		try {
			execute("http://47.93.246.164:8080/TestCxfHibernate/REST/Domain/getExpressSheet/"+id+"?_type=json", "GET");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void New(String id)
	{
		int uid = ((ExTraceApplication)context.getApplication()).getLoginUser().getUID();
		url += "newExpressSheet/id/"+ id + "/uid/"+ uid + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Save(String nodeid,String useid,ExpressSheet es)//揽收快递！！！！！！！！！！！！
	{
		//	Toast.makeText(context, ""+es.getRecievername()+"_____"+es.getSendername(), Toast.LENGTH_SHORT).show();
		String ans= JSON.toJSONString(es);
		url += "saveExpressSheet";
		try {
			execute("http://47.93.246.164:8080/TestCxfHibernate/REST/Domain/newExpress/nodeid/"+nodeid+"/userid/"+useid+"?_type=json", "POST", ans);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Receive(String id)
	{
		int uid = ((ExTraceApplication)context.getApplication()).getLoginUser().getUID();
		url += "receiveExpressSheetId/id/"+ id + "/uid/"+ uid + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Delivery(String id)///deliver/expressid/{expressid}/nodeid/{nodeid}/userid/{userid}  查询快递
	{
		int uid = ((ExTraceApplication)context.getApplication()).getLoginUser().getUID();
		url += "/deliver/expressid/"+ id + "/userid/"+ uid + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressLint("LongLogTag")
	public void Assign(String esid, String useid)///assign/expressid/{expressid}/userid/{userid} 签收快递
	{
		Log.i("5555555555555555555555555555555555555555555",""+esid+"==="+useid);
		try {
			execute("http://47.93.246.164:8080/TestCxfHibernate/REST/Domain/assign/expressid/"+esid+"/userid/"+useid+"?_type=json", "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
