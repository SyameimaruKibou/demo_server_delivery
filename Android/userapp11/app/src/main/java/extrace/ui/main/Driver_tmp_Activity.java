package extrace.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import extrace.ui.domain.PackageDeliverActivity;
import extrace.ui.domain.PackageReceiveActivity;
import extrace.ui.misc.MapActivity;

import static extrace.ui.main.R.id;
import static extrace.ui.main.R.layout;

public class Driver_tmp_Activity extends AppCompatActivity  {

    Button receivebn;
    Button develiverbn;
    ImageView myview;
    TextView tvmap;

    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    @SuppressLint("HandlerLeak")
    Handler mHandler=new Handler() {

        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        setContentView(layout.activity_driver_tmp);


        receivebn=findViewById(id.driver_parcel_receive_btn);
        develiverbn=findViewById(R.id.driver_parcel_delive_btn);
        myview=findViewById(id.img_my_driver);
        tvmap=findViewById(id.driver_map_tv);


        receivebn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {//接收包裹
                Intent intent = new Intent();
                intent.setClass(Driver_tmp_Activity.this, PackageReceiveActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        develiverbn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {//交付包裹
                Intent intent = new Intent();
                intent.setClass(Driver_tmp_Activity.this, PackageDeliverActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        myview.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {//查看自己的信息
                Intent intent = new Intent();
                intent.setClass(Driver_tmp_Activity.this, UserInfoEditActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        tvmap.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Driver_tmp_Activity.this, MapActivity.class);
                startActivityForResult(intent, 0);
            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }


}
