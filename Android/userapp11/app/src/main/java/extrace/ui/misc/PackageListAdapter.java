package extrace.ui.misc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import extrace.misc.model.TransPackage;
import extrace.net.IDataAdapter;
import extrace.ui.main.R;

public class PackageListAdapter extends ArrayAdapter<TransPackage> implements IDataAdapter<List<TransPackage>> {
    private List<TransPackage> itemList;
    private Context context;
    private String ex_type;

    @SuppressLint("LongLogTag")
    public PackageListAdapter (List<TransPackage> itemList, Context ctx) {
        super(ctx, R.layout.item_transpackage, itemList);

        this.itemList = itemList;
        this.context = ctx;
    }

    public int getCount() {
        if (itemList != null)
            return itemList.size();
        return 0;
    }

    public TransPackage getItem(int position) {
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
        PackageListAdapter.hold hd = null;

        if(v==null){
            hd = new PackageListAdapter.hold();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_transpackage, null);
            hd.ID = (TextView)v.findViewById(R.id.package_id);
            hd.createTime = (TextView)v.findViewById(R.id.create_time);
            hd.sourceNode = (TextView)v.findViewById(R.id.tv_from);
            hd.targetNode = (TextView)v.findViewById(R.id.tv_to);
            //hd.status = (TextView)v.findViewById(R.id.st);

            v.setTag(hd);
        }else{
            hd = (PackageListAdapter.hold)v.getTag();
        }

        TransPackage es = getItem(position);
        hd.ID.setText(String.valueOf(es.getID()));
        hd.createTime.setText(DateFormat.format("MM月dd日 HH:mm",es.getCreateTime()));
        hd.sourceNode.setText(es.getSourcenode().getNodeName());
        hd.targetNode.setText(es.getTargetnode().getNodeName());





        String stText = "";
        /*switch(es.getStatus()){
            case TransPackage.STATUS.STATUS_CREATED:
                stText = "已创建";
                break;
            case TransPackage.STATUS.STATUS_TRANSPORT:
                stText = "运输中";
                break;
            case TransPackage.STATUS.STATUS_DELIVERIED:
                stText = "已抵达（销毁）";
                break;
        }

         */
        //hd.status.setText(stText);//状态暂时未显示
        return v;
    }

    @Override
    public List<TransPackage> getData() {
        return itemList;
    }

    @Override
    public void setData(List<TransPackage> data) {
        this.itemList = data;
    }

    private class hold{
        TextView sourceNode;
        TextView targetNode;
        TextView createTime;
        TextView ID;
       // TextView status;
    }
}
