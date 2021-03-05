package extrace.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import extrace.misc.model.UserInfo;
import extrace.ui.domain.SearchBoxActivity;
import extrace.ui.util.NearByFragmentController;
import zxing.util.CaptureActivity;

import static android.app.Activity.RESULT_CANCELED;

public class CheckFragment extends Fragment {


    private EditText searchnumber;
    private ImageView scan;
    private TextView mysend,myget;
    private ExTraceApplication app;
    private Button search;
    private String result="";
    private MyRcvboxFragment myRcvboxFragment;
    private MySendboxFragment mySendboxFragment;
    private UserInfo mItem;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private NearByFragmentController controller;
    private RadioGroup rg_tab;

    public static CheckFragment newInstance() {
        CheckFragment fragment = new CheckFragment();
        Bundle args = new Bundle();
        //args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public CheckFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check, container, false);

        controller = NearByFragmentController.getInstance(this, R.id.frame_sub);
        controller.showFragment(0);
        searchnumber=(EditText)view.findViewById(R.id.search_number);
        search=(Button)view.findViewById(R.id.btn_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchNumber();
            }
        });
        scan=(ImageView)view.findViewById(R.id.userinfo_tv_back);
        scan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ScanNumber();
                    }
                });
        rg_tab = (RadioGroup) view.findViewById(R.id.rg_tab);
        rg_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tv_myrcv:
                        controller.showFragment(0);
                        break;
                    case R.id.tv_myget:
                        controller.showFragment(1);
                        break;
                    default:
                        break;
                }
            }
        });
        return view;
    }

    void SearchNumber()//按照快递单号查找
    {
        if(searchnumber.getText()!=null&&searchnumber.getText().length()==32){
            //Toast.makeText(this.getActivity(), "快件号为"+searchnumber.getText(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(),SearchBoxActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("number",searchnumber.getText().toString());
            intent.putExtras(bundle);
            getActivity().startActivity(intent);
        }
        else if(searchnumber.getText()==null){
            Toast.makeText(getActivity(), "请输入快件号！", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getActivity(), "请输入正确的快件号！", Toast.LENGTH_SHORT).show();
            searchnumber.setText("");
        }
    }

    void ScanNumber()
    {
        Intent intent = new Intent();
        intent.setClass(this.getActivity(), CaptureActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0){
            if(resultCode == RESULT_CANCELED)
                return;
            result = data.getExtras().getString("BarCode");//得到新Activity 关闭后返回的数据
            searchnumber.setText(result);
        }
    }

}