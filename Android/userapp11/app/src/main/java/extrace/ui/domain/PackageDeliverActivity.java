package extrace.ui.domain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import extrace.loader.TransPackageLoader;
import extrace.loader.UserInfoLoader;
import extrace.misc.model.TransPackage;
import extrace.misc.model.UserInfo;
import extrace.net.IDataAdapter;
import extrace.ui.main.ExTraceApplication;
import extrace.ui.main.R;
import extrace.ui.misc.PackageListAdapter;
import zxing.util.CaptureActivity;

public class PackageDeliverActivity extends AppCompatActivity implements IDataAdapter<TransPackage> {

    public static final int REQUEST_SearchPid = 666;
    ImageView ivScan;
    EditText etPackageId;
    Button btOk;
    TransPackageLoader mloader;
    ImageView search;

    String Pid;
    UserInfo user;
    private ExTraceApplication app;

    ListView list;
    PackageListAdapter mAdapter;
    View emptyView;
    private List<TransPackage> traceList= new ArrayList<>(1);
    private List<TransPackage> traceTmp= new ArrayList<>(1);

    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        setContentView(R.layout.activity_deliver_package_driver);
        ivScan = (ImageView) findViewById(R.id.package_trans_iv_scan);
        etPackageId = (EditText) findViewById(R.id.package_del_driver_et_pid);
        btOk = (Button) findViewById(R.id.package_deliver_bt_ok);

        app = (ExTraceApplication) getApplication();
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

        ivScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getcode();
            }
        });

        search=findViewById(R.id.package_trans_search);//搜索包裹信息的按钮
        search.setOnClickListener(new View.OnClickListener() {//将文本框内的东西进行搜索
            @Override
            public void onClick(View view) {
                //需要查询此包裹的信息
                String id=etPackageId.getText().toString();
                traceTmp.clear();
                for(int i=0;i<traceList.size();i++){
                    TransPackage tpTmp=traceList.get(i);
                    if(tpTmp.getID().equals(id)){
                        traceTmp.add(tpTmp);
                        break;
                    }
                }
                mAdapter = new PackageListAdapter(traceTmp,PackageDeliverActivity.this);
                list.setAdapter(mAdapter);
                if(traceTmp.size()==0){
                    Toast.makeText(PackageDeliverActivity.this, "没有符合要求的包裹！", Toast.LENGTH_SHORT).show();
                }
            }
        });






        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pid = etPackageId.getText().toString();
                //后期需要进行修改！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
                for(int i=0;i<traceList.size();i++){
                    TransPackage tpTmp=traceList.get(i);
                    if(tpTmp.getID().equals(Pid)){
                        traceList.remove(i);
                        break;
                    }
                }
                mloader = new TransPackageLoader(PackageDeliverActivity.this, PackageDeliverActivity.this);
                mloader.unLoad(Pid,user.getUID()+"");
                Toast.makeText(PackageDeliverActivity.this, "交付成功！"+Pid, Toast.LENGTH_SHORT).show();
                if(traceList.size()==0){
                    app.setBegnode(null);
                    app.setEndnode(null);
                }
                app.setTraceList(traceList);
                mAdapter = new PackageListAdapter(traceList,PackageDeliverActivity.this);
                list.setAdapter(mAdapter);

            }
        });

        findViewById(R.id.arrow_left).setOnClickListener(//返回键
                new View.OnClickListener() {
                    public void onClick(View view) {
                        finish();
                    }
                });
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
    }

    @Override
    public TransPackage getData() {
        return null;
    }

    @Override
    public void setData(TransPackage data) {

    }

    @Override
    public void notifyDataSetChanged() {
        UserInfoLoader mLoader = new UserInfoLoader(PackageDeliverActivity.this);
        //每次点击新建Loader,以刷新数据
        mLoader.LoadUserInfoByID(user.getUID()+"",user.getPWD());
    }
}
