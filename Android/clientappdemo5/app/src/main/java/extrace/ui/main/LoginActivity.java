package extrace.ui.main;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import extrace.loader.CustomerLoader;
import extrace.misc.model.Customer;
import extrace.net.IDataAdapter;

public class LoginActivity extends AppCompatActivity implements IDataAdapter<Customer> {

    CheckBox cbPwd;
    CheckBox cbLogin;
    EditText etUserId;
    EditText etUserPwd;
    Button tvUserLogin;
    String userId;
    String userPwd;
    TextView otherlogin;
    TextView tvregister;
    private Customer mItem;
    private CustomerLoader mLoader;

    SharedPreferences sp;
    SharedPreferences.Editor et;
    private ExTraceApplication app;

    public static final String RPWD="rpwd";
    public static final String EID="eid";
    public static final String EPWD="epwd";
    public static final String RLOGIN="rlogin";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserId = (EditText) findViewById(R.id.login_et_userid);
        etUserPwd = (EditText) findViewById(R.id.login_et_userpwd);
        tvUserLogin = (Button) findViewById(R.id.login_tv_login);
        cbLogin = (CheckBox) findViewById(R.id.checkBox_login);
        cbPwd = (CheckBox) findViewById(R.id.checkBox_password);
        otherlogin =(TextView) findViewById(R.id.tele_mes_login);
        tvregister = (TextView)findViewById(R.id.tv_register);
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

        if (rpwd.equals("T")) {//记住密码
            cbPwd.setChecked(true);
            etUserId.setText(sp.getString(EID,""));
            etUserPwd.setText(sp.getString(EPWD,""));
        }

        //   Toast.makeText(LoginActivity.this, URL+rlogin+"存储"+rpwd, Toast.LENGTH_SHORT).show();///////////////////////////////////////////////////

        otherlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LoginTeleActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
        tvregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegiActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
        tvUserLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {		//登录监听

                userId = etUserId.getText().toString();//输入的账号密码
                userPwd = etUserPwd.getText().toString();
                mLoader = new CustomerLoader(LoginActivity.this, LoginActivity.this);
                //每次点击新建Loader,以刷新数据
                mLoader.LoadCustomer(userId,userPwd);
                et.putString(EID,etUserId.getText().toString());
                et.putString(EPWD,etUserPwd.getText().toString());
                et.commit();			//每次登陆重新在内存中写入密码
/**

 startActivity(intent2);
 LoginActivity.this.finish();

 userId = etUserId.getText().toString();
 userPwd = etUserPwd.getText().toString();
 mLoader = new UserInfoLoader(LoginActivity.this, LoginActivity.this);

 mLoader.LoadUserInfoByID(userId,userPwd);

 et.putString(EID,etUserId.getText().toString());
 et.putString(EPWD,etUserPwd.getText().toString());
 et.commit();			//每次登陆重新在内存中写入密码
 ****/
            }
        });

        if (rlogin.equals("T")) {//自动登录
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
    }

    @Override
    public Customer getData() {
        return mItem;
    }

    @Override
    public void setData(Customer data) {
        mItem=data;
    }

    @Override
    public void notifyDataSetChanged() {
        //一定要在此方法中进行数据处理
        String pwd = mItem.getPwd();//不为空的情况

        if(userPwd.equals(pwd)) {

            app.saveCustomer(mItem);
            Toast.makeText(LoginActivity.this, mItem.getNickname()+"，你好！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();	//让用户不能通过返回键回到原页面
        }
        else {
            Toast.makeText(LoginActivity.this, "密码错误!", Toast.LENGTH_SHORT).show();
        }


    }
}
