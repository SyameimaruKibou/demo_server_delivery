package extrace.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import java.util.ArrayList;
import java.util.List;

import extrace.loader.LocationLoader;
import extrace.misc.model.ExpressHistory;
import extrace.misc.model.Posion;
import extrace.net.IDataAdapter;
import overlayutil.DrivingRouteOverlay;

public class RealTimeLocationActivity extends AppCompatActivity implements IDataAdapter<Posion> {

    private TextView number;
    private String stringNumber;
    private MapView mMapView;
    private BaiduMap mBaidumap;
    private Posion mItem;
    private LocationLoader locationLoader;
    private LatLng curPoint;
    private LatLng prePoint = null;
    private Overlay preMarker = null;
    private int updateTime = 0;
    private Handler handler = new Handler();
    private RealTimeLocationActivity context = this;
    private boolean isFirst = true;
    private Runnable runnable = new Runnable() {
        public void run() {
            this.update();
            handler.postDelayed(this, 1000 * 10);// 间隔10秒
        }
        void update() {
            locationLoader = new LocationLoader(context,context);
            locationLoader.GetLocation(stringNumber);
            updateTime++;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time_location);

        initMapView();

        Intent i =getIntent();
        number = (TextView)findViewById(R.id.box_numer);
        stringNumber = i.getStringExtra("number");
        number.setText(stringNumber);
        findViewById(R.id.goback).setOnClickListener(//返回键
                new View.OnClickListener() {
                    public void onClick(View view) {
                        finish();
                    }
                });

        handler.postDelayed(runnable, 0);
    }
    @Override
    public  Posion getData() {
        return mItem;
    }

    @Override
    public void setData( Posion data) {
        mItem=data;
    }

    @Override
    public void notifyDataSetChanged() {
        //一定要在此方法中进行数据处理
        //Toast.makeText(this, "notifyDataSetChanged!", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "第"+updateTime+"次更新："+"x="+mItem.getX()+",y="+mItem.getY(), Toast.LENGTH_SHORT).show();

        double tx = Double.parseDouble(String.format("%.6f", mItem.getX()));
        double ty = Double.parseDouble(String.format("%.6f", mItem.getY()));

        curPoint = new LatLng(mItem.getX(), mItem.getY());

        if(preMarker!=null){
            //绘制折线
            List<LatLng> tmpPoints = new ArrayList<LatLng>();
            tmpPoints.add(curPoint);
            tmpPoints.add(prePoint);

            OverlayOptions mOverlayOptions = new PolylineOptions()
                    .width(10)
                    .color(0xAAFF0000)
                    .points(tmpPoints);
            mBaidumap.addOverlay(mOverlayOptions);

            //删除前一标绘
            preMarker.remove();

        }
        prePoint = curPoint;

        //添加marker
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.history_es);
        OverlayOptions option = new MarkerOptions()
                .position(curPoint)
                .icon(bitmap)
                .zIndex(999);
        preMarker = mBaidumap.addOverlay(option);

        if(isFirst) {
            //转移视角中心
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(curPoint)
                    .zoom(20)
                    .build();
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            mBaidumap.setMapStatus(mMapStatusUpdate);
            isFirst = false;
        }
    }


    private void initMapView() {
        mMapView = (MapView) findViewById(R.id.real_loc_map_view);
        mBaidumap = mMapView.getMap();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable); //停止刷新
        super.onDestroy();
    }
}