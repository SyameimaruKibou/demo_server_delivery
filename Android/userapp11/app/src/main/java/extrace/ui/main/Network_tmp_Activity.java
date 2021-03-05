package extrace.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import extrace.ui.domain.PackageDepackActivity;
import extrace.ui.domain.PackagePackupActivity;
import extrace.ui.domain.TransNodeActivity;

public class Network_tmp_Activity extends AppCompatActivity {

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
        ActionBar actionBar1 = getSupportActionBar();
        if (actionBar1 != null) actionBar1.hide();
        setContentView(R.layout.activity_network_tmp);

        AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                .setTitle("登陆的时候要先选择工作节点")//标题
                .setMessage("（后期就不用每次登陆就修改）")//内容
                .setIcon(R.mipmap.ic_launcher)//图标
                .create();
        alertDialog1.show();

        findViewById(R.id.networker_sheet_rev_btn).setOnClickListener(//揽收
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Receive();
                    }
                });

        findViewById(R.id.networker_sheet_packup_btn).setOnClickListener(//打包
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Packup();
                    }
                });

        findViewById(R.id.networker_sheet_depack_btn).setOnClickListener(//拆包
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Depackup();
                    }
                });


        findViewById(R.id.networkder_node_find).setOnClickListener(//查看网点信息
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Nodeinfo();
                    }
                });

        findViewById(R.id.img_my_networker).setOnClickListener(//我的资料
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CheckMyself();
                    }
                });

        findViewById(R.id.tracsnode_choose_tv).setOnClickListener(//我的资料
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ChooseNode();
                    }
                });


    }





    void Receive(){
        Intent intent = new Intent();
        intent.setClass(Network_tmp_Activity.this, Express_Receive_Activity.class);
        startActivityForResult(intent, 0);
    }


    void Packup()
    {
        Intent intent = new Intent();
        intent.setClass(Network_tmp_Activity.this, PackagePackupActivity.class);
        startActivityForResult(intent, 0);
    }

    void Depackup()
    {
        Intent intent = new Intent();
        intent.setClass(Network_tmp_Activity.this, PackageDepackActivity.class);
        startActivityForResult(intent, 0);
    }
    //
    void Nodeinfo()
    {
        Intent intent = new Intent();
        intent.setClass(Network_tmp_Activity.this, TransNodeActivity.class);
        startActivityForResult(intent, 1);
    }
    void CheckMyself()
    {
        Intent intent = new Intent();
        intent.setClass(Network_tmp_Activity.this, UserInfoEditActivity.class);
        startActivityForResult(intent, 0);
    }

    void ChooseNode()
    {
        Intent intent = new Intent();
        intent.setClass(Network_tmp_Activity.this, choose_transnode_activity.class);
        startActivityForResult(intent, 0);
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
