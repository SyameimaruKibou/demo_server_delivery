package extrace.ui.domain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import extrace.loader.ExpressHistoryLoader;
import extrace.misc.model.ExpressHistory;
import extrace.net.IDataAdapter;
import extrace.ui.main.LoginActivity;
import extrace.ui.main.R;
import extrace.ui.main.RealTimeLocationActivity;
import extrace.ui.main.RegiMesActivity;
import overlayutil.DrivingRouteOverlay;

public class SearchBoxActivity extends AppCompatActivity  implements IDataAdapter<List<ExpressHistory>> {

    private ListView lvHistory;
    private List<ExpressHistory> traceList = new ArrayList<>(1);
    private ExpressHistoryLoader mloader;
    private TextView number;
    private String stringNumber;
    private HistoryAdapter adapter;
    private MapView mMapView;
    private BaiduMap mBaidumap;
    private RelativeLayout relativeLayout;
    private double[][] historyPoint;
    private Float[] curPosition;
    private RoutePlanSearch mSearch;
    private int listSize;
    private List<PlanNode> wayPoints= new ArrayList<>();
    private int epsState = 2;//默认为未加载完成状态


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_box);

        iniMapView();//初始化地图控件

        Intent i =getIntent();
        lvHistory = (ListView) findViewById(R.id.lvHistory);
        setListViewHeightBasedOnChildren(lvHistory);

        findViewById(R.id.goback).setOnClickListener(//返回键
                new View.OnClickListener() {
                    public void onClick(View view) {
                        finish();
                    }
                });
        mloader=new ExpressHistoryLoader(SearchBoxActivity.this,SearchBoxActivity.this);
        number = (TextView)findViewById(R.id.box_numer);
        stringNumber = i.getStringExtra("number");
        number.setText(stringNumber);
        relativeLayout = (RelativeLayout)findViewById(R.id.rl_location) ;
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(epsState==2)
                    Toast.makeText(SearchBoxActivity.this, "请等待本界面加载完成", Toast.LENGTH_SHORT).show();
                else if(epsState==1)
                    Toast.makeText(SearchBoxActivity.this, "该快递已签收，不支持实时位置", Toast.LENGTH_SHORT).show();
                else {
                    Intent i = new Intent();
                    i.putExtra("number", stringNumber);
                    i.setClass(SearchBoxActivity.this, RealTimeLocationActivity.class);
                    startActivity(i);
                }
            }
        });
        mloader.LoadHistorybyExpressid(stringNumber);//接收传递的快递单号

    }

    private void iniMapView() {
        mMapView = (MapView) findViewById(R.id.check_map_view);
        mBaidumap = mMapView.getMap();

        mSearch = RoutePlanSearch.newInstance();
        OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
                //创建DrivingRouteOverlay实例
                DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaidumap);
                if (drivingRouteResult.getRouteLines().size() > 0) {
                    //获取路径规划数据,(以返回的第一条路线为例）
                    //为DrivingRouteOverlay实例设置数据
                    overlay.setData(drivingRouteResult.getRouteLines().get(0));
                    //在地图上绘制DrivingRouteOverlay
                    overlay.addToMap();
                    overlay.zoomToSpan();
                }
            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        };
        mSearch.setOnGetRoutePlanResultListener(listener);

    }

    @Override
    public  List<ExpressHistory> getData() {
        return traceList;
    }

    @Override
    public void setData( List<ExpressHistory> data) {
        traceList=data;
    }

    @Override
    public void notifyDataSetChanged() {
        //一定要在此方法中进行数据处理
/*
        {//测试用数据
            historyPoint = new double[4][2];
            historyPoint[0][0] = 39.913385;
            historyPoint[0][1] = 115.224033;
            historyPoint[1][0] = 39.987282;
            historyPoint[1][1] = 116.289573;
            historyPoint[2][0] = 41.017791;
            historyPoint[2][1] = 116.497693;
            historyPoint[3][0] = 39.916042;
            historyPoint[3][1] = 117.487344;

            listSize = 4;
        }
*/
        //  正式数据
        if(traceList==null){
            return;
        }
        if(traceList.size()==0){
            Toast.makeText(this, "快递不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        listSize = traceList.size();
        historyPoint = new double[listSize][2];
        for(int i = 0;i<traceList.size();i++){

            ExpressHistory tmp = traceList.get(i);

            if(i==listSize-1&&(tmp.getActId()==3||tmp.getActId()==5)) {
                historyPoint[i][0] = (double) tmp.getUser().getX();
                historyPoint[i][1] = (double) tmp.getUser().getY();
            }
            else if(tmp.getActId()==6){
                historyPoint[i][0] = (double) tmp.getEps().getCurrentnode().getX();
                historyPoint[i][1] = (double) tmp.getEps().getCurrentnode().getY();
            }
            else{
                historyPoint[i][0] = (double) tmp.getSourcenode().getX();
                historyPoint[i][1] = (double) tmp.getSourcenode().getY();
            }

/*
if(traceList.get(i).getActId()!=6) {
    historyPoint[i][0] = (double) traceList.get(i).getSourcenode().getX();
    historyPoint[i][1] = (double) traceList.get(i).getSourcenode().getY();

 */


        }
        Collections.reverse(traceList);

        //Toast.makeText(this, traceList.get(0).getActTime().toString()+traceList.get(2).getActTime().toString(), Toast.LENGTH_SHORT).show();
        //处理文字
        adapter= new HistoryAdapter(this,traceList);


        //处理地图
        double sx=Double.parseDouble(String.format("%.6f", historyPoint[0][0]));
        double sy=Double.parseDouble(String.format("%.6f", historyPoint[0][1]));
        double ex=Double.parseDouble(String.format("%.6f", historyPoint[historyPoint.length-1][0]));
        double ey=Double.parseDouble(String.format("%.6f", historyPoint[historyPoint.length-1][1]));

        PlanNode stNode = PlanNode.withLocation(new LatLng(sx,sy));
        PlanNode enNode = PlanNode.withLocation(new LatLng(ex,ey));

        double preX = 0;
        double preY = 0;

        for(int i=historyPoint.length-1;i>=0;i--){
            double tx=Double.parseDouble(String.format("%.6f", historyPoint[i][0]));
            double ty=Double.parseDouble(String.format("%.6f", historyPoint[i][1]));
            if(preX==tx&&preY==ty)
                continue;
            preX = tx;
            preY = ty;
            PlanNode tNode = PlanNode.withLocation(new LatLng(tx,ty));
            wayPoints.add(tNode);
        }

        if(wayPoints.size()>1)
            mSearch.drivingSearch((new DrivingRoutePlanOption())
                    .from(stNode)
                    .to(enNode)
                    .passBy(wayPoints));

        LatLng curPoint = new LatLng(ex, ey);
//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.history_es);
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(curPoint)
                .icon(bitmap)
                .zIndex(999);
//在地图上添加Marker，并显示
        mBaidumap.addOverlay(option);
        if(wayPoints.size()<=1){
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(curPoint)
                    .zoom(13)
                    .build();
//定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//改变地图状态
            mBaidumap.setMapStatus(mMapStatusUpdate);
        }

/*
        if(traceList==null){
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "notnull", Toast.LENGTH_SHORT).show();
        }

 */

        lvHistory.setAdapter(adapter);

        if(traceList.get(0).getActId()==6)
            epsState = 1;
        else
            epsState = 0;
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mSearch.destroy();
        mMapView.onDestroy();
    }
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}