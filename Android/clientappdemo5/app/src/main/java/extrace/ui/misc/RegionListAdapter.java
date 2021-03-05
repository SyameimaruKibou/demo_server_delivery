package extrace.ui.misc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import extrace.misc.model.CodeNamePair;
import extrace.net.IDataAdapter;
import extrace.ui.main.R;

public class RegionListAdapter extends ArrayAdapter<CodeNamePair> implements IDataAdapter<List<CodeNamePair>> {

	private List<CodeNamePair> itemList;
	private Context context;

	@SuppressLint("LongLogTag")
	public RegionListAdapter(List<CodeNamePair> itemList, Context ctx) {

		super(ctx, android.R.layout.simple_list_item_1, itemList);

		this.itemList = itemList;
		this.context = ctx;
	}

	public int getCount() {
		if (itemList != null)
			return itemList.size();
		return 0;
	}

	public CodeNamePair getItem(int position) {
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
		Log.i("onStatusNotify", ""+position+"88888888888888888888888888888888888888888888888888888888888888888888888888 " );

		if (v == null) {
			Log.i("LayoutInflater","LayoutInflater=0000000000000000000000000000000000000000000");
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.region_select_list, null);
		}
		CodeNamePair c = itemList.get(position);
		TextView text = (TextView) v.findViewById(R.id.region_name);
		text.setText(c.getName());
		text.setTag(c.getCode());
		return v;
	}

	@Override
	public List<CodeNamePair> getData() {
		return itemList;
	}

	@Override
	public void setData(List<CodeNamePair> data) {
		this.itemList = data;
	}
}