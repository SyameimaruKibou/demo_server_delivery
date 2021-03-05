package extrace.ui.misc;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.bikenavi.BikeNavigateHelper;
import com.baidu.mapapi.bikenavi.adapter.IBEngineInitListener;
import com.baidu.mapapi.bikenavi.adapter.IBRoutePlanListener;
import com.baidu.mapapi.bikenavi.model.BikeRoutePlanError;
import com.baidu.mapapi.bikenavi.params.BikeNaviLaunchParam;
import com.baidu.mapapi.bikenavi.params.BikeRouteNodeInfo;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;

import extrace.misc.model.TransNode;
import extrace.ui.main.ExTraceApplication;
import extrace.ui.main.R;

public class MapActivity extends Activity {
    private ExTraceApplication app;

    private final static String TAG = MapActivity.class.getSimpleName();

    private MapView mMapView;
    private BaiduMap mBaiduMap;

    /*导航起终点Marker，可拖动改变起终点的坐标*/
    private Marker mStartMarker;
    private Marker mEndMarker;

    private LatLng startPt;
    private LatLng endPt;

    TransNode beginPoint;
    TransNode endPoint;

    //定位客户端
    private LocationClient mLocationClient;
    //是否是第一次定位
    private boolean isFirstLoc = true;

    private BikeNaviLaunchParam bikeParam;

    private double x;
    private double y;

    private static boolean isPermissionRequested = false;

    private BitmapDescriptor bdStart = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_start);
    private BitmapDescriptor bdEnd = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_end);

    //设置定位监听器
    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);

            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            //第一次定位需要更新下地图显示状态
            //if (isFirstLoc) {
                isFirstLoc = false;
                MapStatus.Builder builder = new MapStatus.Builder()
                        .target(ll)//地图缩放中心点
                        .zoom(18f);//缩放倍数 百度地图支持缩放21级 部分特殊图层为20级
                //改变地图状态
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            //}
            x = location.getLatitude();
            y = location.getLongitude();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        requestPermission();
        mMapView = (MapView) findViewById(R.id.mapview);
        initMapStatus();

        app = (ExTraceApplication) getApplication();
        beginPoint = app.getBegnode();
        endPoint = app.getEndnode();
        Log.i("喂喂喂喂I喂喂喂喂喂喂喂喂喂喂喂",""+beginPoint.getX()+"--"+beginPoint.getY()+"=="+endPoint.getX()+"--"+endPoint.getY());

        Toast.makeText(MapActivity.this, "ttttt！"+beginPoint.getX()+"==="+endPoint.getX(), Toast.LENGTH_SHORT).show();


        /*
        beginPoint = new TransNode();
        endPoint = new TransNode();

        beginPoint.setX((float) 1.0);
        beginPoint.setY((float) 1.0);
        endPoint.setX((float) 34.029714);
        endPoint.setY((float) 112.547287);
*/

        /*骑行导航入口*/
        Button bikeBtn = (Button) findViewById(R.id.btn_bikenavi);
        bikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(beginPoint==null||endPoint==null){
                    Toast.makeText(MapActivity.this, "当前没有包裹要运送！", Toast.LENGTH_SHORT).show();
                }
                else {
                    startBikeNavi();
                }
            }
        });



        /* 初始化起终点Marker */
        //initOverlay();
    }

    /**
     * 初始化地图状态
     */
    private void initMapStatus(){
        mBaiduMap = mMapView.getMap();
        //开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        //定位初始化
        mLocationClient = new LocationClient(this);

        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(0);

        //设置locationClientOption
        mLocationClient.setLocOption(option);

        //注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        //开启地图定位图层
        mLocationClient.start();
    }


    /**
     * 开始骑行导航
     */
    private void startBikeNavi() {
        Log.d(TAG, "startBikeNavi");
        try {
            BikeNavigateHelper.getInstance().initNaviEngine(this, new IBEngineInitListener() {
                @Override
                public void engineInitSuccess() {
                    Log.d(TAG, "BikeNavi engineInitSuccess");
                    routePlanWithBikeParam();
                }

                @Override
                public void engineInitFail() {
                    Log.d(TAG, "BikeNavi engineInitFail");
                    BikeNavigateHelper.getInstance().unInitNaviEngine();
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "startBikeNavi Exception");
            e.printStackTrace();
        }
    }

    /**
     * 发起骑行导航算路
     */
    private void routePlanWithBikeParam() {
        //重要！！！将小数位数控制在6位，否则执行算路时会出现解析错误
        x=Double.parseDouble(String.format("%.6f", x));
        y=Double.parseDouble(String.format("%.6f", y));

        double eX = Double.parseDouble(String.format("%.6f", (double)endPoint.getX()));
        double eY = Double.parseDouble(String.format("%.6f", (double)endPoint.getY()));

        startPt = new LatLng(x, y);
        endPt = new LatLng(eX,eY);

        /*构造导航起终点参数对象*/
        BikeRouteNodeInfo bikeStartNode = new BikeRouteNodeInfo();
        bikeStartNode.setLocation(startPt);
        BikeRouteNodeInfo bikeEndNode = new BikeRouteNodeInfo();
        bikeEndNode.setLocation(endPt);
        bikeParam = new BikeNaviLaunchParam().startNodeInfo(bikeStartNode).endNodeInfo(bikeEndNode);
        //发起算路
        BikeNavigateHelper.getInstance().routePlanWithRouteNode(bikeParam, new IBRoutePlanListener() {
            @Override
            public void onRoutePlanStart() {
                Log.d(TAG, "BikeNavi onRoutePlanStart");
            }

            @Override
            public void onRoutePlanSuccess() {
                Log.d(TAG, "BikeNavi onRoutePlanSuccess");
                Intent intent = new Intent();
                intent.setClass(MapActivity.this, BNaviGuideActivity.class);
                startActivity(intent);
            }

            @Override
            public void onRoutePlanFail(BikeRoutePlanError error) {
                Log.d(TAG, "BikeNavi onRoutePlanFail");
            }

        });
    }

    void refreshStartEndPoint(){
        //设置导航状态为true
        //isRequestingNavi = true;
        //mLocationClient.stop();
        mLocationClient.start();
    }

    /**
     * Android6.0之后需要动态申请权限
     */
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23 && !isPermissionRequested) {

            isPermissionRequested = true;

            ArrayList<String> permissionsList = new ArrayList<>();

            String[] permissions = {
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.INTERNET,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.MODIFY_AUDIO_SETTINGS,
                    Manifest.permission.WRITE_SETTINGS,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    Manifest.permission.CHANGE_WIFI_MULTICAST_STATE


            };

            for (String perm : permissions) {
                if (PackageManager.PERMISSION_GRANTED != checkSelfPermission(perm)) {
                    permissionsList.add(perm);
                    // 进入到这里代表没有权限.
                }
            }

            if (permissionsList.isEmpty()) {
                return;
            } else {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), 0);
            }
        }
    }

    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        bdStart.recycle();
        bdEnd.recycle();
        super.onDestroy();
    }
}
