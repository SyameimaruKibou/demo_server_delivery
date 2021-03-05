package extrace.ui.main;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import extrace.loader.UserInfoLoader;
import extrace.misc.model.UserInfo;
import extrace.net.IDataAdapter;
import zxing.util.CaptureActivity;

public class UserInfoEditActivity  extends AppCompatActivity implements IDataAdapter<UserInfo> {

    public static final int REQUEST_CAPTURE = 100;

    private ExTraceApplication app;
    private UserInfo user;
    private UserInfoLoader mLoader;

    TextView tvSave;
    TextView tvID;
    TextView tvName;
    EditText etPwd;
    EditText etuName;
    TextView tvTelPhone;
    TextView tvRole;
    TextView tvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar1 = getSupportActionBar();
        if (actionBar1 != null) actionBar1.hide();
        setContentView(R.layout.activity_userinfo_edit);



        app = (ExTraceApplication)getApplication();
        user = app.getLoginUser();
        if(user==null){
            Log.i("xixixi","//////");
        }
        Log.i("嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻00","："+user.getUID());
       // Toast.makeText(this, user.getName()+"+"+user.getUname(), Toast.LENGTH_SHORT).show();

        xianshi();




     /***

        findIdAndSetText();
        //Toast.makeText(this, user.geTransPackageID()+"结果 ", Toast.LENGTH_SHORT).show();

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoader = new UserInfoLoader(UserInfoEditActivity.this, UserInfoEditActivity.this);
                //每次点击新建Loader,以刷新数据
                user.setPWD(etPwd.getText().toString());
                user.setUname(etuName.getText().toString());
                user.setTelCode(etTelPhone.getText().toString());
                Toast.makeText(UserInfoEditActivity.this, user+"结果 ", Toast.LENGTH_SHORT).show();

                mLoader.SaveUser(user);

                //StartCapture(); //测试扫码功能
            }
        });

      ****/

        TextView tvLogout  = (TextView) findViewById(R.id.user_tv_logout);
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(UserInfoEditActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关键的一句，将新的activity置为栈顶
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.arrow_left).setOnClickListener(//返回键
                new View.OnClickListener() {
                    public void onClick(View view) {
                        finish();
                    }
                });
    }


    @SuppressLint({"SetTextI18n", "CutPasteId"})
    public void xianshi(){

         tvSave = (TextView) findViewById(R.id.user_edit_tv_save);

         tvID = (TextView) findViewById(R.id.user_edit_tv_id);
         tvID.setText(user.getUname()+"");

         tvName = (TextView) findViewById(R.id.user_edit_tv_name);
         tvName.setText(user.getName());
         tvRole = (TextView) findViewById(R.id.user_tv_role);
         tvRole.setText(chenckRole(user.getURole()));

         etPwd = (EditText) findViewById(R.id.user_edit_tv_pwd);
         etPwd.setText(user.getPWD());
         tvTelPhone = (EditText) findViewById(R.id.user_edit_tv_telphone);
         tvTelPhone.setText(user.getTelCode());



    }

    public String chenckRole(int role){
        if(role==1){
            return "网点工作人员";
        }
        else if(role==2){
            return "分拣中心工作人员";
        }
        else if(role==3){
            return "司机";
        }
        else if(role==4){
            return "快递员";
        }
        return "出错！";
    }



    @Override
    public UserInfo getData() {

        return user;
    }

    @Override
    public void setData(UserInfo data) {

        user = data;

    }

    @Override
    public void notifyDataSetChanged() {

    }

    private void StartCapture(){
        Intent intent = new Intent();
        //intent.putExtra("Action","Captrue");
        intent.setClass(UserInfoEditActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CAPTURE);
    }


}
