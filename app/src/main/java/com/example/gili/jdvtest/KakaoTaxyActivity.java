package com.example.gili.jdvtest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.gili.comutil.CommonUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class KakaoTaxyActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraIdleListener {

    TextView txtLocation;


    GoogleApiClient googleApiClient;
    FusedLocationProviderApi fusedLocationProviderApi;
    GoogleMap map;
    Location location;

    Marker marker;

    String resultAddress;
    double resultLat;
    double resultLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_taxy);

        txtLocation = (TextView)findViewById(R.id.txtLocation);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        googleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(googleApiClient.isConnected()){
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            location = fusedLocationProviderApi.getLastLocation(googleApiClient);
            moveMap();
        }else{
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},100);
        }
   }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100 && grantResults.length>0){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                location = fusedLocationProviderApi.getLastLocation(googleApiClient);
                moveMap();
            }else
            {
                CommonUtils.showLongMessage(KakaoTaxyActivity.this, "no permission");
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        CommonUtils.showLongMessage(KakaoTaxyActivity.this, "access location fail");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnCameraIdleListener(this);
        map.setOnCameraMoveListener(this);
        moveMap();
    }

    private void moveMap(){
        if(location != null && map != null){
            LatLng gpsLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraPosition position = new CameraPosition.Builder().target(gpsLatLng).zoom(16f).build();
            map.moveCamera(CameraUpdateFactory.newCameraPosition(position));

            if(marker != null) marker.remove();
            marker = map.addMarker(new MarkerOptions().position(gpsLatLng).title("Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_start)));

            MyGeocoderThread thred = new MyGeocoderThread(gpsLatLng);
            thred.start();
        }
    }

    @Override
    public void onCameraIdle() {
        MyGeocoderThread thred = new MyGeocoderThread(map.getCameraPosition().target);
        thred.start();
        resultLat = map.getCameraPosition().target.latitude;
        resultLng = map.getCameraPosition().target.longitude;
    }

    @Override
    public void onCameraMove() {
        marker.setPosition(map.getCameraPosition().target);
    }

    class MyGeocoderThread extends Thread{
        LatLng latLng;
        public MyGeocoderThread(LatLng latLng){
            this.latLng = latLng;
        }

        @Override
        public void run() {
            Geocoder geocoder = new Geocoder(KakaoTaxyActivity.this);
            List<Address> lstAddress = null;
            String addressText = "";
            try
            {
                lstAddress = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                Thread.sleep(500);
                if(lstAddress != null && lstAddress.size()>0){
                    Address address = lstAddress.get(0);
                    addressText = address.getAdminArea()+" "+(address.getMaxAddressLineIndex()>0?address.getAddressLine(0):address.getLocality())+" ";
                    String txt = address.getSubLocality();
                    if(txt != null){
                        addressText += txt + " ";
                    }
                    addressText += address.getThoroughfare()+ " " + address.getSubThoroughfare();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            Message message = new Message();
            message.what = 100;
            message.obj = addressText;
            handler.sendMessage(message);

            resultAddress=addressText;
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 100:
                {
                    txtLocation.setText((String)msg.obj);
                }
            }
        }
    };
}
