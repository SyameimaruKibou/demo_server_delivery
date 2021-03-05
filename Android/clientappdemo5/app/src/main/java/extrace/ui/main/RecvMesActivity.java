package extrace.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Pattern;
import extrace.loader.CustomerLoader;
import extrace.misc.model.TempCustomer;
import extrace.ui.misc.RegionListActivity;

public class RecvMesActivity extends AppCompatActivity {

    public static final int INTENT_NEW = 1;
    public static final int INTENT_EDIT = 2;

    private TempCustomer mItem;
    private CustomerLoader mLoader;

    private Intent mIntent;

    private EditText mNameView;
    private EditText mTelCodeView;
    private TextView mRegionView;
    private EditText mAddrView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recv_mes);

        mNameView = (EditText) findViewById(R.id.edit_send_name);
        mTelCodeView = (EditText) findViewById(R.id.edit_send_tele);
        mRegionView = (TextView) findViewById(R.id.send_choose_region_txt);
        mAddrView = (EditText) findViewById(R.id.send_choose_add_edt);

        findViewById(R.id.arrow_left).setOnClickListener(//返回键
                new View.OnClickListener() {
                    public void onClick(View view) {
                        finish();
                    }
                });
       mRegionView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GetRegion();
                    }
                });

        findViewById(R.id.rcv_btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Save()==true){
                    mIntent.putExtra("CustomerInfo",mItem);  //存储保存信息
                    Log.i("开始返回信息：","开始CustomerLoader！！！！！！！！！！！！"+mItem.getName());
                    setResult(RESULT_OK, mIntent);//结果返回调用的Activity
                    finish();
                }
            }
        });
        mIntent = getIntent();

        if (mIntent.hasExtra("Action")) {
            if(mIntent.getStringExtra("Action").equals("New")){//首次进行编辑
                mItem = new TempCustomer();
            }
            else if(mIntent.getStringExtra("Action").equals("Edit")){//再次编辑
                mItem = new TempCustomer();
                if (mIntent.hasExtra("CustomerInfo")) {
                    mItem = (TempCustomer) mIntent.getSerializableExtra("CustomerInfo");

                    mNameView.setText(mItem.getName());
                    //	Log.i("NAME==================",""+mNameView.getText().toString());
                    mTelCodeView.setText(mItem.getTelCode());
                    mAddrView.setText(mItem.getAddress());
                    mRegionView.setTag(mItem.getRegionCode());
                    mRegionView.setText(mItem.getRegionString()); // ================
                } else {
                    this.setResult(RESULT_CANCELED, mIntent);//出错的处理方法
                    this.finish();
                }
            }
            else{
                this.setResult(RESULT_CANCELED, mIntent);//出错的处理方法
                this.finish();
            }
        }
        else{
            this.setResult(RESULT_CANCELED, mIntent);
            this.finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                if(resultCode==RESULT_CANCELED)
                    return;
                String regionId, regionString;
                if (data.hasExtra("RegionId")) {
                    regionId = data.getStringExtra("RegionId");
                    regionString = data.getStringExtra("RegionString");
                } else {
                    regionId = "";
                    regionString = "";
                }
                mRegionView.setTag(regionId);
                mRegionView.setText(regionString);
                break;
            default:
                break;
        }
    }

    private void GetRegion() {
        Intent intent = new Intent();
        intent.setClass(this, RegionListActivity.class);
        try{
            String rCode = mRegionView.getTag().toString();
            String rString = mRegionView.getText().toString();
            intent.putExtra("RegionId", rCode);
            intent.putExtra("RegionString", rString);
        }
        catch(Exception e){

        }
        startActivityForResult(intent, 0);
    }


    private boolean Save() {
        //mLoader = new CustomerEditLoader(this, this);
//		mItem = new CustomerInfo();
//		try {
//			mItem.setID(mTelCodeView.getText().toString());
//		} catch (Exception e) {
//			Toast.makeText(this, "客户电话号码不能为空!", Toast.LENGTH_SHORT).show();
//		}
        try {
            mItem.setName(mNameView.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, "客户姓名不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(mItem.getName().length() < 2)
        {
            Toast.makeText(this, "客户姓名不能太短!", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            mItem.setTelCode(mTelCodeView.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, "客户电话号码不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(mItem.getTelCode().length() < 6)
        {
            Toast.makeText(this, "客户电话号码不能太短!", Toast.LENGTH_SHORT).show();
            return false;
        }

        String regex_mb = "(\\+\\d+)?1[34578]\\d{9}$";  //移动电话的正则表达式
        String regex_ph = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";  //固定电话的正则表达式
        Pattern pattern_mb = Pattern.compile(regex_mb);
        Pattern pattern_ph = Pattern.compile(regex_ph);
        if(!(pattern_mb.matcher(mItem.getTelCode()).matches() || pattern_ph.matcher(mItem.getTelCode()).matches()))
        {
            Toast.makeText(this, "客户电话号码格式错误!", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            mItem.setAddress(mAddrView.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, "客户地址不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            mItem.setRegionCode(mRegionView.getTag().toString());
            mItem.setRegionString(mRegionView.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, "客户地址行政区不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
       // mLoader.SaveCustomer(mItem);
    }
}
