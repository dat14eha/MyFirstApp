package com.example.myfirstapp;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Compass extends AppCompatActivity implements SensorEventListener {

    // device sensor manager
    private SensorManager SensorManage;
    // define the compass picture that will be use
    private ImageView compassimage;
    // record the angle turned of the compass picture
    private float DegreeStart = 0f;

    TextView DegreeTV;

    MediaPlayer mp;

    RelativeLayout layout;

    Vibrator vibrator;

    private boolean firstPic;

    boolean color = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        //
        compassimage = (ImageView) findViewById(R.id.compass_image);
        firstPic = true;
        // TextView that will display the degree
        DegreeTV = (TextView) findViewById(R.id.Degree);
        // initialize your android device sensor capabilities
        SensorManage = (SensorManager) getSystemService(SENSOR_SERVICE);

        layout = (RelativeLayout) findViewById(R.id.relativeLayout);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        Button compass_button = (Button) findViewById(R.id.compass_button);
        compass_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstPic) {
                    compassimage.setImageResource(R.drawable.compass2);
                    firstPic = false;
                } else {
                    compassimage.setImageResource(R.drawable.compass_icon);
                    firstPic = true;
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        vibrator.cancel();
        pause();
        // to stop the listener and save battery
        SensorManage.unregisterListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // code for system's orientation sensor registered listeners
        SensorManage.registerListener(this, SensorManage.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // get angle around the z-axis rotated
        float degree = Math.round(event.values[0]);
        DegreeTV.setText("Heading: " + Float.toString(degree) + " degrees");
        // rotation animation - reverse turn degree degrees
        RotateAnimation ra = new RotateAnimation(
                DegreeStart,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        // set the compass animation after the end of the reservation status
        ra.setFillAfter(true);
        // set how long the animation for the compass image will take place
        ra.setDuration(210);
        // Start animation of compass image
        compassimage.startAnimation(ra);
        DegreeStart = -degree;

        if (degree >= 345 || degree <= 15) {
            play();
            if(color) {
                layout.setBackgroundColor(Color.RED);
                color = false;
            } else {
                layout.setBackgroundColor(Color.BLUE);
                color = true;
            }
            vibrator.vibrate(10000);
                //vibrator.vibrate(0b111110100);
        } else {
            pause();
            layout.setBackgroundColor(Color.WHITE);
            vibrator.cancel();
        }
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

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
