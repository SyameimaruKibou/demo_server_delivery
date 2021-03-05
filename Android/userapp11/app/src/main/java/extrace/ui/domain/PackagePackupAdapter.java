package extrace.ui.domain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import extrace.misc.model.CodeNamePair;
import extrace.misc.model.ExpressSheet;
import extrace.ui.main.ExTraceApplication;
import extrace.ui.main.R;

public class PackagePackupAdapter extends BaseAdapter {

    private List<ExpressSheet> Datas;
    private Context mContext;
    private List<CodeNamePair> mlist =  new ArrayList<CodeNamePair>();
    private ExTraceApplication app;

    public PackagePackupAdapter(List<ExpressSheet> datas, Context mContext) {
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


    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        PackagePackupAdapter.hold hd = null;
        if(v==null){
            hd = new PackagePackupAdapter.hold();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_expresslist_packup, null);
            hd.no = (TextView)v.findViewById(R.id.ser);
            hd.id = (TextView)v.findViewById(R.id.tv_id);
            hd.sendertel = (TextView)v.findViewById(R.id.tv_sender);
            hd.fenlei = (TextView)v.findViewById(R.id.fenlei);
            hd.source = (TextView)v.findViewById(R.id.tv_source);
            hd.target = (TextView)v.findViewById(R.id.tv_target);

            v.setTag(hd);
        }else{
            hd = (PackagePackupAdapter.hold)v.getTag();
        }

        ExpressSheet es = getItem(position);//赋值
        hd.no.setText(String.valueOf(position+1));
        hd.id.setText(es.getID());
        hd.sendertel.setText(es.getSendertel());
        hd.fenlei.setText("文件");

        hd.source.setText(es.getSenderregcode());
        hd.target.setText(es.getNextnode().getNodeName());

        return v;
    }



    private class hold{
        TextView no;
        TextView id;
        TextView sendertel;
        TextView fenlei;
        TextView source;
        TextView target;
    }

}
