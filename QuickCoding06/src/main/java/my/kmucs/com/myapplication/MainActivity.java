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
    TextView xGravity, yGravity, zGravity;
    TextView xAccelerometer, yAccelerometer, zAccelerometer;
    TextView xLinearAccel, yLinearAccel, zLinearAccel;
    TextView xGyro, yGyro, zGyro;
    Sensor sensor_gravity, sensor_accelerometer, sensor_linear, sensor_gyroscope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        xGravity = (TextView)findViewById(R.id.xGravity);
        yGravity = (TextView)findViewById(R.id.yGravity);
        zGravity = (TextView)findViewById(R.id.zGravity);
        xAccelerometer = (TextView)findViewById(R.id.xAccelero);
        yAccelerometer = (TextView)findViewById(R.id.yAccelero);
        zAccelerometer = (TextView)findViewById(R.id.zAccelero);
        xLinearAccel = (TextView)findViewById(R.id.xLinearAccel);
        yLinearAccel = (TextView)findViewById(R.id.yLinearAccel);
        zLinearAccel = (TextView)findViewById(R.id.zLinearAccel);
        xGyro = (TextView)findViewById(R.id.xGyro);
        yGyro = (TextView)findViewById(R.id.yGyro);
        zGyro = (TextView)findViewById(R.id.zGyro);

        sensor_gravity = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensor_accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensor_linear = sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensor_gyroscope = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

    }

    @Override
    protected void onResume() {
        super.onResume();

        //센서 리스너에 등록
        sm.registerListener(this, sensor_gravity, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, sensor_accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, sensor_linear, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, sensor_gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //센서리스너해제
        sm.unregisterListener(this);
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        switch(event.sensor.getType()){
            case Sensor.TYPE_GRAVITY:
                xGravity.setText("X : " + event.values[0]);
                yGravity.setText("Y : " + event.values[1]);
                zGravity.setText("Z : " + event.values[2]);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                xAccelerometer.setText("X : " + event.values[0]);
                yAccelerometer.setText("Y : " + event.values[1]);
                zAccelerometer.setText("Z : " + event.values[2]);
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                xLinearAccel.setText("X : " + event.values[0]);
                yLinearAccel.setText("Y : " + event.values[1]);
                zLinearAccel.setText("Z : " + event.values[2]);
                break;
            case Sensor.TYPE_GYROSCOPE:
                xGyro.setText("X : " + event.values[0]);
                yGyro.setText("Y : " + event.values[1]);
                zGyro.setText("Z : " + event.values[2]);
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

