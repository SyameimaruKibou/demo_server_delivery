package extrace.ui.misc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import extrace.misc.model.TransNode;
import extrace.net.IDataAdapter;
import extrace.ui.main.R;

public class TransNodeAdapter extends ArrayAdapter<TransNode> implements IDataAdapter<List<TransNode>> {

    private List<TransNode> itemList;
    private Context context;

    public TransNodeAdapter( Context ctx, List<TransNode> itemList) {
        super(ctx, R.layout.item_transnode, itemList);
        this.itemList = itemList;
        this.context = ctx;
    }

    public int getCount() {
        if (itemList != null)
            return itemList.size();
        return 0;
    }

    public TransNode getItem(int position) {
        if (itemList != null)
            return itemList.get(position);
        return null;
    }

    public long getItemId(int position) {
        if (itemList != null)
            return itemList.get(position).hashCode();
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        hold hd = null;
        if(v==null){//
            hd = new hold();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_transnode, null);
            hd.ID = (TextView)v.findViewById(R.id.id);
            hd.telCode = (TextView)v.findViewById(R.id.tele_code);
            hd.nodetype = (TextView)v.findViewById(R.id.node_type);
            hd.nodename = (TextView)v.findViewById(R.id.node_name);
            hd.regioncode = (TextView)v.findViewById(R.id.region_code);
            hd.x = (TextView)v.findViewById(R.id.x);
            hd.y = (TextView)v.findViewById(R.id.y);
            hd.status = (TextView)v.findViewById(R.id.status);

            v.setTag(hd);
        }else{
            hd = (hold)v.getTag();
        }

        TransNode es = getItem(position);
        hd.ID.setText(String.valueOf(es.getID()));
        hd.nodename.setText(es.getNodeName());
        hd.regioncode.setText(es.getRegionCode());
        hd.telCode.setText(es.getTelCode());
        hd.x.setText(es.getX().toString());
        hd.y.setText(es.getY().toString());

        String stText = "";
        switch(es.getNodeType()){
            case 1:
                stText = "网点";
                break;
            case 2:
                stText = "分拣中心";
                break;
        }
        hd.nodetype.setText(stText);
        return v;
    }

    @Override
    public List<TransNode> getData() {
        return itemList;
    }

    @Override
    public void setData(List<TransNode> data) {
        this.itemList = data;
    }

    private class hold{
        TextView ID;
        TextView nodetype;
        TextView nodename;
        TextView regioncode;
        TextView telCode;
        TextView x;
        TextView y;
        TextView status;
    }
}
