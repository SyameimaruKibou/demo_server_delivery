package extrace.ui.main;

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
import java.util.HashMap;
import java.util.Map;

import extrace.misc.model.ExpressSheet;

public class express_born_ok_Activity extends AppCompatActivity {

    TextView express_id;
    TextView express_fee;
    ImageView express_myqcode;
    ImageView network_im_download;
    ExpressSheet es;


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar1 = getSupportActionBar();
        if (actionBar1 != null) actionBar1.hide();
        setContentView(R.layout.activity_express_born_ok);

        express_id=findViewById(R.id.nexwork_express_id);
        express_fee=findViewById(R.id.network_express_fee);
        express_myqcode=findViewById(R.id.create_img);
        Intent intent=getIntent();
        es= (ExpressSheet) intent.getSerializableExtra("ExpressSheet");

        express_id.setText(es.getID());
        //    Log.i("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy",""+es.getID()+"-----"+intent.getStringExtra("ID"));
        express_fee.setText(Float.toString(es.getTransFee()));
        if(es.getID()!=null){
            generat();
        }
        if(es.getID()==null){
            Toast.makeText(this,"es.getId()为空",Toast.LENGTH_SHORT).show();
        }
        findViewById(R.id.user_tv_logout).setOnClickListener(new View.OnClickListener() {//确定按钮的监听！！！！！！

            @Override
            public void onClick(View view) {
                finish();;
            }
        });

        findViewById(R.id.nexwork_express_id_copy).setOnClickListener(new View.OnClickListener() {//确定按钮的监听！！！！！！

            @Override
            public void onClick(View view) {//复制订单ID的按钮
                ClipboardManager cm =(ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(express_id.getText().toString());
                // 创建普通字符型ClipData
                //ClipData mClipData = ClipData.newPlainText("",express_id.getText().toString());
                // 将ClipData内容放到系统剪贴板里。
                //cm.setPrimaryClip(mClipData);
                Toast.makeText(express_born_ok_Activity.this, "订单号已复制到剪切板，快去粘贴吧", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.network_im_download).setOnClickListener(new View.OnClickListener() {//复制二维码按钮的监听！！！！！！
            @Override
            public void onClick(View view) {
                savebitmap();
                Toast.makeText(express_born_ok_Activity.this, "二维码已保存到本地！", Toast.LENGTH_SHORT).show();
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
        String content = "" ;
        // if (myedit.getText().toString().equals("")){
        //Toast.makeText(this,"请输入二维码信息",Toast.LENGTH_SHORT).show();
        // return;
        // }


        // else {

        // SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        // content=df.format(new Date());
        content=es.getID();
        // String mycontent=content.substring(0,4)+content.substring(5,7)+content.substring(8,10)+content.substring(11,13)+content.substring(14,16)+content.substring(17,19);
        //content = myedit.getText().toString();
        System.out.println(content);
        Bitmap qrBitmap = generateBitmap(content,400, 400);
        express_myqcode.setImageBitmap(qrBitmap);

    }

    public void savebitmap() {
        express_myqcode.buildDrawingCache();

        Bitmap bitmap = express_myqcode.getDrawingCache();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Zuoyue");
        if (!dir.isFile()) {
            dir.mkdir();
        }
        //File file = new File(dir, System.currentTimeMillis() + ".png");
        File file = new File(dir, "快递ID="+es.getID() + ".png");
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

}
