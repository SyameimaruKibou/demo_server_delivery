package extrace.ui.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import extrace.misc.model.ExpressHistory;
import extrace.ui.main.R;

public class HistoryAdapter extends BaseAdapter {
    private Context context;
    private List<ExpressHistory> traceList = new ArrayList<>(1);
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL= 0x0001;


    public HistoryAdapter(Context context, List<ExpressHistory> traceList) {
        this.context = context;
        this.traceList = traceList;
    }

    @Override
    public int getCount() {
        return traceList.size();
    }

    @Override
    public ExpressHistory getItem(int position) {
        return traceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final ExpressHistory trace = getItem(position);
        String messgae="";
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
            holder.tvAcceptTime = (TextView) convertView.findViewById(R.id.tvAcceptTime);
            holder.tvAcceptStation = (TextView) convertView.findViewById(R.id.tvAcceptStation);
           // holder.tvTargetStation = (TextView) convertView.findViewById(R.id.tvTargetStation);
            holder.tvTopLine = (TextView) convertView.findViewById(R.id.tvTopLine);
            holder.tvDot = (TextView) convertView.findViewById(R.id.tvDot);
            holder.tv_new = (TextView) convertView.findViewById(R.id.tv_new);
            convertView.setTag(holder);
        }

        if (getItemViewType(position) == TYPE_TOP) {
            // 第一行头的竖线不显示
            holder.tvTopLine.setVisibility(View.INVISIBLE);
            holder.tv_new.setVisibility(View.VISIBLE);
            // 字体颜色加深
            holder.tvAcceptTime.setTextColor(context.getResources().getColor(R.color.red));
            holder.tvAcceptStation.setTextColor(context.getResources().getColor(R.color.red));
            holder.tvDot.setBackgroundResource(R.drawable.dot_first);
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            holder.tvTopLine.setVisibility(View.VISIBLE);
            holder.tv_new.setVisibility(View.INVISIBLE);
            holder.tvAcceptTime.setTextColor(0xff999999);
            holder.tvAcceptStation.setTextColor(0xff999999);
            holder.tvDot.setBackgroundResource(R.drawable.dot);
        }

        switch (trace.getActId()){
            case 0://ok
                messgae = "卓越快递在【"+trace.getSourcenode().getNodeName()+"】已收取快件";
                break;
            case 1://ok
                messgae = "快件在【"+trace.getSourcenode().getNodeName()+"】已打包，准备发往【"+trace.getTargetnode().getNodeName()+"】";
                break;
            case 2:
                messgae = "快件正在【"+trace.getSourcenode().getNodeName()+"】分拣中心进行分拣,负责人为："+trace.getUser().getName();
                break;
            case 3://ok
                messgae = "快件已发车";
                break;
            case 4://ok
                messgae = "快件到达【"+trace.getSourcenode().getNodeName()+"】";
                break;
            case 5://ok
                messgae = "快递员"+trace.getUser().getName()+"(电话:"+trace.getUser().getTelCode()+")正在派送您的快件，请保持电话畅通哦~";
                break;
            case 6://ok
                messgae = "您的快件已签收！";
                break;

        }
        holder.tvAcceptTime.setText(getStringDate(trace.getActTime()));
        //Toast.makeText(context, getStringDate(trace.getActTime()), Toast.LENGTH_LONG).show();
        holder.tvAcceptStation.setText(messgae);

        return convertView;
    }

    @Override
    public int getItemViewType(int id) {
        if (id == 0) {
            return TYPE_TOP;
        }
        return TYPE_NORMAL;
    }

    static class ViewHolder {
        public TextView tvAcceptTime, tvAcceptStation,tvTargetStation;
        public TextView tvTopLine, tvDot,tv_new;
    }

    public static String getStringDate(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
        }
}
