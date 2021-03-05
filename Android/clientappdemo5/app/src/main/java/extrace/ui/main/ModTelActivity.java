package extrace.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

public class ModTelActivity extends AppCompatActivity implements IDataAdapter<Customer> {

    private ExTraceApplication app;
    private Button btn;
    private ImageView clear;
    private EditText edt;
    private Customer mdata,customer;
    private CustomerLoader customerLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_tel);
        app = (ExTraceApplication)getApplication();
        customer = new Customer();
        customer = app.getLoginCustomer();
        customerLoader = new CustomerLoader(this,this);
        init();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt.getText().equals("")){
                   Toast.makeText(ModTelActivity.this, "电话号码不能为空！", Toast.LENGTH_SHORT).show();
                }else if(edt.getText().toString().length()>11){
                    Toast.makeText(ModTelActivity.this, "电话号码长度位11位！请重新输入！", Toast.LENGTH_SHORT).show();
                    //edt.setText("");
                }else if(!edt.getText().equals(app.getLoginCustomer().getTelCode())){//电话号码改变
                    customerLoader.ChangeCustomerTel(app.getLoginCustomer().getID(),edt.getText().toString());
                    customer.setTelCode(edt.getText().toString());
                    app.saveCustomer(customer);
                    Intent i = new Intent();
                    i.putExtra("tele",edt.getText().toString());
                    Toast.makeText(ModTelActivity.this, "电话号码为"+edt.getText().toString(), Toast.LENGTH_SHORT).show();
                    i.setClass(ModTelActivity.this,UserInfoEditActivity.class);
                    startActivity(i);
                }else{//电话号码未改变
                    Intent i = new Intent();
                    i.setClass(ModTelActivity.this,UserInfoEditActivity.class);
                    i.putExtra("tele","null");
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
                i.setClass(ModTelActivity.this,UserInfoEditActivity.class);
                i.putExtra("tele","null");
                startActivity(i);
                finish();
            }
        });
        btn = (Button)findViewById(R.id.rcv_btn_save);
        edt = (EditText)findViewById(R.id.mod_nickname);
        edt.setText(app.getLoginCustomer().getTelCode());
        clear = (ImageView)findViewById(R.id.img_clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt.setText("");
            }
        });
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
