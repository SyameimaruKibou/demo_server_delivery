package extrace.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import extrace.ui.domain.PackageDepackActivity;
import extrace.ui.domain.PackagePackupActivity;
import extrace.ui.domain.TransNodeActivity;

public class Sorter_tmp_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar1 = getSupportActionBar();
        if (actionBar1 != null) actionBar1.hide();
        setContentView(R.layout.activity_sorter_tmp);


        findViewById(R.id.sorter_sheet_packup_btn).setOnClickListener(//分拣打包
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Packup();
                    }
                });

        findViewById(R.id.sorter_sheet_depack_btn).setOnClickListener(//拆包扫一扫
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DePackup();
                    }
                });

        findViewById(R.id.img_my_sorter).setOnClickListener(//我的资料
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CheckMyself();
                    }
                });

        findViewById(R.id.tracsnode_choose_tv).setOnClickListener(//节点选择
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NodeChoose();
                    }
                });//sorter_node_find

        findViewById(R.id.sorter_node_find).setOnClickListener(//分拣中心信息
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NodeMessage();
                    }
                });

        
    }

    void  Packup()
    {
        Intent intent = new Intent();
        intent.setClass(Sorter_tmp_Activity.this, PackagePackupActivity.class);
        startActivityForResult(intent, 0);
    }

    void DePackup()
    {
        Intent intent = new Intent();
        intent.setClass(Sorter_tmp_Activity.this, PackageDepackActivity.class);
        startActivityForResult(intent, 0);
    }

    void NodeChoose()
    {
        Intent intent = new Intent();
        intent.setClass(Sorter_tmp_Activity.this, choose_transnode_activity.class);
        startActivityForResult(intent, 0);
    }

    void NodeMessage()
    {
        Intent intent = new Intent();
        intent.setClass(Sorter_tmp_Activity.this, TransNodeActivity.class);
        startActivityForResult(intent, 0);
    }

    void CheckMyself()
    {
        Intent intent = new Intent();
        intent.setClass(Sorter_tmp_Activity.this,UserInfoEditActivity.class);
        startActivityForResult(intent, 0);
    }
    
    
    
}
