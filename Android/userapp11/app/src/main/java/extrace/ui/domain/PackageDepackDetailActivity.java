package extrace.ui.domain;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import extrace.loader.ExpressListLoader;
import extrace.misc.model.ExpressSheet;
import extrace.misc.model.UserInfo;
import extrace.net.IDataAdapter;
import extrace.ui.main.ExTraceApplication;
import extrace.ui.main.Network_tmp_Activity;
import extrace.ui.main.R;
import extrace.ui.main.Sorter_tmp_Activity;
import zxing.util.CaptureActivity;

import static extrace.misc.model.UserInfo.ROLE.CENTER_WORKER;
import static extrace.misc.model.UserInfo.ROLE.NODE_WORKER;

public class PackageDepackDetailActivity extends AppCompatActivity implements IDataAdapter<List<ExpressSheet>> {

    private Button btn;
    private TextView tvid,tvcount,tvfrom,tvto,jieshou_x_jian;
    private ImageView  imgscan;
    private String searchnumber ="";//快件号
    private String pkid="";//包裹号
    private String nodeid,userid;
    private ListView list;
    private List<ExpressSheet> mlist =  new ArrayList<ExpressSheet> ();
    private PackageDepackDetailAdapter depackDetailAdapter;
    private ExpressListLoader mloader ;
    private ExTraceApplication app;
    public static final int REQUEST_PACK_DETIAL = 23;
    private boolean flag =false;//扫描之后的标志
    private boolean exit = false;//扫描的快件是否在包裹里面
    private int listcount=0;
    private int tot=0;
    private UserInfo user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        setContentView(R.layout.activity_package_depack_detail);
        //app = (ExTraceApplication) getApplication();
        //userid = app.getLoginUser().getUname();

        tvid = (TextView) findViewById(R.id.id_package_number);
        tvcount= (TextView) findViewById(R.id.count_package);
        tvfrom = (TextView) findViewById(R.id.tv_from);
        tvto = (TextView) findViewById(R.id.tv_to);

        Intent i = new Intent();//包裹基本信息
        i =getIntent();
        pkid = i.getStringExtra("ID");
        tvid.setText(pkid);
        Toast.makeText(this, "包裹id"+pkid, Toast.LENGTH_SHORT).show();
        // tvcount.setText(i.getIntExtra("count",0));
        // Toast.makeText(this, "count"+i.getIntExtra("count",0), Toast.LENGTH_SHORT).show();
        tvfrom.setText(i.getStringExtra("source"));
        tvto.setText(i.getStringExtra("target"));//listview上的包裹信息赋值

        app = (ExTraceApplication)getApplication();
        nodeid=app.getNode().getID();
        userid=String.valueOf(app.getLoginUser().getUID());

        btn = (Button)findViewById(R.id.btn_package_ok);//拆包确认
        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {

                mloader = new ExpressListLoader(PackageDepackDetailActivity.this,PackageDepackDetailActivity.this);
                Log.i("5555555555555555555555555555555555555555555",""+pkid+nodeid+userid);
                mloader.DepackTransPackage(pkid,nodeid,userid);
                //1打包 0拆包

                Toast.makeText(getApplication(), "包裹"+pkid+"已拆包！", Toast.LENGTH_SHORT).show();
                Intent intent  = new Intent();

                user=app.getLoginUser();
                if(user.getURole()==NODE_WORKER){
                    intent.setClass(getApplicationContext(), Network_tmp_Activity.class);
                }else if(user.getURole()==CENTER_WORKER){
                    intent.setClass(getApplicationContext(), Sorter_tmp_Activity.class);
                }
                startActivity(intent);
            }
        });
        list = (ListView)findViewById(R.id.package_content_list);//listview
        jieshou_x_jian=findViewById(R.id.jieshou_x_jian);

        mloader = new ExpressListLoader(PackageDepackDetailActivity.this,PackageDepackDetailActivity.this);
        mloader.LoadExpressListInPackage(pkid);

        imgscan = (ImageView)findViewById(R.id.scan);
        imgscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_PACK_DETIAL );
            }
        });

        findViewById(R.id.arrow_left).setOnClickListener(//返回键
                new View.OnClickListener() {
                    public void onClick(View view) {
                        finish();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_PACK_DETIAL:
                if (data.hasExtra("BarCode")) {
                    searchnumber = data.getStringExtra("BarCode");
                    flag = true;
                    this.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public List<ExpressSheet> getData() {
        return mlist;
    }

    @Override
    public void setData(List<ExpressSheet> data) {
        mlist=data;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void notifyDataSetChanged() {
        //   Toast.makeText(this, "我现在用到了notifyDataSetChanged()方法!"+es.getID(), Toast.LENGTH_SHORT).show();

        tot=mlist.size();
        tvcount.setText(String.valueOf(mlist.size()));
        depackDetailAdapter = new PackageDepackDetailAdapter(mlist,this);//快件列表
        list.setAdapter(depackDetailAdapter);


        //Toast.makeText(this, "分拣快件，其快件号为"+searchnumber, Toast.LENGTH_SHORT).show();//1打包 0拆包
        if(mlist.size()==0){
            Toast.makeText(this, "包裹为null!", Toast.LENGTH_SHORT).show();
        }
        else{
            if(flag){
                //Toast.makeText(this, "包裹不为空!长度为"+mlist.size(), Toast.LENGTH_SHORT).show();
                for(int i = 0;i<mlist.size();i++){
                    if(mlist.get(i).getID().equals(searchnumber)){
                        mlist.get(i).setStatus(0);
                        exit = true;
                        listcount++;//分拣+1
                        ExpressSheet temp;
                        temp = mlist.get(i);
                        //mlist.get(i)=mlist.get(0);
                        mlist.remove(i);
                        mlist.add(mlist.size(),temp);//将此次分拣的快递放到末尾
                        Toast.makeText(this, "分拣快件，其快件号为"+searchnumber, Toast.LENGTH_SHORT).show();//1打包 0拆包
                        depackDetailAdapter.notifyDataSetChanged();
                        break;
                    }
                }
                if(!exit){
                    Toast.makeText(this, "包裹里无快件号为"+searchnumber+"的快件", Toast.LENGTH_SHORT).show();
                }
                flag = false;
                exit = false;

            }
        }
        //当分拣完毕时就使用拆包的接口
        if(listcount == mlist.size()){

        }

        jieshou_x_jian.setText("（分拣进度"+listcount+"/"+tot+"）");

    }
}
