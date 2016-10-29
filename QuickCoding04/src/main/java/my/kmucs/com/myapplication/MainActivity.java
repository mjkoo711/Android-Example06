package my.kmucs.com.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button btnShowLocation;
    private TextView txtLat;
    private TextView txtLon;

    //GpsTracker class
    private GpsInfo gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShowLocation = (Button)findViewById(R.id.btn_start);
        txtLat = (TextView)findViewById(R.id.Latitude);
        txtLon = (TextView)findViewById(R.id.Longitude);

        //Gps 정보를 보여주기 위한 이벤트 클래스 등록
        btnShowLocation.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                gps = new GpsInfo(MainActivity.this);
                //gps 사용유무 가져오기
                if(gps.isGetLocation()){
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    txtLat.setText(String.valueOf(latitude));
                    txtLon.setText(String.valueOf(longitude));

                    Toast.makeText(getApplicationContext(),"당신의 위치 - \n위도:" + latitude + "\n경도: " + longitude, Toast.LENGTH_LONG).show();
                }
                else{
                    //gps를 사용할 수 없으므로
                    gps.showSettingsAlert();
                }
            }
        });
    }
}
