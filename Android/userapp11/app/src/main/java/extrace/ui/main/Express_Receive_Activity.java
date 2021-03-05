package extrace.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import extrace.loader.ExpressLoader;
import extrace.misc.model.CustomerInfo;
import extrace.misc.model.ExpressSheet;
import extrace.net.IDataAdapter;
import extrace.ui.misc.CustomerEditActivity;

import static java.lang.Float.parseFloat;

public class Express_Receive_Activity extends AppCompatActivity implements IDataAdapter<ExpressSheet> {

    public static final int NEW_DELIEVER = 1;
    public static final int NEW_RECEIVER = 2;

    TextView expressSndName;
    TextView expressSndAddr;
    TextView expressRcvName;
    TextView expressRcvAddr;
    TextView box_fee;
    EditText weight;
    Button network_bn_ok;

    int receiver_bt_num=0;
    int deliever_bt_num=0;
    CustomerInfo deliever;
    CustomerInfo receiver;

    boolean deliever_ok=false;
    boolean receiver_ok=false;

    ExpressSheet es;
    String expressID=null;
    ExTraceApplication app;
    String useid;
    String nodeid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar1 = getSupportActionBar();
        if (actionBar1 != null) actionBar1.hide();
        setContentView(R.layout.activity_express_receive);
        //    ButterKnife.bind(this);
        //   Log.i("onStatusNotify", "YESYES!!!!!!!!!!!!!!!!!!!!!!!!!!!: " );

        expressSndName=findViewById(R.id.expressSndName);
        expressSndAddr=findViewById(R.id.expressSndAddr);
        expressRcvName=findViewById(R.id.expressRcvName);
        expressRcvAddr=findViewById(R.id.expressRcvAddr);
        box_fee=findViewById(R.id.box_fee);
        weight=findViewById(R.id.weight);

        es=new ExpressSheet();//一定要在这里进行初始化，否则很容易loader传不过来值
        app=(ExTraceApplication) getApplication();
        app.getLoginUser();
        useid=String.valueOf(app.getLoginUser().getUID());
        nodeid=app.getNode().getID();




        findViewById(R.id.action_ex_snd_icon).setOnClickListener(new View.OnClickListener() {//发件人信息
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {
                //    Log.i("IIIIIIIIIIIIIIIIIIIIIIIII","okokokok！！！！！！！！！！！！");
                deliever_bt_num++;
                Intent intent = new Intent();
                intent.setClass(Express_Receive_Activity.this, CustomerEditActivity.class);
                if (deliever_bt_num == 1) {
                    intent.putExtra("Action", "New");
                } else {
                    intent.putExtra("Action", "Edit");
                    intent.putExtra("CustomerInfo", deliever);
                }
                intent.putExtra("title","寄件人信息");
                startActivityForResult(intent, Express_Receive_Activity.NEW_DELIEVER);    //激发下层编辑-建立新的
            }
        });

