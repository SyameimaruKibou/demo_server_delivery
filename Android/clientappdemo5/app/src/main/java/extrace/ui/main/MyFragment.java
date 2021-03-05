package extrace.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import extrace.misc.model.Customer;
import extrace.misc.model.TempCustomer;
import extrace.ui.domain.AddressActivity;

public class MyFragment extends Fragment {

    private ExTraceApplication app;
    private RelativeLayout address;
    private RelativeLayout call;
    private ImageView imguser;
    private TextView username;
    Customer tempCustomer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_my, container, false);
        app = (ExTraceApplication) getActivity().getApplication();
        tempCustomer = app.getLoginCustomer();
        address = (RelativeLayout)view.findViewById(R.id.user_address);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AddressActivity.class);
                startActivity(intent);
            }
        });
        imguser = (ImageView)view.findViewById(R.id.user_iv_head);
        imguser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), UserInfoEditActivity.class);
                startActivity(intent);
            }
        });
        username = (TextView)view.findViewById(R.id.user_tv_name);
        username.setText(tempCustomer.getNickname());
        call = (RelativeLayout)view.findViewById(R.id.user_information);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + "15881163389");
                intent.setData(data);
                startActivity(intent);
            }
        });
        return view;
    }
}
