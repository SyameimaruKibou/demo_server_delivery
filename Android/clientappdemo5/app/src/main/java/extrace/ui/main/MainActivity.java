package extrace.ui.main;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import extrace.misc.model.UserInfo;
import extrace.net.IDataAdapter;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, IDataAdapter<UserInfo> {

    private LinearLayout homepageLin, checkLin, myLin;
    private FrameLayout mFrameLayout;
    private HomeFragment mHomeFragment;
    private CheckFragment mCheckFragment;
    private MyFragment mMyFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private UserInfo mItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFrameLayout = findViewById(R.id.frame_main);
        homepageLin = findViewById(R.id.lin_homepage);
        checkLin = findViewById(R.id.lin_check);
        myLin = findViewById(R.id.lin_my);
        homepageLin.setOnClickListener(this);
        checkLin.setOnClickListener(this);
        myLin.setOnClickListener(this);
        //获取FragmentManager对象
        manager = getSupportFragmentManager();
        //获取FragmentTransaction对象
        transaction = manager.beginTransaction();
        Intent intent = getIntent();
        int id = intent.getIntExtra("flag", 0);
        if (id > 0) {
            setSwPage(2);
        }
        else{
            setSwPage(0);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lin_homepage:
                setSwPage(0);
                break;
            case R.id.lin_check:
                setSwPage(1);
                break;
            case R.id.lin_my:
                setSwPage(2);
                break;
        }
    }

    public void setSwPage(int i) {
        //获取FragmentManager对象
        manager = getSupportFragmentManager();
        //获取FragmentTransaction对象
        transaction = manager.beginTransaction();
        //先隐藏所有的Fragment
        hideFragments(transaction);
        switch (i) {
            case 0:
                if (mHomeFragment== null) {
                    mHomeFragment = new HomeFragment();
                    transaction.add(R.id.frame_main, mHomeFragment);
                } else {
                    //如果微信对应的Fragment已经实例化，则直接显示出来
                    transaction.show(mHomeFragment);
                }
                break;
            case 1:
                if (mCheckFragment == null) {
                    mCheckFragment = new CheckFragment();
                    transaction.add(R.id.frame_main, mCheckFragment);
                } else {
                    //如果微信对应的Fragment已经实例化，则直接显示出来
                    transaction.show(mCheckFragment);
                }
                break;
            case 2:
                if (mMyFragment == null) {
                    mMyFragment = new MyFragment();
                    transaction.add(R.id.frame_main, mMyFragment);
                } else {
                    //如果微信对应的Fragment已经实例化，则直接显示出来
                    transaction.show(mMyFragment);
                }
                break;
        }
        transaction.commit();
    }

    //将四个的Fragment隐藏
    private void hideFragments(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mCheckFragment != null) {
            transaction.hide(mCheckFragment);
        }
        if (mMyFragment != null) {
            transaction.hide(mMyFragment);
        }
    }
    @Override
    public UserInfo getData() {
        return mItem;
    }

    @Override
    public void setData(UserInfo data) {
        mItem=data;
    }

    @Override
    public void notifyDataSetChanged() {
        //一定要在此方法中进行数据处理

    }

}
