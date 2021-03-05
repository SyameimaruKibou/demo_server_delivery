package extrace.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import extrace.loader.TemporaryExpressLoader;
import extrace.misc.model.TempCustomer;
import extrace.misc.model.TemporaryExpress;
import extrace.net.IDataAdapter;
import extrace.ui.domain.AddressActivity;

import static java.lang.Float.parseFloat;

public class SendActivity extends AppCompatActivity   implements IDataAdapter<String> {

    public static final int NEW_DELIEVER = 1;
    public static final int NEW_RECEIVER = 2;

    TextView expressSndName;
    TextView expressSndAddr;
    TextView expressRcvName;
    TextView expressRcvAddr;
    TextView box_fee,express_xieyi;
    EditText weight;
    LinearLayout lin1,lin2;
    ImageView imgsend,imgrcv;

    int receiver_bt_num=0;
    int deliever_bt_num=0;
    TempCustomer deliever;
    TempCustomer receiver;

    boolean deliever_ok=false;
    boolean receiver_ok=false;

    String result;
    TemporaryExpress es;
    String expressID=null;
    TemporaryExpressLoader mloader;
    // @BindViews({R.id.btn_normal, R.id.btn_food, R.id.btn_text, R.id.btn_log, R.id.btn_clothe, R.id.btn_other})
    //List<CheckBox> radios; // 单选组
   // private CheckBox btn_nor,btn_food,btn_text,btn_log,btn_cloth,btn_other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
       // ButterKnife.bind(getActivity());


