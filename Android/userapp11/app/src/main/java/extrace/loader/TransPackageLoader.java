package extrace.loader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import extrace.misc.model.TransPackage;
import extrace.net.HttpAsyncTask;
import extrace.net.HttpResponseParam.RETURN_STATUS;
import extrace.net.IDataAdapter;
import extrace.net.JsonUtils;
import extrace.ui.main.ExTraceApplication;

public class TransPackageLoader extends HttpAsyncTask {

	String url;
	IDataAdapter<TransPackage> adapter;
	private Activity context;
	
	public TransPackageLoader(IDataAdapter<TransPackage> adpt, Activity context) {
		super(context);
		this.context = context;
		adapter = adpt;
		url = ((ExTraceApplication)context.getApplication()).getDomainServiceUrl();
	}

	@Override
	public void onDataReceive(String class_name, String json_data) {
		Toast.makeText(context, "ondata成功："+class_name, Toast.LENGTH_SHORT).show();
		if(class_name.equals("E_TransPackage"))//返回异常
		{
			TransPackage tp=new TransPackage();
			tp.setID("NO");
			adapter.setData(tp);
			adapter.notifyDataSetChanged();
			Log.i("错错错粗粗偶凑错错错错错","：null");
			Toast.makeText(context, "操作失败！\n失败原因："+json_data, Toast.LENGTH_SHORT).show();
		}

		else if(class_name.equals("Lo_TransPackage")){     //接收成功！！！
			Toast.makeText(context, "操作结果："+json_data, Toast.LENGTH_SHORT).show();
		}

		else if(class_name.equals("TransPackage"))		//交付成功！！！
		{

			Toast.makeText(context, "操作结果："+json_data, Toast.LENGTH_SHORT).show();
			//adapter.notifyDataSetChanged();

		}else if(class_name.equals("Get_TransPackage"))	{//根据包裹id查询包裹的id！！！！！！！！！！！！！！！！！！！！！
			TransPackage ci = JsonUtils.fromJson(json_data, new TypeToken<TransPackage>(){});
			adapter.setData(ci);
			adapter.notifyDataSetChanged();
			Toast.makeText(context, "查询成功："+json_data, Toast.LENGTH_SHORT).show();
		}else if(class_name.equals("R_TransPackage"))		//保存完成
		{
			TransPackage ci = JsonUtils.fromJson(json_data, new TypeToken<TransPackage>(){});
			adapter.getData().setID(ci.getID());
			//	adapter.getData().onSave();
			adapter.notifyDataSetChanged();
			Toast.makeText(context, "包裹信息保存完成!", Toast.LENGTH_SHORT).show();
		}
		else if(class_name.equals("S_Message"))
		{
			Toast.makeText(context, "包裹转运成功,请在转运任务中查看!", Toast.LENGTH_SHORT).show();
			adapter.notifyDataSetChanged();
		}

	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {
		// TODO Auto-generated method stub
		
	}

	public void Load(String packageId,String id)//loadTranspack/packageid/{packageid}/userid/{userid}包裹接收
	{
		url += "/loadTranspack/packageid/"+ packageId + "/userid/" + id + "?_type=json";
		try {
			execute("http://47.93.246.164:8080/TestCxfHibernate/REST/Domain/loadTranspack/packageid/"+packageId+"/userid/"+id+ "?_type=json", "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void unLoad(String packageId,String id)//包裹交付~!!!!!!!!!!!!!!
	{
		url += "/unloadTranspack/packageid/"+ packageId + "/userid/" + id + "?_type=json";
		try {
			execute("http://47.93.246.164:8080/TestCxfHibernate/REST/Domain/unloadTranspack/packageid/"+packageId+"/userid/"+id+ "?_type=json", "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressLint("LongLogTag")
	public void getTransPackage(String id)//查询包裹！！！！
	{
		try {
			execute("http://47.93.246.164:8080/TestCxfHibernate/REST/Domain/getTransPackage/"+id+"?_type=json", "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void GetTransPackageById(String id)
	{
		try {
			execute("http://47.93.246.164:8080/TestCxfHibernate/REST/Domain/getTransPackage/" + id + "?_type=json", "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
