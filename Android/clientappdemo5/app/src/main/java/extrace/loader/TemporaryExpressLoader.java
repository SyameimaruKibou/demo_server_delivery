package extrace.loader;

import android.app.Activity;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import extrace.misc.model.ExpressSheet;
import extrace.misc.model.TemporaryExpress;
import extrace.net.HttpAsyncTask;
import extrace.net.HttpResponseParam;
import extrace.net.IDataAdapter;
import extrace.net.JsonUtils;
import extrace.ui.main.ExTraceApplication;

public class TemporaryExpressLoader extends HttpAsyncTask {

    String url;
    IDataAdapter<String> adapter;
    private Activity context;

    public TemporaryExpressLoader(IDataAdapter<String> adpt, Activity context) {
        super(context);
        this.context = context;
        adapter = adpt;
        url = ((ExTraceApplication)context.getApplication()).getDomainServiceUrl();
    }

    @Override
    public void onDataReceive(String class_name, String json_data) {

        if(class_name.equals("R_Temp"))
       {
            String ci = JsonUtils.fromJson(json_data,new TypeToken<String>(){});
            if(ci.equals("OK")){
                //Toast.makeText(context, "下单成功！", Toast.LENGTH_SHORT).show();
            }
            adapter.setData(ci);
            adapter.notifyDataSetChanged();
       }

    }

    @Override
    public void onStatusNotify(HttpResponseParam.RETURN_STATUS status, String str_response) {
        // TODO Auto-generated method stub

    }

    public void New(TemporaryExpress es)//上门取件 建立快递信息
    {
        String jsonObj = JSON.toJSONString(es, true);
        //Toast.makeText(context, "jsonString:"+jsonObj, Toast.LENGTH_SHORT).show();
        url = "https://kuaidi.cy1999.cn/TestCxfHibernate/REST/Domain/newTempExpress";
        try {
            execute(url, "POST", jsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
