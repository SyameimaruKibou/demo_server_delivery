package extrace.loader;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import extrace.misc.model.ExpressSheet;
import extrace.net.HttpAsyncTask;
import extrace.net.HttpResponseParam.RETURN_STATUS;
import extrace.net.IDataAdapter;
import extrace.net.JsonUtils;
import extrace.ui.main.ExTraceApplication;

public class ExpressListLoader extends HttpAsyncTask {

	String url;
	IDataAdapter<List<ExpressSheet>> adapter;
	private Activity context;
	
	public ExpressListLoader(IDataAdapter<List<ExpressSheet>> adpt, Activity context) {
		super(context);
		this.context = context;
		adapter = adpt;
		url = ((ExTraceApplication)context.getApplication()).getDomainServiceUrl();
	}
	
	@Override
	public void onDataReceive(String class_data, String json_data) {
		if(class_data.equals("UnPa_TransPackage")){
			//adapter.getData().remove(0);	//这个地方不好处理
			String result = JsonUtils.fromJson(json_data, new TypeToken<String>(){});
			Toast.makeText(context, "拆包"+result+"！！！", Toast.LENGTH_SHORT).show();
		}
		else{
			List<ExpressSheet> cstm = JsonUtils.fromJson(json_data, new TypeToken<List<ExpressSheet>>(){});
			adapter.setData(cstm);
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {
		Log.i("onStatusNotify", "onStatusNotify: " + str_response);
	}

	public void LoadExpressListInPackage(String pkgId)
	{
		url = "http://47.93.246.164:8080/TestCxfHibernate/REST/Domain/getExpressListInPackage/PackageId/"+pkgId+"?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void DepackTransPackage(String pakid, String nodeid ,String userid)//拆包
	{
		//Toast.makeText(context, "DepackTransPackage 方法里！！！", Toast.LENGTH_SHORT).show();
		try {
			execute("http://47.93.246.164:8080/TestCxfHibernate/REST/Domain/unpack/packageid/"+pakid+"/nodeid/"+nodeid+"/userid/"+userid+"?_type=json", "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fuzzy_search(String expressid)//模糊查询快递
	{
		String tmp="18339520685";
		try {
			Log.i("嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻x嘻嘻嘻嘻嘻",""+expressid);
			execute("http://47.93.246.164:8080/TestCxfHibernate/REST/Domain/getExpressList/id/like/"+expressid+"?_type=json", "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




}
