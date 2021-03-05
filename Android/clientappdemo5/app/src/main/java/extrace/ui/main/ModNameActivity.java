package extrace.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import extrace.loader.CustomerLoader;
import extrace.misc.model.Customer;
import extrace.net.IDataAdapter;

public class ModNameActivity extends AppCompatActivity implements IDataAdapter<Customer> {

    private Customer mdata,customer;
    private CustomerLoader customerLoader;
    private ExTraceApplication app;
    private Button btn;
    private ImageView clear;
    private EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_name);
        app = (ExTraceApplication)getApplication();
        init();
        customer = new Customer();
        customer = app.getLoginCustomer();
        customerLoader = new CustomerLoader(this,this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt.getText().toString().equals("")){
                    Toast.makeText(ModNameActivity.this, "昵称不能为空！", Toast.LENGTH_SHORT).show();
                }else if(edt.getText().toString().length()>32){
                    Toast.makeText(ModNameActivity.this, "昵称过长！请重新输入！", Toast.LENGTH_SHORT).show();
                    edt.setText("");
                }else if(!edt.getText().toString().equals(app.getLoginCustomer().getNickname())){//昵称改变
                    customerLoader.ChangeCustomerNick(customer.getID(),edt.getText().toString());
                    customer.setNickname(edt.getText().toString());
                    app.saveCustomer(customer);
                    Intent i = new Intent();
                    i.putExtra("nickname",edt.getText().toString());
                    //Toast.makeText(ModNameActivity.this, "昵称为"+edt.getText().toString(), Toast.LENGTH_SHORT).show();
                    i.setClass(ModNameActivity.this,UserInfoEditActivity.class);
                    startActivity(i);
                    finish();
                }else{//昵称未改变
                    Intent i = new Intent();
                    i.putExtra("nickname","null");
                    i.setClass(ModNameActivity.this,UserInfoEditActivity.class);
                    Toast.makeText(ModNameActivity.this, "昵称没变！！！！", Toast.LENGTH_SHORT).show();
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
                i.putExtra("nickname","null");
                i.setClass(ModNameActivity.this,UserInfoEditActivity.class);
                Toast.makeText(ModNameActivity.this, "昵称没变！！！！", Toast.LENGTH_SHORT).show();
                startActivity(i);
                finish();
            }
        });
        btn = (Button)findViewById(R.id.rcv_btn_save);
        edt = (EditText)findViewById(R.id.mod_nickname);
        edt.setText(app.getLoginCustomer().getNickname());
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
