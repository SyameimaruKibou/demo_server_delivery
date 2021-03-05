package extrace.ui.domain;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import extrace.loader.PackupLoader;
import extrace.misc.model.ArrayOfString;
import extrace.net.IDataAdapter;
import extrace.ui.main.ExTraceApplication;
import extrace.ui.main.R;

@SuppressLint("Registered")
public class PackagePackupOkActivity extends AppCompatActivity implements IDataAdapter<String> {

    private TextView package_id,target_name;
    private ImageView package_myqcode;
    private String pkid ="";
    private PackupLoader mloader;
    private String userid,sourceid,targetid,sourcename,targetname;
    private ExTraceApplication app;
    private ArrayList<String> mlist = new ArrayList<String>();
    private ArrayOfString arrayOfString = new ArrayOfString();


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar1 = getSupportActionBar();
        if (actionBar1 != null) actionBar1.hide();
        setContentView(R.layout.activity_package_packup_ok);
       //Toast.makeText(this, "11111111111111111111111111111111111111111", Toast.LENGTH_SHORT).show();

        app = (ExTraceApplication) getApplication();
        userid = String.valueOf(app.getLoginUser().getUID());
        sourceid=app.getNode().getID();

        Intent i = getIntent();
        targetname = i.getStringExtra("targetname");
        //Toast.makeText(this, targetname, Toast.LENGTH_SHORT).show();
        targetid = i.getStringExtra("targetid");
        //Toast.makeText(this, targetid, Toast.LENGTH_SHORT).show();
        mlist = i.getStringArrayListExtra("list");//StringArrayListExtra
        //Toast.makeText(getApplication(),mlist.get(0)+mlist.get(1) , Toast.LENGTH_SHORT).show();
        arrayOfString.setList(mlist);

        target_name = findViewById(R.id.tv_nextnode);
        target_name.setText(targetname);

        package_id = findViewById(R.id.tv_package_id);
        package_myqcode = findViewById(R.id.create_img);
        mloader = new PackupLoader(this,this);
        Log.i("888888888888888888888888888888888888888888888",""+sourceid+"===="+targetid+"===="+targetname);
        mloader.Packup(arrayOfString,sourceid,targetid,userid);//赋值


        findViewById(R.id.btn_born_ok).setOnClickListener(new View.OnClickListener() {//确定按钮的监听！！！！！！

            @Override
            public void onClick(View view) {
                //Toast.makeText(PackagePackupOkActivity.this, "打包成功！", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        findViewById(R.id.package_id_copy).setOnClickListener(new View.OnClickListener() {//确定按钮的监听！！！！！！

            @Override
            public void onClick(View view) {//复制订单ID的按钮
                ClipboardManager cm =(ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(package_id.getText().toString());
                Toast.makeText(PackagePackupOkActivity.this, "订单号已复制到剪切板，快去粘贴吧", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.package_im_download).setOnClickListener(new View.OnClickListener() {//复制二维码按钮的监听！！！！！！
            @Override
            public void onClick(View view) {
                savebitmap();
                Toast.makeText(PackagePackupOkActivity.this, "二维码已保存到本地！", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Bitmap generateBitmap(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void generat() {
        String content = "";
        // if (myedit.getText().toString().equals("")){
        //Toast.makeText(this,"请输入二维码信息",Toast.LENGTH_SHORT).show();
        // return;
        // }


        // else {

        // SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        // content=df.format(new Date());
        content =pkid;
        // String mycontent=content.substring(0,4)+content.substring(5,7)+content.substring(8,10)+content.substring(11,13)+content.substring(14,16)+content.substring(17,19);
        //content = myedit.getText().toString();
        System.out.println(content);
        Bitmap qrBitmap = generateBitmap(content, 400, 400);
        package_myqcode.setImageBitmap(qrBitmap);

    }

    public void savebitmap() {
        package_myqcode.buildDrawingCache();

        Bitmap bitmap = package_myqcode.getDrawingCache();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Zuoyue");
        if (!dir.isFile()) {
            dir.mkdir();
        }
        //File file = new File(dir, System.currentTimeMillis() + ".png");
        File file = new File(dir, "包裹ID="+package_id.getText().toString()+ ".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(byteArray, 0, byteArray.length);

            fos.flush();

            //用广播通知相册进行更新相册

            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            sendBroadcast(intent);
            Log.v("LOG_TAG", "------>通知相册更新成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getData() {
        return pkid;
    }

    @Override
    public void setData(String data) {
        pkid=data;
    }

    @Override
    public void notifyDataSetChanged() {
        package_id.setText(pkid);
        Toast.makeText(this,"包裹号为"+pkid,Toast.LENGTH_SHORT).show();
        if (pkid != null) {
            generat();
            Toast.makeText(this, "generat!", Toast.LENGTH_SHORT).show();
        }else  {
            Toast.makeText(this, "包裹号为空", Toast.LENGTH_SHORT).show();
        }
    }
}
