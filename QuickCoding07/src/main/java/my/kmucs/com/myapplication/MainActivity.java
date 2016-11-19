package my.kmucs.com.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    SensorManager sm;
    TextView txt01;
    Sensor sensor_accelerometer;
    double acceleration, gravity;
    int dir_UP, dir_DOWN;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dir_UP = 0;
        dir_DOWN = 0;
        count = 0;
        gravity = 9.81;

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        txt01 = (TextView)findViewById(R.id.txt01);

        sensor_accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //센서 리스너에 등록
        sm.registerListener(this, sensor_accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //센서리스너해제
        sm.unregisterListener(this);
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            acceleration =  Math.sqrt(x * x + y * y + z * z);
        }

        if(acceleration - gravity > 5){
            dir_UP = 1;
        }

        if(dir_UP == 1 && gravity - acceleration > 5){
            dir_DOWN = 1;
        }

        if(dir_DOWN == 1){
            count++;
            txt01.setText("You Walked " + count + "Steps!!");

            dir_UP = 0;
            dir_DOWN = 0;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

