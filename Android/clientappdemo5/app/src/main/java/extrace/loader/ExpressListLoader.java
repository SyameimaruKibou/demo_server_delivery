package extrace.loader;

import android.app.Activity;
import android.util.Log;

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

			List<ExpressSheet> cstm = JsonUtils.fromJson(json_data, new TypeToken<List<ExpressSheet>>(){});
			adapter.setData(cstm);
			adapter.notifyDataSetChanged();
	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {
		Log.i("onStatusNotify", "onStatusNotify: " + str_response);
	}

	public void LoadHistorybyRecvTele(String Tel)//收件人
	{///Domain/getExpressList/recievertel/eq/{Tel}
		url = "https://kuaidi.cy1999.cn/TestCxfHibernate/REST/Domain/getExpressList/receivertel/eq/"+Tel+"?_type=json";//获得某一快件的历史纪录
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void LoadHistorybySendTele(String Tel)//寄件人
	{///Domain/getExpressList/recievertel/eq/{Tel}
		url = "https://kuaidi.cy1999.cn/TestCxfHibernate/REST/Domain/getExpressList/sendertel/eq/"+Tel+"?_type=json";//获得某一快件的历史纪录
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
