package extrace.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import extrace.loader.TransNodeLoader;
import extrace.misc.model.TransNode;
import extrace.misc.model.UserInfo;
import extrace.net.IDataAdapter;
import extrace.ui.misc.TransNodeAdapter;

public class choose_transnode_activity extends AppCompatActivity implements IDataAdapter<List<TransNode>>, AdapterView.OnItemClickListener{

    private RadioButton nrg1;
    private RadioButton nrg2;
    private RadioButton nrg3;
    private RadioGroup rg1;



    private ImageView btnGetRegion;
    private ExTraceApplication app;
    private UserInfo user;
    int urole;
    String uname;
    private TextView hello;
    private TextView transnode_tx_region;
    private RelativeLayout transnode_sorter;

    private Spinner mspinner;
    private String zhye;
    private TransNodeAdapter adapter;
    private TransNodeLoader mLoader;
    private TransNode node;

    boolean addr=false;
    boolean choose=false;

    final int TRANSNODE=505;

    String choosecode;
    String chooseaddr;
    private List<TransNode> list;

    ListView iiiii;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        setContentView(R.layout.activity_choose_transnode);


        btnGetRegion=(ImageView)findViewById(R.id.transnode_bn_region);

        rg1=findViewById(R.id.rg_1);
        rg1.setOnCheckedChangeListener(radiogpchange);//按钮组的监听
        transnode_tx_region=findViewById(R.id.transnode_tx_region);//地区的填写框
        transnode_sorter=findViewById(R.id.transnode_sorter);//选择分拣区的级别的整个布局

        nrg1=findViewById(R.id.rb_1);//分拣区的三个按钮
        nrg2=findViewById(R.id.rb_2);
        nrg3=findViewById(R.id.rb_3);

        iiiii=findViewById(R.id.iiiii);//显示具体分拣区或者网点的listview！！！！！！！！！

        app = (ExTraceApplication)getApplication();//获取登陆者的信息
        user = app.getLoginUser();
        urole=user.getURole();
        uname=user.getName();

        hello=findViewById(R.id.transnode_hello);//根据登录身份显示出来登录者的信息
        hello.setText(uname+"的登陆身份："+checkRole(urole));
        Toast.makeText(this, "xxx"+urole, Toast.LENGTH_SHORT).show();


        if(urole==2){//分拣区人员
            //Toast.makeText(this, "xianxian！", Toast.LENGTH_SHORT).show();
            transnode_sorter.setVisibility(View.VISIBLE);//分拣级别的单选按钮布局显示;

        }else if(urole==1||urole==4){//快递员和网点人员
            choose=true;


        }

        btnGetRegion.setOnClickListener(//填写地址按钮
        new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {
                //	Log.i("CusterEditActivity的地区GetRegion开始调用","成功！！！！！！！！！！！！！！！");
                GetRegion();
            }
        });

        findViewById(R.id.transnode_save).setOnClickListener(//保存信息
        new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {
                //	Log.i("CusterEditActivity的地区GetRegion开始调用","成功！！！！！！！！！！！！！！！");
                Save();
            }
        });

        findViewById(R.id.arrow_left).setOnClickListener(//返回键
            new View.OnClickListener() {
                public void onClick(View view) {
                    finish();
                }
        });


    }

    private String checkRole(int role){
        if(role==1){
            return "网点工作人员";
        }else if(role==2){
            return "分拣中心工作人员";
        }else if(role==4){
            return "快递员";
        }
        return"出错";
    }

    private RadioGroup.OnCheckedChangeListener radiogpchange = new RadioGroup.OnCheckedChangeListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            choose=true;
            if (checkedId == nrg1.getId()) {//省级分拣站
               // showPosition(PROV,NO,NO,NO);
                Toast.makeText(getApplicationContext(), "省级分拣站", 1).show();
                refresh();//清空地址填写框

            } else if (checkedId == nrg2.getId()) {//市级分拣站
                //showPosition(PROV,CITY,NO,NO);
                Toast.makeText(getApplicationContext(), "市级分拣站", 1).show();
                refresh();

            }else if (checkedId == nrg3.getId()) {//区级分拣站
                //showPosition(PROV,CITY,STAT,NO);
                Toast.makeText(getApplicationContext(), "区级分拣站", 1).show();
                refresh();

            }
        }
    };



    private void GetRegion(){//根据身份或者选择分拣区的级别选择确定地区选择的长度length，然后过得地址
        if(choose==false){
            Toast.makeText(this, "工作分拣站的类型未进行选择！", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, RegionListActivity.class);
        intent.putExtra("RegionId", choosecode);//将原本文本框内的信息传给RegionActivity
        intent.putExtra("RegionString", chooseaddr);
        if(urole==4){
            intent.putExtra("Length", 3);
            startActivityForResult(intent, TRANSNODE);//和RegionActivity通信，将其地址string返回
            return ;
        }
        else if(nrg1.isChecked()){
            intent.putExtra("Length", 1);

        }else if(nrg2.isChecked()){
            intent.putExtra("Length", 2);

        }else if(nrg3.isChecked()){
            intent.putExtra("Length", 3);
        }
        startActivityForResult(intent, TRANSNODE);//和RegionActivity通信，将其地址string返回
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK://完成
                String regionId, regionString;
                if (data.hasExtra("RegionId")) {
                    choosecode = data.getStringExtra("RegionId");
                    chooseaddr = data.getStringExtra("RegionString");
                } else {
                    choosecode = "";
                    chooseaddr = "";
                }
                transnode_tx_region.setText(chooseaddr);
                mLoader = new TransNodeLoader(this, this);
                mLoader.getRefionTransNodeList(choosecode);
                addr=true;
                break;
            default:
                break;
        }
    }

    private void Save(){
        if(addr==false){
            Toast.makeText(this, "工作节点的地址未进行选择！", Toast.LENGTH_SHORT).show();
            return;
        }
        if(node==null){
            Toast.makeText(this, "具体节点未进行选择！", Toast.LENGTH_SHORT).show();
            return;
        }
        app.setNode(node);
        Toast.makeText(this, "保存ok！", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void refresh(){//清空地址填写框
        addr=false;
        transnode_tx_region.setText("");
    }

    @Override
    public List<TransNode> getData() {
        return list;
    }

    @Override
    public void setData(List<TransNode> data) {
        list=data;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void notifyDataSetChanged() {
        //Toast.makeText(this, "！"+list.size(), Toast.LENGTH_SHORT).show();
        //Log.i("9999999999999999999999999999999999999999999",""+list.size());
        adapter = new TransNodeAdapter(this,list);

        iiiii.setAdapter(adapter);
        iiiii.setOnItemClickListener((AdapterView.OnItemClickListener) choose_transnode_activity.this);
        iiiii.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        node=adapter.getItem(i);
        Toast.makeText(this, "！"+node.getNodeName(), Toast.LENGTH_SHORT).show();
    }
}
