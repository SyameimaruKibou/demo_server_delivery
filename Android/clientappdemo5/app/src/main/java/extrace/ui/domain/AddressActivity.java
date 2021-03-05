package extrace.ui.domain;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import extrace.misc.model.TempCustomer;
import extrace.ui.main.R;
import extrace.ui.main.SendMesActivity;

public class AddressActivity extends AppCompatActivity {

    public static final int INTENT_NEWADDR = 3;
    public static final int INTENT_EDITADDR = 4;

    SharedPreferences sp;
    SharedPreferences.Editor et;
    private Context mContext;
    private ListView list;
    private Button addAdress;
    private TempCustomer customerInfo = new TempCustomer();
    private AddressAdapter addressAdapter = null;
    private List<TempCustomer> addressList = new ArrayList<TempCustomer>();
    private String jsonStr="";
    private boolean flagcancel =false;
    ConfirmDialogQuit confirmDialog ;
    Intent mintent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        mContext = AddressActivity.this;
        init();
        list=(ListView)findViewById(R.id.add_list);
        findViewById(R.id.goback).setOnClickListener(//返回键
                new View.OnClickListener() {
                    public void onClick(View view) {
                        finish();
                    }
                });
        addAdress=(Button)findViewById(R.id.btn_add);
        addAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(AddressActivity.this, SendMesActivity.class);
                intent.putExtra("Action", "NewAddr");//新建地址簿
                startActivityForResult(intent,AddressActivity.INTENT_NEWADDR);
            }
        });
        addressAdapter = new AddressAdapter(addressList,mContext);
        mintent =getIntent();

        list.setAdapter(addressAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mintent.hasExtra("addr")){
                    mintent.putExtra("CustomerInfo",addressList.get(position));
                    Log.i("开始返回信息：","开始传递"+addressList.get(position).getName());
                    setResult(RESULT_OK, mintent);//结果返回调用的Activity
                    finish();
                }
               // Toast.makeText(AddressActivity.this, "Click item" + position, Toast.LENGTH_SHORT).show();
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showLostFindDialog(position);
                return false;
            }

        });
        addressAdapter.setOnItemEdtClickListener(new AddressAdapter.onItemEdtListener() {
            @Override
            public void onEdtClick(int i) {
                Intent intent = new Intent();
                intent.setClass(AddressActivity.this, SendMesActivity.class);
                intent.putExtra("Action", "EdtAddr");//新建地址簿
                intent.putExtra("CustomerInfo",addressList.get(i));
                intent.putExtra("count",i);
                startActivityForResult(intent,AddressActivity.INTENT_EDITADDR);

               // Toast.makeText(AddressActivity.this, "点击图标了！"+i, Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void init(){//查成功！
        sp = getSharedPreferences("SP_ADD_List", Activity.MODE_PRIVATE);
        et = sp.edit();//修改器
        String PREFS_NAME = "ExTrace.cfg";
        Gson gson = new Gson();
        jsonStr = sp.getString("Address_List","");//获取
        if(jsonStr!=""){
            addressList = gson.fromJson(jsonStr,new TypeToken<List<TempCustomer>>(){}.getType());
        }
    }//存储 取出 地址

    public void onActivityResult(int requestCode, int resultCode, Intent data) {    //编辑界面的回调
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case INTENT_NEWADDR: //新增
                if(resultCode==RESULT_CANCELED)
                    return;
                if (data.hasExtra("CustomerInfo")) {
                    customerInfo = (TempCustomer) data.getSerializableExtra("CustomerInfo");
                    addressList.add(customerInfo);
                    addressAdapter.notifyDataSetChanged();
                    Gson gson = new Gson();
                    String jsonStr = gson.toJson(addressList);
                    et.putString("Address_List",jsonStr);//存储
                    et.commit();
                    Toast.makeText(AddressActivity.this,"添加成功!", Toast.LENGTH_SHORT).show();
                }
                break;
            case INTENT_EDITADDR:
                if(resultCode==RESULT_CANCELED)
                    return;
                if (data.hasExtra("CustomerInfo")) {//改 待定
                    customerInfo = (TempCustomer) data.getSerializableExtra("CustomerInfo");
                    int count = data.getIntExtra("count",-1);
                    addressList.remove(count);
                    addressList.add(count,customerInfo);
                    addressAdapter.notifyDataSetChanged();
                    Gson gson = new Gson();
                    String jsonStr = gson.toJson(addressList);
                    et.putString("Address_List",jsonStr);//存储
                    et.commit();
                    Toast.makeText(AddressActivity.this,"修改成功!", Toast.LENGTH_SHORT).show();
                }
                break;
                /*
            case RESULT_CANCELED://不正常的返回，按钮点击次数-1;
                if (requestCode == NEW_DELIEVER) {
                    deliever_bt_num--;
                }
                if (requestCode == NEW_RECEIVER) {
                    receiver_bt_num--;
                }
                break;
                 */

            default:
                break;
        }
    }

    protected void showLostFindDialog(final int position) {
        confirmDialog= new ConfirmDialogQuit(this, position);
        confirmDialog.show();

    }


    public class ConfirmDialogQuit extends Dialog implements
            View.OnClickListener {
        private Context context;
        private TextView titleTv,contentTv;
        private View okBtn,cancelBtn;
        private int postion;

        public ConfirmDialogQuit(Context context,int postion) {
            super(context);
            this.context = context;
            this.postion = postion;
            initalize();
        }


        //初始化View
        private void initalize() {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirm_dialog_quit, null);
            setContentView(view);
            initWindow();

            titleTv = (TextView) findViewById(R.id.title_name);
            contentTv = (TextView) findViewById(R.id.text_view);
            okBtn = findViewById(R.id.btn_ok);
            cancelBtn = findViewById(R.id.btn_cancel);
            okBtn.setOnClickListener(this);
            cancelBtn.setOnClickListener(this);
        }

        /**
         *添加黑色半透明背景
         */
        private void initWindow() {
            Window dialogWindow = getWindow();
            dialogWindow.setBackgroundDrawable(new ColorDrawable(0));//设置window背景
            dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//设置输入法显示模式
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            DisplayMetrics d = context.getResources().getDisplayMetrics();//获取屏幕尺寸
            lp.width = (int) (d.widthPixels * 0.8); //宽度为屏幕80%
            lp.gravity = Gravity.CENTER;  //中央居中
            dialogWindow.setAttributes(lp);
        }


        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.btn_ok:
                    deleteItem(postion);
                    confirmDialog.dismiss();
                    break;

                case R.id.btn_cancel:
                    confirmDialog.dismiss();

                default:
                    break;
            }
        }

        /**
         *添加按钮点击事件
         */
        private void deleteItem(int postion) {
            addressList.remove(postion);
            addressAdapter.notifyDataSetChanged();
            Gson gson = new Gson();
            String jsonStr = gson.toJson(addressList);
            et.putString("Address_List",jsonStr);//存储
            et.commit();
            Toast.makeText(AddressActivity.this, "删除成功!", Toast.LENGTH_SHORT).show();
        }
    }

}