        findViewById(R.id.action_ex_rcv_icon).setOnClickListener(new View.OnClickListener() {//收件人信息
            @Override
            public void onClick(View view) {//编辑收件人信息
                receiver_bt_num++;
                Intent intent = new Intent();
                intent.setClass(Express_Receive_Activity.this, CustomerEditActivity.class);
                if (receiver_bt_num == 1) {
                    intent.putExtra("Action", "New");
                } else {
                    intent.putExtra("Action", "Edit");
                    intent.putExtra("CustomerInfo", receiver);
                }
                intent.putExtra("title","收件人信息");
                startActivityForResult(intent, Express_Receive_Activity.NEW_RECEIVER);    //激发下层编辑-建立新的
            }
        });
        network_bn_ok=findViewById(R.id.network_bn_ok);
        findViewById(R.id.network_bn_ok).setOnClickListener(new View.OnClickListener() {//下单ok
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

                es.setTransFee(parseFloat(box_fee.getText().toString()));
                es.setCreateTime(new Date());
                //   Toast.makeText(Express_Receive_Activity.this, "xxx薛茜茜成功！！！"+es.getRecievername()+"___"+es.getSendername(), Toast.LENGTH_SHORT).show();


                ExpressLoader mloader=new ExpressLoader(Express_Receive_Activity.this,Express_Receive_Activity.this) ;
                mloader.Save(nodeid,useid,es);
                //  这里一定要在notifyDataSetChanged()里面处理信息！！！！！！！！！！！！！！！！！！！！！！！！！！1切记切记！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！

            }
        });

        //重量改变时候的监听！！！！！！！！！！！！！！
        weight.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(deliever_ok&&receiver_ok){
                    checkOver();
                    network_bn_ok.setBackgroundColor(R.color.colorTop);
                }else{
                    network_bn_ok.setBackgroundColor(R.color.colorTipBackground);
                }

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void afterTextChanged(Editable editable) {
                if(deliever_ok&&receiver_ok){
                    checkOver();
                    network_bn_ok.setBackgroundColor(R.color.colorTop);
                }else{
                    network_bn_ok.setBackgroundColor(R.color.colorTipBackground);
                }
            }
        });


        findViewById(R.id.arrow_left).setOnClickListener(//返回键
                new View.OnClickListener() {
                    public void onClick(View view) {
                        finish();
                    }
                });


    }


    @SuppressLint({"LongLogTag", "SetTextI18n"})
    public void onActivityResult(int requestCode, int resultCode, Intent data) {    //编辑界面的回调
        super.onActivityResult(requestCode, resultCode, data);
        //   Toast.makeText(this, "xxx薛茜茜成功！！！", Toast.LENGTH_SHORT).show();

        switch (requestCode) {
            case NEW_DELIEVER:
                if (data.hasExtra("CustomerInfo")) {
                    deliever = (CustomerInfo) data.getSerializableExtra("CustomerInfo");
                    //     Log.i("!!!!!!!!!!!!!!!!!!!!!!!!!!result!!!!!!!!!!!",""+deliever.getName());
                    expressSndName.setText(deliever.getName() + " " + deliever.getTelCode());
                    expressSndAddr.setText(deliever.getRegionString() +" "+deliever.getAddress());
                    //    Toast.makeText(this, deliever.getName()+"f发件人成功！！！", Toast.LENGTH_SHORT).show();
                    deliever_ok=true;
                }
                break;
            case NEW_RECEIVER:
                if (data.hasExtra("CustomerInfo")) {
                    receiver = (CustomerInfo) data.getSerializableExtra("CustomerInfo");
                    expressRcvName.setText(receiver.getName() + " " + receiver.getTelCode());
                    expressRcvAddr.setText(receiver.getRegionString() + " "+receiver.getAddress());
                    //    Toast.makeText(this, receiver.getName()+"收件人成功！！！", Toast.LENGTH_SHORT).show();
                    receiver_ok=true;
                }
                break;
            case RESULT_CANCELED://不正常的返回，按钮点击次数-1;
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
        //    box_fee.setText("哈哈哈哈哈哈哈哈哈");
    }


    @Override
    public ExpressSheet getData() {
        return es;
    }

    @Override
    public void setData(ExpressSheet data) {
        es=data;
    }

    @Override
    public void notifyDataSetChanged() {
        expressID=es.getID();

        Intent intent = new Intent();
        intent.setClass(Express_Receive_Activity.this, express_born_ok_Activity.class);
        intent.putExtra("ExpressSheet", es);
        intent.putExtra("ID", expressID);
        startActivity(intent);    //激发下层编辑-建立新的
        finish();
        //    Toast.makeText(this, "我现在用到了notifyDataSetChanged()方法!"+expressID, Toast.LENGTH_SHORT).show();
        //    Log.i("哈哈哈哈哈哈哈哈哈哈哈哈或","和哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈"+es.getID());
        //   Toast.makeText(this, "我现在用到了notifyDataSetChanged()方法!"+es.getID(), Toast.LENGTH_SHORT).show();
    }
}
