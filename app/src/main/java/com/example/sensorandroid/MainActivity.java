package com.example.sensorandroid;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Sensor proximitySensor;
    Sensor stepSensor;
    SensorManager sm = null;
    TextView textView1 = null;
    TextView textView2 = null;
    List list;
    boolean flag;
    int counterStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get sensormanager instance
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);

        textView1 = (TextView)findViewById(R.id.textView1);
        textView2 = (TextView)findViewById(R.id.textView2);
        list = sm.getSensorList(Sensor.TYPE_ALL);
        proximitySensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        stepSensor = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (list.size()>0) {
            sm.registerListener(sel, (Sensor) list.get(0), SensorManager.SENSOR_DELAY_NORMAL);
            sm.registerListener(sel,  proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
            //sm.registerListener(sel, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }else{
            Toast.makeText(getBaseContext(), "Error No Accelerometer.", Toast.LENGTH_LONG).show();
        }
    }
    SensorEventListener sel = new SensorEventListener() {
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;

            /*if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
                counterStep++;
               textView2.setText("Your Step : "+counterStep);
            }*/

            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (values[0] == 0){
                    View view1 = findViewById(R.id.view1);
                    view1.setBackgroundColor(Color.RED);
                    flag = true;
                }else{
                    flag = false;
                }
            }else if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER && flag == false) {
                textView1.setText("x: " + values[0] + "\ny: " + values[1] + "\nz: " + values[2]);
                 if (values[0] > 0 && values[0] < 1) {
                     View view1 = findViewById(R.id.view1);
                     view1.setBackgroundColor(Color.GREEN);
                 } else if (values[0] < 0) {
                     View view1 = findViewById(R.id.view1);
                     view1.setBackgroundColor(Color.YELLOW);
                 } else {
                     View view1 = findViewById(R.id.view1);
                     view1.setBackgroundColor(Color.BLUE);
                 }
             }
            }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onStop() {
        if (list.size()>0){
            sm.unregisterListener(sel);
        }
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}