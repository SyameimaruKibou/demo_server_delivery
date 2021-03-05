package extrace.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.List;

import extrace.loader.ExpressListLoader;
import extrace.misc.model.Customer;
import extrace.misc.model.ExpressSheet;
import extrace.misc.model.UserInfo;
import extrace.net.IDataAdapter;
import extrace.ui.domain.ExpressListAdapter;
import extrace.ui.domain.SearchBoxActivity;

public class MySendboxFragment extends Fragment implements IDataAdapter<List<ExpressSheet>> {
    private ExpressListLoader mloader;
    private ExpressListAdapter adapter;
    private List<ExpressSheet> mItem;
   // private  List<tmp>  tmpList;
    private ListView list;
    private ExTraceApplication app;
    private Customer customer;

    public static MySendboxFragment newInstance() {
        MySendboxFragment fragment = new MySendboxFragment();
        Bundle args = new Bundle();
        //args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MySendboxFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mybox_send, container, false);
        app=(ExTraceApplication) getActivity().getApplication();
        customer = app.getLoginCustomer();
        //Toast.makeText(this.getActivity(), "MySendboxFragment", Toast.LENGTH_SHORT).show();
        mloader=new ExpressListLoader(this,getActivity());
        mloader.LoadHistorybySendTele(customer.getTelCode());
        list=(ListView) view.findViewById(R.id.myboxsend);
        list.setEmptyView(view.findViewById(R.id.layout_empty));
        return view;
    }

    @Override
    public List<ExpressSheet> getData() {
        return mItem;
    }

    @Override
    public void setData( List<ExpressSheet>  data) { mItem=data;}


    @Override
    public void notifyDataSetChanged() {
        //一定要在此方法中进行数据处理
        adapter = new ExpressListAdapter(mItem,getContext());
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SearchBoxActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("number",mItem.get(position).getID());
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
    }
}
