package extrace.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import extrace.loader.CustomerLoader;
import extrace.misc.model.Customer;
import extrace.net.IDataAdapter;

public class RegiMesActivity extends AppCompatActivity implements IDataAdapter<Customer> {

    EditText edUname,etNick,etPwd,etRepwd;
    ImageView imgUname,imgNick,imgPwd,imgRepwd;
    TextView tvTele,tvRegister;
    Customer customer;
    CustomerLoader customerLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regi_mes);
        customer = new Customer();

        edUname = (EditText)findViewById(R.id.et_uname);
        imgUname = (ImageView)findViewById(R.id.img_clear_uname);
        imgUname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edUname.setText("");
            }
        });


        etNick = (EditText)findViewById(R.id.et_nickname);
        imgNick = (ImageView)findViewById(R.id.img_clear_nickname);
        imgNick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNick.setText("");
            }
        });

        etPwd = (EditText)findViewById(R.id.et_pwd);
        imgPwd = (ImageView)findViewById(R.id.img_clear_pwd);
        imgPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPwd.setText("");
            }
        });

        etRepwd = (EditText)findViewById(R.id.et_repwd);
        imgRepwd = (ImageView)findViewById(R.id.img_clear_repwd);
        imgRepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etRepwd.setText("");
            }
        });

        tvTele = (TextView)findViewById(R.id.tv_tele);
        Intent i = getIntent();
        tvTele.setText(i.getStringExtra("tele"));

        customerLoader = new CustomerLoader(this,this);
        tvRegister = (TextView)findViewById(R.id.user_tv_logout);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edUname.getText().toString().equals("")||edUname.getText().toString().length()>24){
                    Toast.makeText(RegiMesActivity.this, "请输入正确形式的账户名!", Toast.LENGTH_SHORT).show();
                }else if(etNick.getText().toString().equals("")||etNick.getText().toString().length()>32){
                    Toast.makeText(RegiMesActivity.this, "请输入正确形式的昵称!", Toast.LENGTH_SHORT).show();
                }else if(etPwd.getText().toString().equals("")||etPwd.getText().toString().length()>8){
                    Toast.makeText(RegiMesActivity.this, "请输入正确格式的密码!", Toast.LENGTH_SHORT).show();
                }else if(etRepwd.getText().toString().equals("")){
                    Toast.makeText(RegiMesActivity.this, "请重复输入密码!", Toast.LENGTH_SHORT).show();
                }else if(!etRepwd.getText().toString().equals(etPwd.getText().toString())){
                    Toast.makeText(RegiMesActivity.this, "两次输入密码不一致!", Toast.LENGTH_SHORT).show();
                }else if(tvTele.getText().toString().equals("")||tvTele.getText().toString().length()<11){
                    Toast.makeText(RegiMesActivity.this, "请输入正确的电话号码!", Toast.LENGTH_SHORT).show();
                }else{
                    customer.setUname(edUname.getText().toString());
                    customer.setNickname(etNick.getText().toString());
                    customer.setPwd(etPwd.getText().toString());
                    //Toast.makeText(RegiMesActivity.this, "密码："+customer.getPwd(), Toast.LENGTH_SHORT).show();
                    customer.setTelCode(tvTele.getText().toString());
                    //Toast.makeText(RegiMesActivity.this, "SaveCustomer前面!", Toast.LENGTH_SHORT).show();
                    customerLoader.SaveCustomer(customer);
                    Intent i = new Intent();
                    i.setClass(RegiMesActivity.this,LoginActivity.class);
                    startActivity(i);
                    RegiMesActivity.this.finish();
                    //Toast.makeText(RegiMesActivity.this, "SaveCustomer后面!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public Customer getData() {
        return customer;
    }

    @Override
    public void setData(Customer data) {
        customer=data;
    }

    @Override
    public void notifyDataSetChanged() {
        //一定要在此方法中进行数据处理
        //Toast.makeText(this, "notifyDataSetChanged!", Toast.LENGTH_SHORT).show();


        //Toast.makeText(this, "注册成功!", Toast.LENGTH_SHORT).show();
    }
}
