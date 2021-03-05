package extrace.ui.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import extrace.misc.model.UserInfo;

public class SetActivity extends AppCompatActivity {

    TextView tvSave;
    EditText etURL;
    private ExTraceApplication app;
    private UserInfo user;
    SharedPreferences sp;
    SharedPreferences.Editor et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar1 = getSupportActionBar();
        if (actionBar1 != null) actionBar1.hide();
        setContentView(R.layout.activity_set);

        sp = getPreferences(MODE_PRIVATE);//模式:其他app是否能看到
        et = sp.edit();//修改器
        app = (ExTraceApplication) getApplication();

        tvSave = (TextView) findViewById(R.id.set_tv_save);
        etURL = (EditText) findViewById(R.id.set_et_url);
        etURL.setText(app.getServerUrl());
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String URL = etURL.getText().toString();
                app.setServerUrl(URL);
                Toast.makeText(SetActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                //onKeyDown(KeyEvent.KEYCODE_BACK, null);         //返回
                onBackPressed();        //返回
            }
        });
    }
}
