package extrace.ui.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import extrace.misc.model.TempCustomer;
import extrace.ui.main.R;

public class AddressAdapter extends BaseAdapter {

    private List<TempCustomer> Datas;
    private Context mContext;
    private onItemEdtListener mOnItemEdtListener;

    public AddressAdapter(List<TempCustomer> datas, Context mContext) {
        Datas = datas;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return Datas.size();
    }

    @Override
    public Object getItem(int i) {
        return Datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        AddressAdapter.Holder hd = null;
        if(convertView==null){
            hd = new AddressAdapter.Holder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_address,parent,false);
            hd.name = (TextView)convertView.findViewById(R.id.add_name);
            hd.tel = (TextView)convertView.findViewById(R.id.add_tele);
            hd.addr = (TextView)convertView.findViewById(R.id.add_add);
            hd.edt = (ImageView) convertView.findViewById(R.id.img_edt);
            convertView.setTag(hd);
        }else{
            hd = (Holder) convertView.getTag();
        }

        hd.name.setText(Datas.get(position).getName());
        hd.tel.setText(Datas.get(position).getTelCode());
        hd.addr.setText(Datas.get(position).getRegionString()+Datas.get(position).getAddress());//赋值
        hd.edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               mOnItemEdtListener.onEdtClick(position);
            }
        });

//        此处需要返回view 不能是view中某一个
        return convertView;
    }

    public interface onItemEdtListener {
        void onEdtClick(int i);
    }


    public void setOnItemEdtClickListener(onItemEdtListener mOnItemEdtListener) {
        this.mOnItemEdtListener = mOnItemEdtListener;
    }

    class  Holder{
        TextView name;
        TextView tel;
        TextView addr;
        ImageView edt;
    }
}
