package extrace.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import extrace.loader.CustomerLoader;
import extrace.misc.model.Customer;
import extrace.net.IDataAdapter;

public class ModPwdActivity extends AppCompatActivity implements IDataAdapter<Customer> {

    private ExTraceApplication app;
    private Button btn;
    private ImageView clear;
    private EditText edt,edtRename,edtPre;
    private Customer mdata,customer;
    private CustomerLoader customerLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_pwd);
        app = (ExTraceApplication)getApplication();

        customer = new Customer();
        customer = app.getLoginCustomer();
        customerLoader = new CustomerLoader(this,this);
        init();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtPre.getText().toString().equals(app.getLoginCustomer().getPwd())){
                    Toast.makeText(ModPwdActivity.this, "原始密码错误！", Toast.LENGTH_SHORT).show();
                }
                else if(edt.getText().equals("")){
                    Toast.makeText(ModPwdActivity.this, "新密码不能为空！", Toast.LENGTH_SHORT).show();
                }else if(edt.getText().toString().length()>8){
                    Toast.makeText(ModPwdActivity.this, "密码过长！请重新输入！", Toast.LENGTH_SHORT).show();
                    edt.setText("");
                }else if(!edt.getText().toString().equals(edtRename.getText().toString())){
                    Toast.makeText(ModPwdActivity.this, "两次密码输入不同！请重新输入！", Toast.LENGTH_SHORT).show();
                }
                else if(!edt.getText().toString().equals(app.getLoginCustomer().getPwd())){//昵称改变
                    customerLoader.ChangeCustomerPwd(app.getLoginCustomer().getID(),edt.getText().toString());
                    customer.setPwd(edt.getText().toString());
                    app.saveCustomer(customer);
                    Intent i = new Intent();
                    //Toast.makeText(ModPwdActivity.this, "密码为"+edt.getText().toString(), Toast.LENGTH_SHORT).show();
                    i.putExtra("password",edt.getText().toString());
                    i.setClass(ModPwdActivity.this,UserInfoEditActivity.class);
                    startActivity(i);
                    finish();

                }else{//密码未改变
                    Intent i = new Intent();
                    i.putExtra("password","null");
                    i.setClass(ModPwdActivity.this,UserInfoEditActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    public void init(){
        findViewById(R.id.arrow_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("password","null");
                i.setClass(ModPwdActivity.this,UserInfoEditActivity.class);
                startActivity(i);
                finish();
            }
        });
        btn = (Button)findViewById(R.id.rcv_btn_save);
        edt = (EditText)findViewById(R.id.mod_pwd);
        clear = (ImageView)findViewById(R.id.img_clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt.setText("");
            }
        });
        edtRename = (EditText)findViewById(R.id.mod_repwd);
        edtPre = (EditText)findViewById(R.id.mod_prepwd);
    }

    @Override
    public Customer getData() {
        return mdata;
    }

    @Override
    public void setData(Customer data) {
        mdata=data;
    }

    @Override
    public void notifyDataSetChanged() {
        //一定要在此方法中进行数据处理
        //String pwd =mdata.getPWD();//不为空的情况


    }
}
