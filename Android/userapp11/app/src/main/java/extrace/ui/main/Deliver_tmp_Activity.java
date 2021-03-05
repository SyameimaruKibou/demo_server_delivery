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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import extrace.ui.domain.ExpressDeliveActivity;
import extrace.ui.domain.ExpressPaiSongActivity;
import extrace.ui.misc.MapActivity;

public class Deliver_tmp_Activity extends AppCompatActivity {

    Button receivebn;
    Button develiverbn;
    Button map;
    ImageView myview;
    TextView textView;

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
        setContentView(R.layout.activity_deliver_tmp);

        AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                .setTitle("登陆的时候要先选择工作节点")//标题
                .setMessage("（后期就不用每次登陆就修改）")//内容
                .setIcon(R.mipmap.ic_launcher)//图标
                .create();
        alertDialog1.show();



        receivebn=findViewById(R.id.deliver_sheet_prepai_btn);
        develiverbn=findViewById(R.id.deliver_sign_scan_btn);
        map=findViewById(R.id.deliver_map_location);
        myview=findViewById(R.id.img_my_driver);
        textView=findViewById(R.id.tracsnode_choose_tv);



        receivebn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {//待派送快递列表
                Intent intent = new Intent();
                intent.setClass(Deliver_tmp_Activity.this, ExpressPaiSongActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        develiverbn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {//签收扫一扫
                Intent intent = new Intent();
                intent.setClass(Deliver_tmp_Activity.this, ExpressDeliveActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        map.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {//地图定位导航！
                Intent intent = new Intent();
                intent.setClass(Deliver_tmp_Activity.this, MapActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        myview.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {//查看自己的资料
                Intent intent = new Intent();
                intent.setClass(Deliver_tmp_Activity.this, UserInfoEditActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        textView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {//选择节点！
                Intent intent = new Intent();
                intent.setClass(Deliver_tmp_Activity.this, choose_transnode_activity.class);
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
