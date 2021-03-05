package extrace.loader;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import extrace.misc.model.CodeNamePair;
import extrace.net.HttpAsyncTask;
import extrace.net.HttpResponseParam.RETURN_STATUS;
import extrace.net.IDataAdapter;
import extrace.net.JsonUtils;
import extrace.ui.main.ExTraceApplication;

public class RegionListLoader extends HttpAsyncTask {

	String url;
	IDataAdapter<List<CodeNamePair>> adapter;
	private Activity context;
	
	public RegionListLoader(IDataAdapter<List<CodeNamePair>> adpt, Activity context) {
		super(context);
		adapter = adpt;
		url = ((ExTraceApplication)context.getApplication()).getMiscServiceUrl();
		url="http://47.93.246.164:8080/TestCxfHibernate/REST/Misc/";
	}

	@Override
	public void onDataReceive(String class_data, String json_data) {
		List<CodeNamePair> rg = JsonUtils.fromJson(json_data, new TypeToken<List<CodeNamePair>>(){});
	//	Log.i("Loader里面fromjson省份：","成功！！！！！！！！！"+rg.size());
		adapter.setData(rg);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {
		// TODO Auto-generated method stub
		Log.i("onStatusNotify", "onStatusNotify: " + str_response);
	}
	
	public void LoadProvinceList()
	{
		url += "getProvinceList?_type=json";
		try {
		//	Log.i("Loader里面查看省份：","成功！！！！！！！！！");
			execute("http://47.93.246.164:8080/TestCxfHibernate/REST/Misc/getProvinceList?_type=json", "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void LoadCityList(String rCode)
	{
		url += "getCityList/"+ rCode.substring(0, 2) + "0000?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void LoadTownList(String rCode)
	{
		url += "getTownList/"+ rCode.substring(0, 4) +"00?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
