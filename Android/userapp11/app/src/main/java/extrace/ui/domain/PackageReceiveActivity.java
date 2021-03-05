package extrace.ui.domain;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import extrace.loader.TransPackageLoader;
import extrace.misc.model.TransNode;
import extrace.misc.model.TransPackage;
import extrace.misc.model.UserInfo;
import extrace.net.IDataAdapter;
import extrace.ui.main.ExTraceApplication;
import extrace.ui.main.R;
import extrace.ui.misc.PackageListAdapter;
import zxing.util.CaptureActivity;

public class PackageReceiveActivity extends AppCompatActivity implements IDataAdapter<TransPackage> {
    public static final int REQUEST_SearchPid = 666;
    ImageView ivScan;
    ImageView search;
    EditText etPackageId;
    Button btOk;
    TransPackageLoader mloader;

    String Pid;
    UserInfo user;
    private ExTraceApplication app;
    String result;
    TransNode begplace;
    TransNode endplace;
    TransNode begtmp;
    TransNode endtmp;
    LinearLayout driver_package_message;
    TextView trans_package_id;


    ListView list;
    PackageListAdapter mAdapter;
    View emptyView;
    private List<TransPackage> traceList = new ArrayList<>(1);
    private List<TransPackage> charesultlis= new ArrayList<>(1);

    boolean searchok=false;
    boolean tpexit=true;

    private TransPackage tp;
    TransPackage tn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        setContentView(R.layout.activity_receive_package_driver);

        ivScan = (ImageView) findViewById(R.id.package_trans_iv_scan);//扫二维码按钮
        etPackageId = (EditText) findViewById(R.id.package_rec_driver_et_pid);//包裹id的编辑框
        btOk = (Button) findViewById(R.id.package_receive_bt_ok);//确认接收按钮

        ivScan.setOnClickListener(new View.OnClickListener() {//扫码获取结果
            @Override
            public void onClick(View view) {
                getcode();
            }
        });

        app = (ExTraceApplication) getApplication();//获取司机的信息！！！！
        user = app.getLoginUser();

        emptyView = (View)findViewById(R.id.empty_tv);///////////////////////////////////////////////////////////////////////
        list = (ListView)findViewById(R.id.package_list);//listview部分
        list.setEmptyView(emptyView);
/*
        begplace=new TransNode();
        endplace=new TransNode();
        begplace.setNodeName("aaaa");
        endplace.setNodeName("bbbb");
        tn=new TransPackage();
        tn.setID("123456");
        tn.setSourcenode(begplace);
        tn.setTargetnode(endplace);
        tn.setCreateTime(new Date());
        tn.setStatus(1);
*/


        traceList=app.getTraceList();
        mAdapter = new PackageListAdapter(traceList,this);
        list.setAdapter(mAdapter);

        driver_package_message=findViewById(R.id.driver_package_message);//整个布局


        search=findViewById(R.id.package_trans_search);//搜索包裹信息的按钮
        search.setOnClickListener(new View.OnClickListener() {//将文本框内的东西进行搜索
            @Override
            public void onClick(View view) {
                //需要查询此包裹的信息
                Search(etPackageId.getText().toString());

            }
        });




        btOk.setOnClickListener(new View.OnClickListener() {//确认揽收按钮的监听
            @Override
            public void onClick(View view) {
                if(searchok==false){
                    Toast.makeText(PackageReceiveActivity.this, "请先查询此包裹！", Toast.LENGTH_SHORT).show();
                    return;
                }
                tp=charesultlis.get(0);
                Pid =tp.getID();

                if(begplace==null&&endplace==null){
                    begplace=begtmp;
                    endplace=endtmp;
                    app.setBegnode(begplace);
                    app.setEndnode(endplace);
                }else{
                    if(begplace!=begtmp||endplace!=endtmp){
                        Toast.makeText(PackageReceiveActivity.this, "添加失败！起终点不一样！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                mloader = new TransPackageLoader(PackageReceiveActivity.this, PackageReceiveActivity.this);

                Log.i("一一一一一一一一一一一一一一一一一一一一",""+tp.getCreateTime()+"====="+tp.getID());
                Toast.makeText(PackageReceiveActivity.this, ""+tp.getID()+"==="+tp.getCreateTime(), Toast.LENGTH_SHORT).show();

                mloader.Load(Pid,String.valueOf(user.getUID()));
                if(tpexit==false){
                    Log.i("错错错粗粗偶凑错错错错错3","：null");
                    tpexit=true;
                    return;
                }else{
                    Log.i("错错错粗粗偶凑错错错错错2","：null"+tp.getID());
                }
                boolean flag=false;
                for(int i=0;i<traceList.size();i++){
                    TransPackage tp1=traceList.get(i);
                    if(tp1.equals(tp)){
                        flag=true; break;
                    }
                }
                if(flag==false){
                    Toast.makeText(PackageReceiveActivity.this, "添加成功！起点："+begplace+"->终点："+endplace, Toast.LENGTH_SHORT).show();
                    app.setBegnode(tp.getSourcenode());
                    app.setEndnode(tp.getTargetnode());
                    traceList.add(tp);
                    app.setTraceList(traceList);
                    mAdapter = new PackageListAdapter(traceList,PackageReceiveActivity.this);
                    list.setAdapter(mAdapter);
                }

            }
        });

        findViewById(R.id.arrow_left).setOnClickListener(//返回键
                new View.OnClickListener() {
                    public void onClick(View view) {
                        finish();
                    }
                });
    }

    private  void Search(String id){
        searchok=false;//修改成没有查询成功！！！
        Pid=id;
        if(Pid.length()!=32){//包裹的id为多少位啊？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
            Toast.makeText(PackageReceiveActivity.this, "包裹ID为32位！", Toast.LENGTH_SHORT).show();
            return;
        }
        mloader = new TransPackageLoader(PackageReceiveActivity.this, PackageReceiveActivity.this);
        //Log.i("书茹茹茹茹茹茹茹茹茹茹茹茹茹茹茹茹茹茹茹",""+Pid);
        mloader.getTransPackage(Pid);

    }

    private void getcode(){
        Intent intent = new Intent();
        intent.setClass(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_SearchPid);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//获取扫码的结果！！！！！！！！
        super.onActivityResult(requestCode, resultCode, data);
        result = data.getExtras().getString("BarCode");//得到新Activity 关闭后返回的数据
        etPackageId.setText(result);//填写到文本框之中！！！！！！！！！！！！！
        Search(result);
    }

    @Override
    public TransPackage getData() {
        return tp;
    }

    @Override
    public void setData(TransPackage data) {
        tp=data;
    }

    @Override
    public void notifyDataSetChanged() {
        //Toast.makeText(this, "成功进入111111", Toast.LENGTH_SHORT).show();
        if(tp.getID().equals("NO")){
            tpexit=false;
            return;
        }

        driver_package_message.setVisibility(View.VISIBLE);//改成可以看见
        while(charesultlis.size()!=0){
            charesultlis.remove(0);
        }
        charesultlis.add(tp);
        ListView charresult=findViewById(R.id.charesult);
        mAdapter = new PackageListAdapter(charesultlis,this);
        charresult.setAdapter(mAdapter);

        begtmp=tp.getSourcenode();
        endtmp=tp.getTargetnode();
        searchok=true;
        //Toast.makeText(this, "成功进入 notifyDataSetChanged()", Toast.LENGTH_SHORT).show();
    }
}
