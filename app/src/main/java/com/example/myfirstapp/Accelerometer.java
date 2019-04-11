package com.example.myfirstapp;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Accelerometer extends AppCompatActivity  implements SensorEventListener {
    private TextView xText, yText, zText;
    private Sensor mySensor;
    private SensorManager SM;

    MediaPlayer mp;

    RelativeLayout layout;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        textView = (TextView) findViewById(R.id.txt);

        layout = (RelativeLayout) findViewById(R.id.relativeLayout);

        // Create SensorManager
        SM = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Accelerometer Sensor
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register sensor Listener
        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Assign TextView
        xText = (TextView)findViewById(R.id.xText);
        yText = (TextView)findViewById(R.id.yText);
        zText = (TextView)findViewById(R.id.zText);
    }

    @Override
    protected void onPause() {
        super.onPause();
        pause();
        // to stop the listener and save battery
        SM.unregisterListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        if (Math.abs(x) > Math.abs(y)) {
            if (x < 0) {
                //image.setImageResource(R.drawable.right);
                textView.setText("You tilt the device right");
                play();
                //layout.setBackgroundColor(Color.RED);
            }
            if (x > 0) {
                //image.setImageResource(R.drawable.left);
                textView.setText("You tilt the device left");
                pause();
            }
        } else {
            if (y < 0) {
                //image.setImageResource(R.drawable.up);
                textView.setText("You tilt the device up");
                pause();
            }
            if (y > 0) {
                //image.setImageResource(R.drawable.down);
                textView.setText("You tilt the device down");
                pause();
            }
        }
        if (x > (-2) && x < (2) && y > (-2) && y < (2)) {
            //image.setImageResource(R.drawable.center);
            textView.setText("Not tilt device");
            pause();

        }

        //xText.setText("X: " + event.values[0]);

       /* if(event.values[0] >= 0){
            play();
            layout.setBackgroundColor(Color.RED);
        } else {
            pause();
            layout.setBackgroundColor(Color.WHITE);
        }

        yText.setText("Y: " + event.values[1]);
        zText.setText("Z: " + event.values[2]); */
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void play(){
        if(mp == null) {
            mp = MediaPlayer.create(this, R.raw.soho);
        }
        mp.start();
    }

    public void pause() {
        if (mp != null) {
            mp.pause();
        }
    }

    public void stopSong(View v) {
        mp.stop();
        mp=MediaPlayer.create(this, R.raw.soho);
    }
}
