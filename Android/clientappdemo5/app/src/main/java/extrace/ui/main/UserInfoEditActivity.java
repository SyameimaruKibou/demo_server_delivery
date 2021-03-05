package extrace.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import extrace.misc.model.Customer;
import extrace.net.IDataAdapter;

public class UserInfoEditActivity extends AppCompatActivity  {

	private ExTraceApplication app;
	private Customer mItem,mdata;
	private boolean flag ;
	TextView tvID;
	TextView etName;
	TextView etTelPhone;
    ImageView tvBack;
    TextView tvLogout;
    TextView tvUname;
    RelativeLayout rlname,rlpasword,rltelcode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo_edit);
		app = (ExTraceApplication) getApplication();
		mdata = new Customer();//上传更新的个人信息
		mItem = app.getLoginCustomer();
		flag = false;
		findIdAndSetText();
		//Toast.makeText(this, String.valueOf(flag), Toast.LENGTH_SHORT).show();
	}

	private void findIdAndSetText() {
		tvBack = (ImageView) findViewById(R.id.arrow_left);
		tvBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(UserInfoEditActivity.this, MainActivity.class);
				intent.putExtra("flag", 2);
				startActivity(intent);
				UserInfoEditActivity.this.finish();
			}
		});
		tvID = (TextView) findViewById(R.id.user_edit_tv_id);
		tvID.setText(String.valueOf(mItem.getID()));

		etName = (TextView) findViewById(R.id.user_edit_tv_name);
		etName.setText(mItem.getNickname());
		tvUname = (TextView)findViewById(R.id.user_edit_tv_uname);
		tvUname.setText(mItem.getUname());
		etTelPhone = (TextView) findViewById(R.id.user_edit_tv_telphone);
		etTelPhone.setText(mItem.getTelCode());

		rlname = (RelativeLayout)findViewById(R.id.rl_name);
		rlpasword = (RelativeLayout)findViewById(R.id.rl_pasword);
		rltelcode = (RelativeLayout)findViewById(R.id.rl_telcode);
		tvLogout = (TextView)findViewById(R.id.user_tv_logout);
		tvLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserInfoEditActivity.this, LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关键的一句，将新的activity置为栈顶
				startActivity(intent);
				finish();
			}
		});

		rlname.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserInfoEditActivity.this, ModNameActivity.class);
				startActivityForResult(intent,0);
			}
		});

		rlpasword.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserInfoEditActivity.this, ModPwdActivity.class);
				startActivityForResult(intent,1);
			}
		});

		rltelcode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserInfoEditActivity.this, VerifyActivity.class);
				startActivityForResult(intent,2);
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode){
			case 0:
				if(resultCode==RESULT_CANCELED)
					return;
				if(!data.getStringExtra("nickname").equals("null")){
				etName.setText(data.getStringExtra("nickname"));
				flag = true;
			}
				break;
			case 1:
				if(resultCode==RESULT_CANCELED)
					return;
				if(!data.getStringExtra("password").equals("null")){
				flag = true;
			}
				break;
			case 2:
				if(resultCode==RESULT_CANCELED)
					return;
				if(!data.getStringExtra("tele").equals("null")){
				etTelPhone.setText(data.getStringExtra("tele"));
				flag = true;
			}
				break;
			default:break;

		}
	}
}
