package my.kmucs.com.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
* 위치 관리자를 이용해 내 위치를 확인하는 방법
 */

public class MainActivity extends Activity {
    private String dbName = "QuickCoding04.db"; //db명
    private int dbVersion = 1; //db 버젼
    private SQLiteDatabase db;

    Double latitude;
    Double longitude;

    TextView txt01, txt02, datetxt;
    Button save_btn, move_btn, reset_btn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //버튼 이벤트 처리

        save_btn = (Button)findViewById(R.id.btn01);
        move_btn = (Button)findViewById(R.id.btn02);
        reset_btn = (Button)findViewById(R.id.btn03);
        txt01 = (TextView)findViewById(R.id.txt01);
        txt02 = (TextView)findViewById(R.id.txt02);
        datetxt = (TextView)findViewById(R.id.datetxt);

        checkDangerousPermissions();

        save_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //위치 정보 확인을 위해 정의한 메소드 호출
                startLocationService();
            }
        });



    }
    private void checkDangerousPermissions(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for(int i=0 ; i< permissions.length; i++){
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if(permissionCheck == PackageManager.PERMISSION_DENIED){
                break;
            }
        }
        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,permissions[0])){
                Toast.makeText(this,"권한 설명 필요함.", Toast.LENGTH_LONG).show();
            }else{
                ActivityCompat.requestPermissions(this,permissions,1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == 1){
            for(int i=0 ; i<permissions.length ; i++){
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, permissions[i] + "권한이 승인됨.", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(this,permissions[i] + "권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    //위치 정보 확인을 위해 정의한 메소드
    private void startLocationService(){
        //위치 관리자 객체 참조
        LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        //위치 정보를 받을 리스너 생성
        GPSListener gpsListener = new GPSListener();
        long minTime = 10000; //1000 = 1초, 1분
        float minDistance = 10; //10미터

        try{
            //gps를 이용한 위치 요청(주기적으로)
            //manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance,gpsListener);
            //네트워크를 이용한 위치 요청(주기적으로)
            //manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, gpsListener);
            //위치 확인이 안되는 경우에도 최근에 확인된 위치 정보 먼저 확인
            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(lastLocation != null){
                Double latitude = lastLocation.getLatitude();
                Double longitude = lastLocation.getLongitude();

                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String strNow = sdfNow.format(date);

                datetxt.setText("마지막으로 최적화된 날짜 : " + strNow);

                txt01.setText("위도 : " + latitude);
                txt02.setText("경도 : " + longitude);
                Toast.makeText(getApplicationContext(), "Last Known Location : " + "Latitude : " + latitude + "\nLongitude:" + longitude, Toast.LENGTH_LONG).show();
            }
        }catch (SecurityException ex){
            ex.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), "위치 확인이 시작되었습니다. 로그를 확인하세요.", Toast.LENGTH_SHORT).show();
    }
    private class GPSListener implements LocationListener {
        //위치 정보가 확인될때 자동 호출되는 메소드
        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String strNow = sdfNow.format(date);

            datetxt.setText("마지막으로 최적화된 날짜 : " + strNow);

            txt01.setText("위도 : " + latitude);
            txt02.setText("경도 : " + longitude);


            String msg = "Latitude : " + latitude + "\nLongitude : " + longitude;
            Log.i("GPSListener", msg);

            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();


        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

}
