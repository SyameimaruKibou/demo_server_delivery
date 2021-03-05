package extrace.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import extrace.ui.misc.TimeCountUtil;

public class VerifyActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvGet;
    EditText etCode;
    Button btLogin;
    ImageView imgClearCode;
    private TimeCountUtil mTimeCountUtil;
    private String phone_number;
    private String cord_number;
    EventHandler eventHandler;
    private int time=60;
    private boolean flag=true;
    ExTraceApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        app = (ExTraceApplication)getApplication();
        phone_number = app.getLoginCustomer().getTelCode();
        initview();

        imgClearCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etCode.setText("");
            }
        });

        //tvGetMes这个是点击获取验证码的哦
        mTimeCountUtil = new TimeCountUtil( tvGet, 60000, 1000);//这里的倒计时的时间 是 ：用第二参数 / 第三个三参数 = 倒计时为10秒
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message msg=new Message();//创建了一个对象
                msg.arg1=event;
                msg.arg2=result;
                msg.obj=data;
                handler.sendMessage(msg);
            }
        };

        SMSSDK.registerEventHandler(eventHandler);//注册短信回调（记得销毁，避免泄露内存）*/

    }
    private void initview() {
        etCode = (EditText)findViewById(R.id.mod_nickname);
        findViewById(R.id.arrow_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("tele","null");
                i.setClass(VerifyActivity.this,UserInfoEditActivity.class);
                startActivity(i);
                finish();
            }
        });

        imgClearCode = (ImageView)findViewById(R.id.img_clear);
        tvGet = (TextView)findViewById(R.id.tv_get_mesg);//获取验证码
        btLogin = (Button)findViewById(R.id.login_tv_login);//确定

        tvGet .setOnClickListener(this);//验证码的触发事件
        btLogin.setOnClickListener(this);//确定

    }
    protected void onDestroy() {//销毁
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    /**
     * 使用Handler来分发Message对象到主线程中，处理事件
     */
    @SuppressLint("HandlerLeak")
    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event=msg.arg1;
            int result=msg.arg2;
            Object data=msg.obj;
            /*
            if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {//获取验证码成功
                if(result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    boolean smart = (Boolean)data;
                    if(smart) {
                        Toast.makeText(getApplicationContext(),"该手机号已经注册过，请重新输入",
                                Toast.LENGTH_LONG).show();
                        etTele.requestFocus();//焦点
                        return;
                    }
                }
            }
            //回调完成
            if (result==SMSSDK.RESULT_COMPLETE){
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功

                    Intent intent=new Intent(LoginTeleActivity.this,Main2Activity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "验证码输入正确",
                            Toast.LENGTH_LONG).show();

                }
            }else {//其他出错情况

             */
            if(flag)
            {
                tvGet.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"验证码获取失败请重新获取", Toast.LENGTH_LONG).show();
                //etTele.requestFocus();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"验证码输入错误", Toast.LENGTH_LONG).show();
            }

            //  }
        }

    };

    //按钮点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case  R.id.tv_get_mesg://获取验证码的ID

                mTimeCountUtil.start();
                SMSSDK.getVerificationCode("86",phone_number);//获取你的手机号的验证码
                etCode.requestFocus();//判断是否获得焦点
                break;
            //  获取后要提交你的验证码以判断是否正确，并登陆成功

            case R.id.login_tv_login://登陆页面的ID

                if(judCord())//判断验证码
                    SMSSDK.submitVerificationCode("86",phone_number,cord_number);//提交手机号和验证码

                startActivity(new Intent(this,ModTelActivity.class));
                flag=false;
                break;

        }

    }

    private boolean judCord() {//判断验证码是否正确
        if(TextUtils.isEmpty(etCode.getText().toString().trim()))//验证码
        {
            Toast.makeText(VerifyActivity.this,"请输入您的验证码",Toast.LENGTH_LONG).show();
            etCode.requestFocus();//聚集焦点
            return false;
        }
        else if(etCode.getText().toString().trim().length()!=4)
        {
            Toast.makeText(VerifyActivity.this,"您的验证码位数不正确",Toast.LENGTH_LONG).show();
            etCode.requestFocus();

            return false;
        }
        else
        {
            cord_number=etCode.getText().toString().trim();
            return true;
        }
    }
}
