package extrace.ui.domain;

import android.content.Intent;
import android.os.Bundle;
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

import extrace.loader.ExpressLoader;
import extrace.misc.model.ExpressSheet;
import extrace.net.IDataAdapter;
import extrace.ui.main.ExTraceApplication;
import extrace.ui.main.R;
import zxing.util.CaptureActivity;

public class PackagePackupActivity extends AppCompatActivity implements IDataAdapter<ExpressSheet>{

    private Button btn;
    private TextView tvid,tvcount,tvfrom,tvto;
    private ImageView imgscan;
    private String searchnumber ="";
    private String targetid,targetname;
    private ListView list;
    private List<ExpressSheet> mlist =  new ArrayList<ExpressSheet>();
    private ArrayList<String> stringList = new ArrayList<String>();
    private ExpressSheet expressSheet;
    private PackagePackupAdapter depackDetailAdapter;
    private ExpressLoader mloader ;
    private ExTraceApplication app;
    // private EditText edt;

    public static final int REQUEST_PACK_DETIAL = 121;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar1 = getSupportActionBar();
        if (actionBar1 != null) actionBar1.hide();
        setContentView(R.layout.activity_package_packup);

        btn = (Button)findViewById(R.id.btn_package_ok);
        btn.setOnClickListener(new View.OnClickListener() {//添加快递完毕，进行打包
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplication(),stringList.get(0)+stringList.get(1) , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(PackagePackupActivity.this, PackagePackupOkActivity.class);
                intent.putStringArrayListExtra("list",stringList);
                intent.putExtra("targetid",targetid);
                intent.putExtra("targetname",targetname);
                startActivity(intent);    //激发下层编辑-建立新的
                finish();
            }
        });

        app = (ExTraceApplication)getApplication();//获取登陆者的信息

        list = (ListView)findViewById(R.id.package_content_list);//listview
        View emptyview = (View)findViewById(R.id.empty);
        list.setEmptyView(emptyview);
        depackDetailAdapter = new PackagePackupAdapter(mlist,this);
        list.setAdapter(depackDetailAdapter);

        //edt = (EditText)findViewById(R.id.express_id);

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
                    mloader = new ExpressLoader(PackagePackupActivity.this,PackagePackupActivity.this);
                    mloader.Load(searchnumber);
                }
                break;
        }
    }


    @Override
    public ExpressSheet getData() {
        return expressSheet;
    }

    @Override
    public void setData(ExpressSheet data) {
        expressSheet=data;
    }


    @Override
    public void notifyDataSetChanged() {
        //Toast.makeText(this, "我现在用到了notifyDataSetChanged()方法!"+expressSheet.getID(), Toast.LENGTH_SHORT).show();

        stringList.add(searchnumber);//List<String>（数据）
        boolean flag =false;//是否重复
        for(int i = 0;i<mlist.size();i++){
            if(mlist.get(i).getID().equals(searchnumber)){
                flag = true;//有重复
                Toast.makeText(this, searchnumber+"已在列表里！", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        if(!flag){
            expressSheet.setSenderregcode(app.getNode().getNodeName());
            mlist.add(expressSheet);//List<ExpressSheet>(显示）
            // Toast.makeText(getApplication(), "打包快件，其快件号为"+expressSheet.getID()+"寄件人为"+expressSheet.getSendername(), Toast.LENGTH_SHORT).show();
            targetname = expressSheet.getNextnode().getNodeName();
            Toast.makeText(getApplication(), "下一站："+expressSheet.getNextnode().getNodeName(), Toast.LENGTH_SHORT).show();
            targetid = expressSheet.getNextnode().getID();
            depackDetailAdapter.notifyDataSetChanged();
            depackDetailAdapter = new PackagePackupAdapter(mlist,this);
            list.setAdapter(depackDetailAdapter);
        }
    }
}