        expressSndName=findViewById(R.id.expressSndName);
        expressSndAddr=findViewById(R.id.expressSndAddr);
        expressRcvName=findViewById(R.id.expressRcvName);
        expressRcvAddr=findViewById(R.id.expressRcvAddr);
        weight=findViewById(R.id.weight);
        lin1=findViewById(R.id.lin_send);
        lin2=findViewById(R.id.lin_get);
        imgsend = findViewById(R.id.add_image1);
        imgrcv = findViewById(R.id.add_image2);
        findViewById(R.id.arrow_left).setOnClickListener(//返回键
                new View.OnClickListener() {
                    public void onClick(View view) {
                        finish();
                    }
                });
        express_xieyi=findViewById(R.id.express_xieyi);
        express_xieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aler_xieyi();
            }
        });
        es=new TemporaryExpress();//一定要在这里进行初始化，否则很容易loader传不过来值

        imgsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliever_bt_num++;
                Intent intent = new Intent();
                intent.setClass(SendActivity.this, AddressActivity.class);
                intent.putExtra("addr","addAddr");
                startActivityForResult(intent, SendActivity.NEW_DELIEVER);
            }
        });

        imgrcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiver_bt_num++;
                Intent intent = new Intent();
                intent.setClass(SendActivity.this, AddressActivity.class);
                intent.putExtra("addr","addAddr");
                startActivityForResult(intent, SendActivity.NEW_RECEIVER);
            }
        });


        lin1.setOnClickListener(new View.OnClickListener() {//发件人信息
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {
                deliever_bt_num++;
                Intent intent = new Intent();
                intent.setClass(SendActivity.this, SendMesActivity.class);
                if (deliever_bt_num == 1) {
                    intent.putExtra("Action", "New");
                } else {
                    intent.putExtra("Action", "Edit");
                    intent.putExtra("CustomerInfo", deliever);
                }
                startActivityForResult(intent, SendActivity.NEW_DELIEVER);    //激发下层编辑-建立新的
            }
        });


        lin2.setOnClickListener(new View.OnClickListener() {//收件人信息
            @Override
            public void onClick(View view) {//编辑收件人信息
                receiver_bt_num++;
                Intent intent = new Intent();
                intent.setClass(SendActivity.this, RecvMesActivity.class);
                if (receiver_bt_num == 1) {
                    intent.putExtra("Action", "New");
                } else {
                    intent.putExtra("Action", "Edit");
                    intent.putExtra("CustomerInfo", receiver);
                }
                startActivityForResult(intent, SendActivity.NEW_RECEIVER);    //激发下层编辑-建立新的
            }
        });

        findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {//下单ok
            @Override
            public void onClick(View view) {//下单的监听！！！！！！

                es.setReceivername(receiver.getName());
                es.setReceivertel(receiver.getTelCode());
                es.setReceiveraddr(receiver.getAddress());
                es.setReceiverregcode(receiver.getRegionCode());

                es.setSendername(deliever.getName());
                es.setSendertel(deliever.getTelCode());
                es.setSenderaddr(deliever.getAddress());
                es.setSenderregcode(deliever.getRegionCode());
                //Toast.makeText(SendActivity.this,"寄件人姓名"+receiver.getName(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(SendActivity.this,"收件人姓名", Toast.LENGTH_SHORT).show();
                mloader=new TemporaryExpressLoader(SendActivity.this,SendActivity.this) ;
                mloader.New(es);
                //Toast.makeText(SendActivity.this, "下单按钮", Toast.LENGTH_SHORT).show();
                //finish();
            }
        });

        //得到和失去焦点的监听
        /*
        weight.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(deliever_ok&&receiver_ok){
                    checkOver();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(deliever_ok&&receiver_ok){
                    checkOver();
                }
            }
        });*/
    }

    void aler_xieyi(){
        LayoutInflater layoutInflater = LayoutInflater.from(SendActivity.this);//适配器
        @SuppressLint("InflateParams") View v2 = layoutInflater.inflate(R.layout.express_xieyi, null, false);//引用自定义布局
        AlertDialog.Builder builder2 = new AlertDialog.Builder(SendActivity.this);//创建弹窗
        builder2.setIcon(R.drawable.launcher_icon);
        builder2.setView(v2).show();//设置自定义布局并show出来
    }

    @SuppressLint("LongLogTag")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {    //编辑界面的回调
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case NEW_DELIEVER:
                if(resultCode==RESULT_CANCELED)
                    return;
                if (data.hasExtra("CustomerInfo")) {
                    deliever_bt_num++;
                    deliever = (TempCustomer) data.getSerializableExtra("CustomerInfo");
                    //     Log.i("!!!!!!!!!!!!!!!!!!!!!!!!!!result!!!!!!!!!!!",""+deliever.getName());
                    expressSndName.setText(deliever.getName() + " " + deliever.getTelCode());
                    expressSndAddr.setText(deliever.getRegionString() +" "+deliever.getAddress());
                    //    Toast.makeText(this, deliever.getName()+"f发件人成功！！！", Toast.LENGTH_SHORT).show();
                    deliever_ok=true;
                }
                break;
            case NEW_RECEIVER:
                if(resultCode==RESULT_CANCELED)
                    return;
                if (data.hasExtra("CustomerInfo")) {
                    receiver_bt_num++;
                    receiver = (TempCustomer) data.getSerializableExtra("CustomerInfo");
                    expressRcvName.setText(receiver.getName() + " " + receiver.getTelCode());
                    expressRcvAddr.setText(receiver.getRegionString() + receiver.getAddress());
                    //    Toast.makeText(this, receiver.getName()+"收件人成功！！！", Toast.LENGTH_SHORT).show();
                    receiver_ok=true;
                }
                break;
            case RESULT_CANCELED://不正常的返回，按钮点击次数-1;
                if(resultCode==RESULT_CANCELED)
                    return;
                if (requestCode == NEW_DELIEVER) {
                    deliever_bt_num--;
                }
                if (requestCode == NEW_RECEIVER) {
                    receiver_bt_num--;
                }
                break;
            default:
                break;
        }
    }

    private void checkOver(){
        if(deliever_ok==false||receiver_ok==false){
            Toast.makeText(this, "收件人或者寄件人未填写!", Toast.LENGTH_SHORT).show();
            return ;
        }
        String weight_cal=weight.getText().toString();
        if(weight_cal.equals("")){
            Toast.makeText(this, "重量未填写!", Toast.LENGTH_SHORT).show();
            return ;
        }

        float base=0;
        float sum=0;
        float nex=0;
        float w=parseFloat(weight_cal);
        String code1=deliever.getRegionCode();
        String code2=receiver.getRegionCode();
        if(code1.substring(0,2).equals(code2.substring(0,2))==false){//邮寄到省外
            base= (float) 20.0;
            nex= (float) 3.0;
        }else if(code1.substring(0,4).equals(code2.substring(0,4))==false){//同省不同市邮寄
            base= (float) 12.0;
            nex= (float) 3.0;
        }else{//同省同市邮寄
            base= (float) 10.0;
            nex= (float) 3.0;
        }
        sum= (float) (nex*Math.ceil(w/0.5)-nex+base);
        box_fee.setText(Float.toString(sum));

    }


    @Override
    public String getData() {
        return result;
    }

    @Override
    public void setData(String data) {
        result=data;
    }

    @Override
    public void notifyDataSetChanged() {
        Toast.makeText(SendActivity.this, "我现在用到了notifyDataSetChanged()方法!", Toast.LENGTH_SHORT).show();
        if(result.equals("OK")){
            Toast.makeText(SendActivity.this, "下单成功！", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
