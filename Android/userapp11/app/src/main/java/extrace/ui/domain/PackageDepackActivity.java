package extrace.ui.domain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import extrace.loader.TransPackageLoader;
import extrace.misc.model.TransPackage;
import extrace.misc.model.UserInfo;
import extrace.net.IDataAdapter;
import extrace.ui.main.ExTraceApplication;
import extrace.ui.main.R;
import zxing.util.CaptureActivity;

public class PackageDepackActivity extends AppCompatActivity  implements IDataAdapter<TransPackage> {

    private UserInfo user;
    private ExTraceApplication app;
    private ImageView ivScan;
    private String result="";
    private Button btn;
    private TransPackage transPackage;
    private TransPackageLoader mloader;
    private EditText searchnumber;
    private String id,source,target;
    private int count;

    public static final int REQUEST_DEPACK = 106;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        setContentView(R.layout.activity_package_depack);
        app = (ExTraceApplication) getApplication();
        user = app.getLoginUser();


        searchnumber = (EditText)findViewById(R.id.express_delive_deliver_et_pid);
        ivScan = (ImageView)findViewById(R.id.packgae_depack_iv_scan);
        ivScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_DEPACK);
            }
        });




        btn = (Button)findViewById(R.id.express_rec_bt_ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchNumber();
            }
        });

        findViewById(R.id.arrow_left).setOnClickListener(//返回键
                new View.OnClickListener() {
                    public void onClick(View view) {
                        finish();
                    }
                });



    }


    void SearchNumber()//按照快递单号查找
    {
        result = searchnumber.getText().toString();
        mloader = new TransPackageLoader(PackageDepackActivity.this,PackageDepackActivity.this);
        mloader.GetTransPackageById(result);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_DEPACK:
                if (data.hasExtra("BarCode")) {            //打包
                    searchnumber.setText(data.getStringExtra("BarCode"));

                }
                break;
        }
    }

    @Override
    public TransPackage getData() {
        return transPackage;
    }

    @Override
    public void setData(TransPackage data) {
        transPackage=data;
    }

    @Override
    public void notifyDataSetChanged() {

        Toast.makeText(this, "我现在用到了notifyDataSetChanged()方法!", Toast.LENGTH_SHORT).show();
        id = transPackage.getID();
        Toast.makeText(this, "notifyDataSetChanged()包裹id:"+id, Toast.LENGTH_SHORT).show();//有了
        // count = transPackage.getContent().size();
        // Toast.makeText(this, transPackage.getContent().size(), Toast.LENGTH_SHORT).show();
        source =transPackage.getSourcenode().getNodeName();
        target = transPackage.getTargetnode().getNodeName();

        if(result!=null&&result.length()==32){
            //Toast.makeText(this.getActivity(), "快件号为"+searchnumber.getText(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this,PackageDepackDetailActivity.class);
            intent.putExtra("ID",id);
            intent.putExtra("count",count);
            intent.putExtra("source",source);
            intent.putExtra("target",target);
            Toast.makeText(this, "包裹id:"+id, Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
        else if(result==null){
            Toast.makeText(this, "请输入包裹号！", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "请输入正确的包裹号！", Toast.LENGTH_SHORT).show();
            searchnumber.setText("");
        }
    }
}
