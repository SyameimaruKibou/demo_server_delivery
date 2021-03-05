package extrace.loader;

import android.app.Activity;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import java.util.List;
import extrace.misc.model.ExpressHistory;
import extrace.net.HttpAsyncTask;
import extrace.net.IDataAdapter;
import extrace.net.JsonUtils;
import extrace.ui.main.ExTraceApplication;
import extrace.net.HttpResponseParam.RETURN_STATUS;

public class ExpressHistoryLoader extends HttpAsyncTask {
    String url;
    IDataAdapter<List<ExpressHistory>> adapter;
    private Activity context;

    public ExpressHistoryLoader  (IDataAdapter<List<ExpressHistory>> adpt, Activity context) {
        super(context);
        this.context = context;
        adapter = adpt;
        url = ((ExTraceApplication)context.getApplication()).getDomainServiceUrl();
    }

    @Override
    public void onDataReceive(String class_data, String json_data) {
            List<ExpressHistory> cstm = JsonUtils.fromJson(json_data, new TypeToken<List<ExpressHistory>>(){});
            adapter.setData(cstm);
            adapter.notifyDataSetChanged();
    }

    @Override
    public void onStatusNotify(RETURN_STATUS status, String str_response) {
        Log.i("onStatusNotify", "onStatusNotify: " + str_response);
    }

    public void LoadHistorybyExpressid(String Expressid)//快递单号
    {///Domain/getExpressSheetHistory/{Expressid}
        url = "https://kuaidi.cy1999.cn/TestCxfHibernate/REST/Domain/getExpressSheetHistory/"+Expressid+"?_type=json";//获得某一快件的历史纪录
        try {
            execute(url, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
