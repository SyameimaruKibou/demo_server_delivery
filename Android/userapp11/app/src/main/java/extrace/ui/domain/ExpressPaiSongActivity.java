package extrace.ui.domain;

import android.annotation.SuppressLint;
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

import extrace.loader.ExpressListLoader;
import extrace.loader.RegionListLoader;
import extrace.misc.model.ExpressSheet;
import extrace.misc.model.UserInfo;
import extrace.net.IDataAdapter;
import extrace.ui.main.ExTraceApplication;
import extrace.ui.main.R;
import extrace.ui.misc.ExpressListAdapter;
import zxing.util.CaptureActivity;

public class ExpressPaiSongActivity extends AppCompatActivity implements IDataAdapter<List<ExpressSheet>> {
    private UserInfo user;
    private ExTraceApplication app;
    private EditText edt;
    private ImageView ivScan;
    private Button acok;
    private String result;
    private RegionListLoader regionListLoader;
    private List<ExpressSheet> expressSheetList;
    private ExpressListLoader mLoader;
    private LinearLayout message_ok;
    private TextView sendname,sendtele,sendadd,rcvname,rcvtele,rcvadd;
    private ImageView express_delive_ig_search;

    ListView list;
    ListView chalist;
    ExpressListAdapter mAdapter;
    View emptyView;
    private List<ExpressSheet> expresslist = new ArrayList<>(1);
    boolean search_ok=false;

    public static final int REQUEST_PAISONG = 106;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        setContentView(R.layout.activity_paisong_express_deliver);

        app = (ExTraceApplication) getApplication();//获取登录的快递员信息
        user = app.getLoginUser();

        edt=(EditText)findViewById(R.id.express_delive_deliver_et_pid) ;//快递ID的文本框
        express_delive_ig_search=findViewById(R.id.express_delive_ig_search);
        express_delive_ig_search.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {
                Log.i("嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻x嘻嘻嘻嘻嘻222222",""+result);
                Search();
            }
        });//根据ID查询快递信息的监听

        mLoader=new ExpressListLoader(this,this);//查询快递的loader


        emptyView = (View)findViewById(R.id.empty_tv);//快递员已经接收的快递list(默认为空值！！！！！)
        list = (ListView)findViewById(R.id.package_list);//listview部分
        chalist=(ListView)findViewById(R.id.charesult);//查询快递listview部分
        list.setEmptyView(emptyView);


        expresslist=app.getExpreseeList();
        mAdapter = new ExpressListAdapter(expresslist,this,"待派送");//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!后期需要改动！！！！
        TextView jieshou_x_jian=findViewById(R.id.jieshou_x_jian);
        jieshou_x_jian.setText("（共接收"+expresslist.size()+"件待派送快递）");
        list.setAdapter(mAdapter);


        ivScan = (ImageView)findViewById(R.id.express_delive_iv_scan);
        ivScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delive();
            }
        });//扫描二维码的监听

        acok=(Button)findViewById(R.id.express_rec_bt_ok);
        acok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delivery();
            }
        });//确认接收按钮的监听

        findViewById(R.id.arrow_left).setOnClickListener(//返回键
                new View.OnClickListener() {
                    public void onClick(View view) {
                        finish();
                    }
                });

    }

    @SuppressLint("LongLogTag")
    private void Search(){//查询快递ID
        result=edt.getText().toString();
        //Log.i("7777777777777777777777777777777777777777777777777777777777777777777",""+result);
        if(result.length()==32){
            mLoader.fuzzy_search(result);
        }else if(result.length()<5){
            Toast.makeText(ExpressPaiSongActivity.this, "查找快递至少需要输入后5位！", Toast.LENGTH_SHORT).show();
        }else{
            mLoader.fuzzy_search(result);
        }

    }

    //确认接收按钮的监听
    private void Delivery() {
        if(search_ok==false){
            Toast.makeText(ExpressPaiSongActivity.this, "请输入正确快递ID查询后接收！", Toast.LENGTH_SHORT).show();
            return;
        }

        for(int i=0;i<mAdapter.mChecked.size();i++){
            if(mAdapter.mChecked.get(i)){
                Log.i("选择性在选择选择选择选择选择选择","："+i);
                ExpressSheet es1=expressSheetList.get(i);
                boolean flag=false;
                for(int j=0;j<expresslist.size();j++){
                    ExpressSheet es2=expresslist.get(j);
                    if(es2.equals(es1)){
                        Toast.makeText(ExpressPaiSongActivity.this, "快递"+ es1.getID()+"已经存在待派送列表之中了！", Toast.LENGTH_SHORT).show();
                        flag=true; break;
                    }
                }
                if(!flag){
                    expresslist.add(es1);
                }
            }
        }

        app.setExpreseeList(expresslist);
        mAdapter = new ExpressListAdapter(expresslist,this,"待派送");//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!后期需要改动！！！！
        TextView jieshou_x_jian=findViewById(R.id.jieshou_x_jian);
        jieshou_x_jian.setText("（共接收"+expresslist.size()+"件待派送快递）");
        list.setAdapter(mAdapter);

    }


    private void delive(){//调用扫码
        Intent intent = new Intent();
        intent.setClass(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_PAISONG);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//获取扫码的结果！！！！！！！！
        super.onActivityResult(requestCode, resultCode, data);
        result = data.getExtras().getString("BarCode");//得到新Activity 关闭后返回的数据
        edt.setText(result);//填写到文本框之中！！！！！！！！！！！！！
        Search();
        Log.i("嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻x嘻嘻嘻嘻嘻1111",""+result);
    }

    @Override
    public List<ExpressSheet> getData() {
        return expressSheetList;
    }

    @Override
    public void setData(List<ExpressSheet> data) {
        expressSheetList=data;
    }


    @SuppressLint("LongLogTag")
    @Override
    public void notifyDataSetChanged() {
        //Log.i("777777777777777777777777777777777777777777777777777","ok");
        search_ok=true;
        message_ok=findViewById(R.id.message_ok);
        message_ok.setVisibility(View.VISIBLE);//快递的详情进行显示！！！！
        if(expressSheetList.size()!=0){
            mAdapter = new ExpressListAdapter(expressSheetList,this,"待接收");//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!后期需要改动！！！！
            chalist.setAdapter(mAdapter);
            TextView sousuo_x_tiao=findViewById(R.id.sousuo_x_tiao);
            sousuo_x_tiao.setText("（共搜索到"+expressSheetList.size()+"条相关快递信息）");
        }else{
            TextView sousuo_x_tiao=findViewById(R.id.sousuo_x_tiao);
            sousuo_x_tiao.setText("（共搜索到0条相关快递信息）");
        }

    }
}
