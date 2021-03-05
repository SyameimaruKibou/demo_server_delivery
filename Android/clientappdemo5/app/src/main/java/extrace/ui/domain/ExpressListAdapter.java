package extrace.ui.domain;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import extrace.misc.model.ExpressSheet;
import extrace.net.IDataAdapter;
import extrace.ui.main.R;

public class ExpressListAdapter extends ArrayAdapter<ExpressSheet> implements IDataAdapter<List<ExpressSheet>>{
	
	private List<ExpressSheet> itemList;
	private Context context;

	public ExpressListAdapter(List<ExpressSheet> itemList, Context ctx) {
		super(ctx, R.layout.express_list_item, itemList);
		
		this.itemList = itemList;
		this.context = ctx;

	}
	
	public int getCount() {
		if (itemList != null)
			return itemList.size();
		return 0;
	}

	public ExpressSheet getItem(int position) {
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
		if(v==null){
			hd = new hold();
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.express_list_item, null);
			hd.boxnumber = (TextView)v.findViewById(R.id.tv_box_number);
			hd.socity = (TextView)v.findViewById(R.id.socity);
			hd.tarcity = (TextView)v.findViewById(R.id.tarcity);
			hd.soname = (TextView)v.findViewById(R.id.sendname);
			hd.tarname=(TextView)v.findViewById(R.id.rcvname);
			hd.status = (TextView)v.findViewById(R.id.tv_state);
			v.setTag(hd);
		}else{
			hd = (hold)v.getTag();
		}

		ExpressSheet es = getItem(position);
		//regionListLoader = new RegionListLoader(regionListAdapter,this.context);
		//regionListLoader.LoadCityList(es.getRecieverregcode());

		hd.soname.setText(es.getSendername());
		hd.tarname.setText(es.getReceivername());
		hd.boxnumber.setText(es.getID());
		hd.socity.setText(es.getSenderregname().toCharArray(),0,3);
		hd.tarcity.setText(es.getReceiverregname().toCharArray(),0,3);

		String stText = "";
		switch(es.getStatus()){
		case -1:
			stText = "建立中";
			break;
		case 0:
			//stText = "运输中";
			//break;
		case 1:
			stText = "运输中";
			break;
		case 2:
			stText = "已签收";
			break;
		}
		hd.status.setText(stText);
		return v;		
	}

	@Override
	public List<ExpressSheet> getData() {
		return itemList;
	}

	@Override
	public void setData(List<ExpressSheet> data) {
		this.itemList = data;
	}
	
	private class hold{
		TextView boxnumber;
		TextView socity;
		TextView tarcity;
		TextView soname;
		TextView tarname;
		TextView status;
	}
}