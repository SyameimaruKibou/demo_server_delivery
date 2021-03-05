package extrace.loader;

import android.app.Activity;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import extrace.misc.model.Customer;
import extrace.misc.model.Posion;
import extrace.net.HttpAsyncTask;
import extrace.net.HttpResponseParam;
import extrace.net.IDataAdapter;
import extrace.net.JsonUtils;
import extrace.ui.main.ExTraceApplication;

public class LocationLoader  extends HttpAsyncTask {
    String url;
    IDataAdapter<Posion> adapter;
    private Activity context;

    public LocationLoader(IDataAdapter<Posion> adpt, Activity context) {
        super(context);
        this.context = context;
        adapter = adpt;
        url = ((ExTraceApplication)context.getApplication()).getMiscServiceUrl();
    }

    @Override
    public void onDataReceive(String class_data, String json_data) {
        if(class_data.equals("Posion"))//登录
        {
            Posion ci = JsonUtils.fromJson(json_data, new TypeToken<Posion>(){});
            adapter.setData(ci);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStatusNotify(HttpResponseParam.RETURN_STATUS status, String str_response) {
        Log.i("onStatusNotify", "onStatusNotify: " + str_response);
    }

    public void GetLocation(String id)
    {
        url = "https://kuaidi.cy1999.cn/TestCxfHibernate/REST/Domain/getEpsLoc/"+id+"?_type=json";
        try {
            execute(url, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
