package extrace.ui.misc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

import extrace.misc.model.CustomerInfo;
import extrace.ui.main.R;
import extrace.ui.main.RegionListActivity;

public class CustomerEditActivity extends AppCompatActivity {

	public static final int INTENT_NEW = 1; 
	public static final int INTENT_EDIT = 2; 

	private CustomerInfo mItem;

	private Intent mIntent;

	private EditText mNameView;
	private EditText mTelCodeView;
	private TextView mRegionView;
	private EditText mAddrView;
	private  TextView express_edit_title;

	String mId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar1 = getSupportActionBar();
		if (actionBar1 != null) actionBar1.hide();
		setContentView(R.layout.activity_customer_edit);

		mNameView = (EditText) findViewById(R.id.edtName);//填写人的信息
		mTelCodeView = (EditText) findViewById(R.id.edtTelCode);
		mAddrView = (EditText) findViewById(R.id.edtAddr);
		mRegionView = (TextView) findViewById(R.id.txtRegion);
		express_edit_title=(TextView) findViewById(R.id.express_edit_title);

		findViewById(R.id.btnGetRegion).setOnClickListener(//填写三级地址按钮
				new View.OnClickListener() {
					@SuppressLint("LongLogTag")
					@Override
					public void onClick(View view) {
					//	Log.i("CusterEditActivity的地区GetRegion开始调用","成功！！！！！！！！！！！！！！！");
						GetRegion();
					}
				});

		findViewById(R.id.express_tv_save).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(Save()==true){
					mIntent.putExtra("CustomerInfo",mItem);  //存储保存信息
				//	Log.i("开始返回信息：","开始CustomerLoader！！！！！！！！！！！！"+mItem.getName());
					setResult(RESULT_OK, mIntent);//结果返回调用的Activity
					finish();
				}
			}
		});


		mIntent = getIntent();
		String title=mIntent.getStringExtra("title");
		express_edit_title.setText(title);
		if (mIntent.hasExtra("Action")) {
			if(mIntent.getStringExtra("Action").equals("New")){//首次进行编辑
				mItem = new CustomerInfo();
			}
			else if(mIntent.getStringExtra("Action").equals("Edit")){//再次编辑
				mItem = new CustomerInfo();
				if (mIntent.hasExtra("CustomerInfo")) {
					mItem = (CustomerInfo) mIntent.getSerializableExtra("CustomerInfo");

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
	protected void onStart() {
		super.onStart();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}


	//处理从ReginActivity传来的信息
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
			case RESULT_OK://完成
				String regionId, regionString;
				if (data.hasExtra("RegionId")) {
					regionId = data.getStringExtra("RegionId");
					regionString = data.getStringExtra("RegionString");
				} else {
					regionId = "";
					regionString = "";
				}
				mRegionView.setTag(regionId);//这只tag，将view可以根据tag的不同进行分组操作
				mRegionView.setText(regionString);
				break;
			default:
				break;
		}
	}

	//调用regionActivity，获取地址
	private void GetRegion() {
		Intent intent = new Intent();
		intent.setClass(this, RegionListActivity.class);
		try{
			String rCode = mRegionView.getTag().toString();
			String rString = mRegionView.getText().toString();
			intent.putExtra("RegionId", rCode);//将原本文本框内的信息传给RegionActivity
			intent.putExtra("RegionString", rString);
		}
		catch(Exception e){
			
		}
	//	Log.i("调用RegionListActivity：","成功调用！！！！！！");
		startActivityForResult(intent, 0);//和RegionActivity通信，将其地址string返回
	}


	@SuppressLint("LongLogTag")
	private boolean Save() {

		try {
		//	Log.i("mitem的名字是：：：：：：：：：：：",""+ mItem.getName());
			mItem.setName(mNameView.getText().toString());
		//	Log.i("NAME==================2=======",""+mNameView.getText().toString());
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
	//	mLoader.SaveCustomer(mItem);

	}
}
