package com.yiwen.lbstest;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public  LocationClient mLocationClient;
    private TextView       mTextView;
    private MapView        mapView;
    private BaiduMap baiduMap;
    private boolean isFirstLocation =true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MylocationListener());
        mTextView = (TextView) findViewById(R.id.text_view);
        mapView = (MapView) findViewById(R.id.bdmap);
        baiduMap=mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        List<String> permissionlist = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(android.Manifest.permission.READ_PHONE_STATE);
        }
        if (!permissionlist.isEmpty()) {
            String[] perssions = permissionlist.toArray(new String[permissionlist.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, perssions, 1);
        } else {
            requestionLotion();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_DENIED) {
                            Toast.makeText(MainActivity.this, "请同意所申请权限", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestionLotion();
                } else {
                    Toast.makeText(MainActivity.this, "somthing hanppened", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    private void requestionLotion() {
        initLotion();
        mLocationClient.start();
    }

    private void initLotion() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        mLocationClient.setLocOption(option);
    }

    public class MylocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            StringBuilder currentPosion = new StringBuilder();
            currentPosion.append("纬度：").append(location.getLatitude()).append("\n");
            currentPosion.append("经度：").append(location.getLongitude()).append("\n");
            currentPosion.append("经度：").append(location.getLongitude()).append("\n");
            currentPosion.append("国家：").append(location.getCountry()).append("\n");
            currentPosion.append("省：").append(location.getProvince()).append("\n");
            currentPosion.append("市：").append(location.getCity()).append("\n");
            currentPosion.append("区：").append(location.getDirection()).append("\n");
            currentPosion.append("街道：").append(location.getStreet()).append("\n");
            currentPosion.append("定位方式：");
            Log.d("currentPosion", "onReceiveLocation: " + currentPosion);
//            if (location.getLocType() == BDLocation.TypeGpsLocation) {
//                currentPosion.append("GPS");
//            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
//                currentPosion.append("network");
//            }
            if (location.getLocType() == BDLocation.TypeGpsLocation||
                    location.getLocType() == BDLocation.TypeNetWorkLocation){
                navigateTo(location);
            }
            mTextView.setText(currentPosion);
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }

    }

    private void navigateTo(BDLocation location) {
        if (isFirstLocation){
            LatLng ll=new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate update= MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update=MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocation=false;
        }
        MyLocationData.Builder builder=new MyLocationData.Builder();
        builder.latitude(location.getLatitude());
        builder.longitude(location.getLongitude());
        MyLocationData data=builder.build();
        baiduMap.setMyLocationData(data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
}
