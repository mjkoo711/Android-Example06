package my.kmucs.com.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
* 위치 관리자를 이용해 내 위치를 확인하는 방법
 */

public class MainActivity extends Activity {
    //DecimalFormat form = new DecimalFormat("#.####");

    Double latitude;
    Double longitude;

    TextView latitude_txt, longitude_txt, date_txt, work_txt;
    Button save_btn, print_btn, move_btn, reset_btn;
    EditText work_et;
    Intent i1;

    MyDB mydb;
    SQLiteDatabase sqlite;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //버튼 이벤트 처리

        save_btn = (Button)findViewById(R.id.btn01);
        print_btn = (Button)findViewById(R.id.btn01_1);
        move_btn = (Button)findViewById(R.id.btn02);
        reset_btn = (Button)findViewById(R.id.btn03);
        latitude_txt = (TextView)findViewById(R.id.latitude);
        longitude_txt = (TextView)findViewById(R.id.longitude);
        date_txt = (TextView)findViewById(R.id.date);
        work_et =(EditText)findViewById(R.id.ed_work);
        work_txt = (TextView)findViewById(R.id.work);
        //인텐트 생성
        i1 = new Intent(getApplicationContext(), MapsActivity.class);

        //데이터베이스 연결
        mydb = new MyDB(this);



        checkDangerousPermissions();

        save_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //위치 정보 확인을 위해 정의한 메소드 호출
                saveInformation();
            }
        });

        print_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printInformation();
            }
        });

        move_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i1);
            }
        });
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latitude_txt.setText("위도");
                longitude_txt.setText("경도");
                date_txt.setText("날짜/시간");
                work_txt.setText("일어난 일");

                sqlite = mydb.getWritableDatabase();

                mydb.onUpgrade(sqlite, 1, 2);
                sqlite.close();

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
    //정보를 출력하는 함수
    private void printInformation(){
        //데이터베이스 열고
        sqlite = mydb.getReadableDatabase(); //읽기만 가능한 속성, 왜냐하면 조회는 수정이 필요없으니까

        String sql = "SELECT * FROM location"; //*은 전부를 뜻한다, 이테이블의 전부를 가져와라
        Cursor cursor = sqlite.rawQuery(sql, null); //인자가 두개가 들어가는데 두번째 인자는 커서가 null에 위치하고 있겠다는 뜻, 몇번째 raw위치에 커서를 위치할 것인가.

        String dateStr      = "날짜/시간\r\n";
        String latitudeStr  = "위도\r\n";
        String longitudeStr = "경도\r\n";
        String workStr      = "일어난 일\r\n";

        while(cursor.moveToNext()){//다음으로 갈수 있는 커서가 있다면
            dateStr     += cursor.getString(1) + "\r\n";
            latitudeStr += cursor.getString(2) + "\r\n\r\n";
            longitudeStr+= cursor.getString(3) + "\r\n\r\n";
            workStr     += cursor.getString(4) + "\r\n\r\n";
        }

        //텍스트화면에 출력
        date_txt.setText(dateStr);
        latitude_txt.setText(latitudeStr);
        longitude_txt.setText(longitudeStr);
        work_txt.setText(workStr);

        //cursor를 닫아줘야함
        cursor.close();

        //데이터베이스 닫아줌
        sqlite.close();
    }


    //정보를 저장하는 함수
    private void saveInformation(){
        //위치 관리자 객체 참조
        LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        //위치 정보를 받을 리스너 생성
        //GPSListener gpsListener = new GPSListener();
        //long minTime = 10000; //1000 = 1초, 1분
        //float minDistance = 10; //10미터

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
                String temp_workStr = work_et.getText().toString();

                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd" + "\nHH:mm:ss");
                String strNow = sdfNow.format(date);

//                date_txt.setText("날짜/시간\n" + strNow);
//                latitude_txt.setText("위도\n" + form.format(latitude));
//                longitude_txt.setText("경도\n" + form.format(longitude));
//                work_txt.setText("일어난 일\n" + temp_workStr);
                //db에 저장

                sqlite = mydb.getWritableDatabase(); //읽고쓰기가능한속성

                String dateStr = strNow;
                String latitudeStr = latitude.toString();
                String longitudeStr = longitude.toString();
                String workStr = work_et.getText().toString();

                String sql = "INSERT INTO location(date, latitude, longitude, work) VALUES('" + dateStr + "','" +latitudeStr+"', '" +longitudeStr+"', '" +workStr+"')";

                sqlite.execSQL(sql);
                sqlite.close();
                Toast.makeText(getApplicationContext(), "데이터가 저장되었습니다." , Toast.LENGTH_SHORT).show();
            }
        }catch (SecurityException ex){
            ex.printStackTrace();
        }
    }
    private class GPSListener implements LocationListener {
        //위치 정보가 확인될때 자동 호출되는 메소드
        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            String msg = "위도 : " + latitude + "\n경도 : " + longitude;
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
