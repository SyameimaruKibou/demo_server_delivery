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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import extrace.ui.misc.TimeCountUtil;


public class RegiActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvLogin;
    TextView tvGetMes;
    Button btnCon;
    EditText etTele;
    EditText etCode;
    ImageView imgClearTele;
    ImageView imgClearCode;


    private TimeCountUtil mTimeCountUtil;
    private String phone_number;
    private String cord_number;
    EventHandler eventHandler;
    private int time=60;
    private boolean flag=true;
    //*******************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regi);
        //初始化操作
        initview();

        imgClearTele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTele.setText("");
            }
        });
        imgClearCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etCode.setText("");
            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegiActivity.this, LoginActivity.class);

                startActivity(intent);
                RegiActivity.this.finish();
            }
        });

        //tvGetMes这个是点击获取验证码的哦
        mTimeCountUtil = new TimeCountUtil( tvGetMes, 60000, 1000);//这里的倒计时的时间 是 ：用第二参数 / 第三个三参数 = 倒计时为10秒

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
        tvLogin = (TextView)findViewById(R.id.tv_login);//登录
        tvGetMes = (TextView)findViewById(R.id.tv_get_mesg);//获取验证码
        btnCon = (Button)findViewById(R.id.login_tv_login);//确定
        etTele = (EditText)findViewById(R.id.tele_code);//手机号
        etCode = (EditText)findViewById(R.id.mod_nickname);
        imgClearTele = (ImageView)findViewById(R.id.img_clear_tele);
        imgClearCode = (ImageView)findViewById(R.id.img_clear);

        tvGetMes.setOnClickListener(this);//验证码的触发事件
        btnCon.setOnClickListener(this);//确定

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
            } */
            //回调完成
            if (result==SMSSDK.RESULT_COMPLETE){
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功

                    Intent intent=new Intent(RegiActivity.this,RegiMesActivity.class);
                    intent.putExtra("tele",phone_number);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "验证码输入正确",
                            Toast.LENGTH_LONG).show();

                }
            }else {//其他出错情况


                if(flag)
                {
                    tvGetMes.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"获取请稍候...", Toast.LENGTH_LONG).show();
                    etTele.requestFocus();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"验证码输入错误", Toast.LENGTH_LONG).show();
                }

            }
        }

    };

    //按钮点击事件
    @Override
    public void onClick(View v) {
        /*String phone_number=edit_phone.getText().toString();//1
        String cord_number=bt_getcord.getText().toString().trim();//1 */
        switch (v.getId()){

            case  R.id.tv_get_mesg://获取验证码的ID
                if(judPhone())//去掉左右空格获取字符串，是正确的手机号
                {
                    mTimeCountUtil.start();
                    SMSSDK.getVerificationCode("86",phone_number);//获取你的手机号的验证码
                    etCode.requestFocus();//判断是否获得焦点
                }
                break;
            //  获取后要提交你的验证码以判断是否正确，并登陆成功

            case R.id.login_tv_login://登陆页面的ID

                if(judCord())//判断验证码
                    SMSSDK.submitVerificationCode("86",phone_number,cord_number);//提交手机号和验证码

                //startActivity(new Intent(this,RegiActivity.class));
                //startActivity(new Intent(this,MainActivity.class));
                flag=false;
                break;
        }

    }
    private boolean judPhone() {//判断手机号是否正确
        //不正确的情况
        if(TextUtils.isEmpty(etTele.getText().toString().trim()))//对于字符串处理Android为我们提供了一个简单实用的TextUtils类，如果处理比较简单的内容不用去思考正则表达式不妨试试这个在android.text.TextUtils的类，主要的功能如下:
        //是否为空字符 boolean android.text.TextUtils.isEmpty(CharSequence str)
        {
            Toast.makeText(RegiActivity.this,"请输入您的电话号码",Toast.LENGTH_LONG).show();
            etTele.requestFocus();//设置是否获得焦点。若有requestFocus()被调用时，后者优先处理。注意在表单中想设置某一个如EditText获取焦点，光设置这个是不行的，需要将这个EditText前面的focusable都设置为false才行。
            return false;
        }
        else if(etTele.getText().toString().trim().length()!=11)
        {
            Toast.makeText(RegiActivity.this,"您的电话号码位数不正确",Toast.LENGTH_LONG).show();
            etTele.requestFocus();
            return false;
        }
        //正确的情况
        else
        {
            phone_number=etTele.getText().toString().trim();
            String num="[1][358]\\d{9}";
            if(phone_number.matches(num))
                return true;
            else
            {
                Toast.makeText(RegiActivity.this,"请输入正确的手机号码",Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }

    private boolean judCord() {//判断验证码是否正确
        judPhone();//先执行验证手机号码正确与否
        if(TextUtils.isEmpty(etCode.getText().toString().trim()))//验证码
        {
            Toast.makeText(RegiActivity.this,"请输入您的验证码",Toast.LENGTH_LONG).show();
            etCode.requestFocus();//聚集焦点
            return false;
        }
        else if(etCode.getText().toString().trim().length()!=6)
        {
            Toast.makeText(RegiActivity.this,"您的验证码位数不正确",Toast.LENGTH_LONG).show();
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