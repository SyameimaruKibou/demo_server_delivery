package extrace.loader;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;

import extrace.misc.model.ArrayOfString;
import extrace.net.HttpAsyncTask;
import extrace.net.HttpResponseParam;
import extrace.net.IDataAdapter;
import extrace.net.JsonUtils;

public class PackupLoader extends HttpAsyncTask{

    String url;
    IDataAdapter<String> adapter;
    private Activity context;

    public PackupLoader(IDataAdapter<String> adpt, Activity context) {
        super(context);
        this.context = context;
        adapter = adpt;
    }

    @Override
    public void onDataReceive(String class_data, String json_data) {
        if(class_data.equals("E_TransPackage")){
            //adapter.getData().remove(0);	//这个地方不好处理
            Toast.makeText(context, "打包失败！", Toast.LENGTH_SHORT).show();
        }//TransPackage
        else if(class_data.equals("Pa_TransPackage")){//打包成功
            String string = JsonUtils.fromJson(json_data, new TypeToken<String>(){});
            Toast.makeText(context, "打包成功！", Toast.LENGTH_SHORT).show();
            adapter.setData(string);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStatusNotify(HttpResponseParam.RETURN_STATUS status, String str_response) {
        Log.i("onStatusNotify", "onStatusNotify: " + str_response);
    }

    public void Packup(ArrayOfString list,String sourcenode_id,String targetnode_id,String userid)//打包
    {
        String ans= JSON.toJSONString(list);
        try {
            Toast.makeText(context, "packup里面"+ans, Toast.LENGTH_SHORT).show();
            Log.i("Packup", "Packup: " + ans);
            Log.i("Packup", "Packup: " + ans);
            Log.i("Packup", "Packup: " + ans);
            Log.i("Packup", "Packup: " + ans);
            Log.i("Packup", "Packup: " + ans);
            Log.i("Packup", "Packup: " + ans);
            Log.i("Packup", "Packup: " + ans);
            Log.i("Packup", "Packup: " + ans);
            Log.i("Packup", "Packup: " + ans);
            Log.i("Packup", "Packup: " + ans);
            Log.i("Packup", "Packup: " + ans);
            Log.i("Packup", "Packup: " + ans);
            execute("http://47.93.246.164:8080/TestCxfHibernate/REST/Domain/pack/sourcenode/"+sourcenode_id+"/targetnode/"+targetnode_id+"/userid/"+userid+"?_type=json", "POST", ans);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
