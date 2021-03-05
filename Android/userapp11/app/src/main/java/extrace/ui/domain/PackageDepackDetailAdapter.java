package extrace.ui.domain;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import extrace.loader.RegionListLoader;
import extrace.misc.model.CodeNamePair;
import extrace.misc.model.ExpressSheet;
import extrace.ui.main.R;

public class PackageDepackDetailAdapter extends BaseAdapter  {

    private List<ExpressSheet> Datas;
    private Context mContext;
    private List<CodeNamePair> mlist =  new ArrayList<CodeNamePair>();
    private RegionListLoader listLoader;

    public PackageDepackDetailAdapter(List<ExpressSheet> datas, Context mContext) {
        Datas = datas;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return Datas.size();
    }

    @Override
    public ExpressSheet getItem(int i) {
        return Datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        PackageDepackDetailAdapter.hold hd = null;
        if(v==null){
            hd = new PackageDepackDetailAdapter.hold();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_transpackage_detial, null);
            hd.no = (TextView)v.findViewById(R.id.ser);
            hd.id = (TextView)v.findViewById(R.id.tv_id);
            hd.sender = (TextView)v.findViewById(R.id.tv_sender);
            hd.reciver = (TextView)v.findViewById(R.id.tv_reciver);
            hd.target = (TextView)v.findViewById(R.id.tv_target);
            hd.status = (TextView)v.findViewById(R.id.tv_btn);
            v.setTag(hd);
        }else{
            hd = (PackageDepackDetailAdapter.hold)v.getTag();
        }

        ExpressSheet es = getItem(position);//赋值
        hd.no.setText(String.valueOf(position+1));
        hd.id.setText(es.getID());
        hd.sender.setText(es.getReceivername());
        hd.reciver.setText(es.getReceivertel());
        hd.target.setText(es.getNextnode().getNodeName());

        //设置颜色
        if(es.getStatus()==1){//打包中
            hd.status.setText("未分拣");
            hd.status.setBackgroundColor(Color.GREEN);
        }
        else {
            hd.status.setText("已分拣");
            hd.status.setBackgroundColor(Color.RED);
        }

        return v;
    }

    private class hold{
        TextView no;
        TextView id;
        TextView sender;
        TextView reciver;
        TextView target;
        TextView status;
    }

}
