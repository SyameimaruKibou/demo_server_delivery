package extrace.ui.misc;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import extrace.ui.main.R;

public class Qrcode extends AppCompatActivity {
    private ImageView iv;
    private TextView myedit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        iv=(ImageView)findViewById(R.id.create_img);
        myedit=(TextView) findViewById(R.id.create_edit);

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
    public void generate(View view) {
        String content = "" ;
        // if (myedit.getText().toString().equals("")){
        //Toast.makeText(this,"请输入二维码信息",Toast.LENGTH_SHORT).show();
        // return;
        // }


        // else {

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        content=df.format(new Date());
        // String mycontent=content.substring(0,4)+content.substring(5,7)+content.substring(8,10)+content.substring(11,13)+content.substring(14,16)+content.substring(17,19);
        //content = myedit.getText().toString();
        System.out.println(content);
        Bitmap qrBitmap = generateBitmap(content,400, 400);
        iv.setImageBitmap(qrBitmap);
        myedit.setText(content);
    }

    //}








}
