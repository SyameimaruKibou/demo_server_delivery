package extrace.ui.domain;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

import extrace.loader.ExpressLoader;
import extrace.loader.RegionListLoader;
import extrace.misc.model.ExpressSheet;
import extrace.misc.model.UserInfo;
import extrace.net.IDataAdapter;
import extrace.ui.main.ExTraceApplication;
import extrace.ui.main.R;
import extrace.ui.misc.ExpressListAdapter;
import zxing.util.CaptureActivity;

public class ExpressDeliveActivity extends AppCompatActivity implements IDataAdapter<ExpressSheet> {

    private UserInfo user;
    private ExTraceApplication app;
    private EditText edt;
    private ImageView ivScan;
    private Button acok;
    private String result;
    private String province,city,town;
    private RegionListLoader regionListLoader;
    private ExpressSheet expressSheet;
    private ExpressLoader mLoader;
    private TextView sendname,sendtele,sendadd,sendprocity,rcvname,rcvtele,rcvadd,rcvprocity;
    private ImageView express_delive_ig_search;
    private List<ExpressSheet> expressSheetList= new ArrayList<>(1);

    ListView list;
    ExpressListAdapter mAdapter;
    View emptyView;
    private List<ExpressSheet> expresslist = new ArrayList<>(1);
    boolean search_ok=false;

    private  String tmp=null;

    public static final int REQUEST_PAISONG = 106;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        setContentView(R.layout.activity_delive_express_deliver);

        app = (ExTraceApplication) getApplication();//获得快递员的信息
        user = app.getLoginUser();

        edt=(EditText)findViewById(R.id.express_delive_deliver_et_pid) ;//快递ID的编辑框
        express_delive_ig_search=findViewById(R.id.express_delive_ig_search);
        express_delive_ig_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search();
            }
        });//根据ID查询快递信息的监听

        mLoader=new ExpressLoader(this,this);//建立Loader
      //  mLoader.Load(result);
        //region转换



        emptyView = (View)findViewById(R.id.empty_tv);//快递员等待揽收
        // 的快递list(默认为空值！！！！！)
        list = (ListView)findViewById(R.id.express_deliever_list);//listview部分
        list.setEmptyView(emptyView);

        expresslist=app.getExpreseeList();
        mAdapter = new ExpressListAdapter(expresslist,this,"待派送");//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!后期需要改动！！！！
        list.setAdapter(mAdapter);


        ivScan = (ImageView)findViewById(R.id.express_delive_iv_scan);//扫描二维码监听
        ivScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delive();
            }
        });

        acok=(Button)findViewById(R.id.express_rec_bt_ok);//确认签收的监听
        acok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assign();
            }
        });

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
        if(result.length()!=32){
            Toast.makeText(this, "现在查询只支持快递ID号码为32位！", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean yes=false;
        for(int i=0;i<expresslist.size();i++){
            ExpressSheet tmp=expresslist.get(i);
            if(result.equals(tmp.getID())==true){
                xianshi(tmp);
                yes=true;
            }
        }
        if(yes==true){
            Toast.makeText(this, "查询成功！", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "您输入的快递单号不在待派送列表之中！", Toast.LENGTH_SHORT).show();
        }
    }

    private void xianshi(ExpressSheet es){//将查询到的结果显示出来
        search_ok=true;
        LinearLayout message_ok=findViewById(R.id.message_ok);
        message_ok.setVisibility(View.VISIBLE);//快递的详情进行显示！！！！
        while(expressSheetList.size()!=0){
            expressSheetList.remove(0);
        }
        expressSheetList.add(es);
        ListView chalist=findViewById(R.id.charesult);
        mAdapter = new ExpressListAdapter(expressSheetList,this,"待签收");//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!后期需要改动！！！！
        chalist.setAdapter(mAdapter);



        Toast.makeText(this, "notifyDataSetChanged()查询成功！！！！！！！！！！", Toast.LENGTH_SHORT).show();

    }

    private void assign() {
        if(search_ok==false){
            Toast.makeText(this, "请先查询之后再签收！", Toast.LENGTH_SHORT).show();
            return;
        }
      // 这里的result后面需要进行修改，不能需要现在的result；
        mLoader.Assign(result,String.valueOf(user.getUID()));//签收
        for(int i=0;i<expresslist.size();i++){
            ExpressSheet tmp=expresslist.get(i);
            if(result.equals(tmp.getID())==true){
                expresslist.remove(i);
                i--;
            }
        }
        mAdapter = new ExpressListAdapter(expresslist,this,"待派送");//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!后期需要改动！！！！
        list.setAdapter(mAdapter);
        Toast.makeText(this, "签收成功！！！", Toast.LENGTH_SHORT).show();
    }


    private void delive(){//调用扫描二维码的页面
        Intent intent = new Intent();
        intent.setClass(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_PAISONG);
    }

    private String turnregion(String result){

        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//获取二维码扫描的快递ID号码
        super.onActivityResult(requestCode, resultCode, data);
        result = data.getExtras().getString("BarCode");//得到新Activity 关闭后返回的数据
        edt.setText(result);
    }



    @Override
    public ExpressSheet getData() {
        return expressSheet;
    }

    @Override
    public void setData(ExpressSheet data) {
        expressSheet = data;
    }

    @Override
    public void notifyDataSetChanged() {


    }
}