package extrace.loader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import extrace.misc.model.TransNode;
import extrace.net.HttpAsyncTask;
import extrace.net.HttpResponseParam.RETURN_STATUS;
import extrace.net.IDataAdapter;
import extrace.net.JsonUtils;
import extrace.ui.main.ExTraceApplication;

public class TransNodeLoader extends HttpAsyncTask {

    String url;
    IDataAdapter<List<TransNode>> adapter;
    private Activity context;

    public TransNodeLoader(IDataAdapter<List<TransNode>> adpt, Activity context) {
        super(context);
        adapter = adpt;
        url = ((ExTraceApplication)context.getApplication()).getMiscServiceUrl();
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onDataReceive(String class_data, String json_data) {
        List<TransNode> rg = JsonUtils.fromJson(json_data, new TypeToken<List<TransNode>>(){});
        adapter.setData(rg);
        adapter.notifyDataSetChanged();
        //Toast.makeText(context, "查询节点列表成功"+rg.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusNotify(RETURN_STATUS status, String str_response) {
        // TODO Auto-generated method stub
        Log.i("onStatusNotify", "onStatusNotify: " + str_response);
    }

    @SuppressLint("LongLogTag")
    public void TransNodeList()//所有节点列表
    {
    //    Log.i("!!!!!!!!!!!!!!!!!!!!!!!!!!!","进去了！！！！");
        url="http://47.93.246.164:8080/TestCxfHibernate/REST/Misc/getNodesList";
        try {
            execute(url, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getRefionTransNodeList(String regionID)//所有区域内列表
    {
        //    Log.i("!!!!!!!!!!!!!!!!!!!!!!!!!!!","进去了！！！！");
//        Toast.makeText(context, "!!!！"+regionID, Toast.LENGTH_SHORT).show();
        try {
            execute("http://47.93.246.164:8080/TestCxfHibernate/REST/Misc/getNodesListByRegion/"+regionID+"?_type=json", "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
