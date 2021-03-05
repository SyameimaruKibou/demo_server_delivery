package extrace.ui.domain;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import extrace.loader.TransNodeLoader;
import extrace.misc.model.TransNode;
import extrace.misc.model.UserInfo;
import extrace.net.IDataAdapter;
import extrace.ui.main.ExTraceApplication;
import extrace.ui.main.R;
import extrace.ui.misc.TransNodeAdapter;

public class TransNodeActivity extends AppCompatActivity implements IDataAdapter<List<TransNode>> {


    private UserInfo user;
    private ExTraceApplication app;
    private ListView listView;
    private TransNodeLoader mLoader;
    private List<TransNode> list;
    TransNode tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        setContentView(R.layout.activity_transnode);


        app = (ExTraceApplication) getApplication();
        user = app.getLoginUser();

        Toast.makeText(this, user.getUname()+"YES!!!!", Toast.LENGTH_SHORT).show();

        listView = (ListView) findViewById(R.id.transnode_listview);
        mLoader = new TransNodeLoader(TransNodeActivity.this, TransNodeActivity.this);
        mLoader.TransNodeList();


    //    list=this.getData();


    }

    public List<TransNode> getData() {
        return list;
    }

    @Override
    public void setData(List<TransNode> data) {
        //mItem = new UserInfo();
        list = data;
    }

    @SuppressLint("LongLogTag")
    public void notifyDataSetChanged() {
        Log.i("hhhhhhhhhhhhhhhhhhhhhhhhhh",""+list.size());
       // Toast.makeText(this, ""+list.size(), Toast.LENGTH_SHORT).show();
        TransNodeAdapter adapter = new TransNodeAdapter(this,list);
        listView.setAdapter(adapter);

    }
}
