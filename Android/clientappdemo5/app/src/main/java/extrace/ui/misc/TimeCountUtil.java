package extrace.ui.misc;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

public class TimeCountUtil extends CountDownTimer {
    private TextView mtextview;

    public TimeCountUtil(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mtextview = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        // 按钮不可用
        mtextview .setEnabled(false);
        mtextview.setTextColor(Color.GRAY);
        String showText = millisUntilFinished / 1000 + "秒后可重新发送";
        mtextview .setText(showText);
        //mtextview.setTextSize(8);
    }

    @Override
    public void onFinish() {
        // 按钮设置可用
        mtextview .setEnabled(true);
        mtextview.setTextColor(Color.parseColor("#4169E1"));
        mtextview .setText("重新获取");
    }
}
