package extrace.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import extrace.loader.UserInfoLoader;
import extrace.misc.model.UserInfo;
import extrace.net.IDataAdapter;

public class LoginActivity extends AppCompatActivity implements IDataAdapter<UserInfo> {

    CheckBox cbPwd;
    CheckBox cbLogin;
    EditText etUserId;
    EditText etUserPwd;
    Button tvUserLogin;
    Spinner spinner;
    TextView tvSet;
    String userId;
    String userPwd;
    UserInfo user;
    private UserInfo mItem;
    private UserInfoLoader mLoader;
    SharedPreferences sp;
    SharedPreferences.Editor et;
    private ExTraceApplication app;

    public static final String RPWD="rpwd";
    public static final String EID="eid";
    public static final String EPWD="epwd";
    public static final String RLOGIN="rlogin";
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    @SuppressLint("HandlerLeak")
    Handler mHandler=new Handler() {

        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar1 = getSupportActionBar();
        if (actionBar1 != null) actionBar1.hide();

        setContentView(R.layout.activity_login);
        etUserId = (EditText) findViewById(R.id.login_et_userid);
        etUserPwd = (EditText) findViewById(R.id.login_et_userpwd);
        tvUserLogin = (Button) findViewById(R.id.login_tv_login);
        cbLogin = (CheckBox) findViewById(R.id.checkBox_login);
        cbPwd = (CheckBox) findViewById(R.id.checkBox_password);
        tvSet = (TextView) findViewById(R.id.login_tv_set);
        spinner=(Spinner) findViewById(R.id.spin);
        sp = getPreferences(MODE_PRIVATE);//模式:其他app是否能看到
        et = sp.edit();//修改器
        String rpwd = new String();	//记住密码
        String rlogin = new String();	//自动登录
        rpwd = sp.getString(RPWD,"F");
        rlogin = sp.getString(RLOGIN,"F");



        app = (ExTraceApplication) getApplication();
        String PREFS_NAME = "ExTrace.cfg";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        String URL = settings.getString("ServerUrl","noData");
        if(!"noData".equals(URL))	{
          //  app.setServerUrl(URL);/////////////////////////////////////////////////////////////////////////////////
        }

        if (rpwd.equals("T")) {
            cbPwd.setChecked(true);
            etUserId.setText(sp.getString(EID,""));
            etUserPwd.setText(sp.getString(EPWD,""));
        }

     //   Toast.makeText(LoginActivity.this, URL+rlogin+"存储"+rpwd, Toast.LENGTH_SHORT).show();///////////////////////////////////////////////////

        tvUserLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {		//登录监听


                userId = etUserId.getText().toString();
                userPwd = etUserPwd.getText().toString();
                mLoader = new UserInfoLoader(LoginActivity.this, LoginActivity.this);
         //       Toast.makeText(LoginActivity.this, ""+userId+"--"+userPwd, Toast.LENGTH_SHORT).show();
                mLoader.LoadUserInfoByUName(userId,userPwd);


                et.putString(EID,etUserId.getText().toString());
                et.putString(EPWD,etUserPwd.getText().toString());
                et.commit();			//每次登陆重新在内存中写入密码



            }
        });

        if (rlogin.equals("T")) {
            cbLogin.setChecked(true);
            tvUserLogin.performClick();	//模拟点击事件 且位置一定要放到注册监听事件之后,否则无法调用
        }

        cbPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {		//记住密码监听
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
              //  Toast.makeText(LoginActivity.this, "第一步!", Toast.LENGTH_SHORT).show();
                if(isChecked){
                    et.putString(RPWD,"T");//存入键值对
                    et.putString(EID,etUserId.getText().toString());                              //保存密码复选框监听
                    et.putString(EPWD,etUserPwd.getText().toString());
                    et.commit();//将缓存区里面的操作写入文件


                }else{
                    et.putString(RPWD,"F");//存入键值对
                    et.commit();//将缓存区里面的操作写入文件

                }
            }
        });

        cbLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {		//自动登录监听
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    et.putString(RLOGIN,"T");//存入键值对                                          //保存登陆复选框监听
                    et.commit();//将缓存区里面的操作写入文件

                }else{
                    et.putString(RLOGIN,"F");//存入键值对
                    et.commit();//将缓存区里面的操作写入文件

                }
            }
        });

        tvSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {                               //设置监听
                Intent nextIntent = new Intent(LoginActivity.this,SetActivity.class);
                startActivity(nextIntent);
            }
        });


    }

    public boolean onOptionsItemSelected(MenuItem item) {/////////////////////////////////////////点击设置没反应，重新返回原界面！！！！！！
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public UserInfo getData() {
        return mItem;
    }

    @Override
    public void setData(UserInfo data) {
        mItem=data;
        app.setUserInfo(mItem);
    }

    @Override
    public void notifyDataSetChanged() {
        //一定要在此方法中进行数据处理
        String pwd = mItem.getPWD();//不为空的情况


    //    Toast.makeText(LoginActivity.this, mItem.getUname()+"+"+mItem.getName()+"+"+mItem.getPWD()+"+"+mItem.getURole()+"!!!!!!!!!!!!!!!!!!!!!!", Toast.LENGTH_SHORT).show();


        if(userPwd.equals(pwd)) {

            Spinner spinner=findViewById(R.id.spin);
            String loginrole=spinner.getSelectedItem().toString();

            Integer role=mItem.getURole();
            if(role==1&&loginrole.equals("网点人员")){
                Toast.makeText(LoginActivity.this, "亲爱的网点人员"+mItem.getName()+"，你好！", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(LoginActivity.this,Network_tmp_Activity.class);
                //Intent intent2 = new Intent(LoginActivity.this, PackagePackupOkActivity.class);
                startActivity(intent2);
                LoginActivity.this.finish();

            }else if(role==2&&loginrole.equals("分拣站")){ //分拣中心人员
                Toast.makeText(LoginActivity.this, "亲爱的分拣站人员"+mItem.getName()+"，你好！", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(LoginActivity.this,Sorter_tmp_Activity.class);
                startActivity(intent2);
                LoginActivity.this.finish();

            }else if(role==3&&loginrole.equals("司机")){ //司机
                Toast.makeText(LoginActivity.this, "亲爱的司机"+mItem.getName()+"，你好！", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(LoginActivity.this,Driver_tmp_Activity.class);
                startActivity(intent2);
                LoginActivity.this.finish();

            }else if(role==4&&loginrole.equals("快递员")){ //快递员
                Toast.makeText(LoginActivity.this, "亲爱的快递员"+mItem.getName()+"，你好！", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(LoginActivity.this,Deliver_tmp_Activity.class);
                startActivity(intent2);
                LoginActivity.this.finish();
            }else{
                Toast.makeText(LoginActivity.this, "密码错误!", Toast.LENGTH_SHORT).show();
            }

        }
        else {
            Toast.makeText(LoginActivity.this, "密码错误!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }
}
