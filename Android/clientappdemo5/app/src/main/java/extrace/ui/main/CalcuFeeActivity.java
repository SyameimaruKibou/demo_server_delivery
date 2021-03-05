package extrace.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import extrace.ui.misc.RegionListActivity;

import static java.lang.Float.parseFloat;

public class CalcuFeeActivity extends AppCompatActivity {

    private LinearLayout lin1,lin2;
    private EditText edt;
    private TextView feetotal,feefirst,from,to;
    private String fromcode="123",tocode="123";
    private String regioncode,regionstring;
    private int fee=0;
    boolean recv_ok=false;
    boolean send_ok=false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcu_fee);
        lin1=(LinearLayout)findViewById(R.id.lin_send);
        lin2=(LinearLayout)findViewById(R.id.lin_get);//lin_get
        edt=(EditText)findViewById(R.id.weight);
        feetotal=(TextView)findViewById(R.id.fee);
        feefirst=(TextView)findViewById(R.id.first_fee);
        from=(TextView)findViewById(R.id.from);
        to=(TextView)findViewById(R.id.to);
        findViewById(R.id.arrow_left).setOnClickListener(//返回键
                new View.OnClickListener() {
                    public void onClick(View view) {
                        finish();
                    }
                });
        lin1.setOnClickListener(new View.OnClickListener() {//选择城市
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalcuFeeActivity.this, RegionListActivity.class);
                startActivityForResult(intent,0);
            }
        });

        lin2.setOnClickListener(new View.OnClickListener() {//选择城市
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalcuFeeActivity.this, RegionListActivity.class);
                startActivityForResult(intent,1);
            }
        });
        edt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(recv_ok&&send_ok){
                    checkOver();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(recv_ok&&send_ok){
                    checkOver();
                }
            }
        });

    }

    private void checkOver() {
        if(recv_ok==false||send_ok==false){
            Toast.makeText(this, "收件人或者寄件人未填写!", Toast.LENGTH_SHORT).show();
            return ;
        }
        String weight_cal=edt.getText().toString();
        if(weight_cal.equals("")){
            Toast.makeText(this, "重量未填写!", Toast.LENGTH_SHORT).show();
            return ;
        }

        float base=0;
        float sum=0;
        float nex=0;
        float w=parseFloat(weight_cal);
        String code1=fromcode;
        String code2=tocode;

        if(code1.substring(0,2).equals(code2.substring(0,2))==false){//邮寄到省外
            base= (float) 20.0;
            nex= (float) 3.0;
            feefirst.setText("首重20元/公斤");
        }else if(code1.substring(0,4).equals(code2.substring(0,4))==false){//同省不同市邮寄
            base= (float) 12.0;
            nex= (float) 3.0;
            feefirst.setText("首重12元/公斤");
        }else{//同省同市邮寄
            base= (float) 10.0;
            nex= (float) 3.0;
            feefirst.setText("首重10元/公斤");
        }
        sum= (float) (nex*Math.ceil(w/0.5)-nex+base);
        feetotal.setText(Float.toString(sum));
    }


    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode)

        {

            case (0) :

            {
                if(resultCode==RESULT_CANCELED)
                    return;
                    regioncode = data.getExtras().getString("RegionId");//得到新Activity 关闭后返回的数据

                    regionstring = data.getExtras().getString("RegionString");

                    from.setText(regionstring);
                    fromcode=regioncode;
                    send_ok=true;

                    break;

            }

            case (1) :
            {
                if(resultCode==RESULT_CANCELED)
                    return;
                    regioncode = data.getExtras().getString("RegionId");//得到新Activity 关闭后返回的数据

                    regionstring = data.getExtras().getString("RegionString");

                    to.setText(regionstring);
                    tocode=regioncode;
                    recv_ok=true;

                break;

            }
            default:break;
        }

    }



}
