package com.example.myfirstapp;

        import android.content.Intent;
        import android.hardware.SensorEvent;
        import android.hardware.SensorEventListener;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.hardware.Sensor;
        import android.hardware.SensorManager;
        import android.view.View;
        import android.view.animation.Animation;
        import android.view.animation.RotateAnimation;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Sensor mySensor;
    private Button acc;
    private Button comp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        acc = (Button)findViewById(R.id.accelerometer);
        comp = (Button)findViewById(R.id.compass);

        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAccelerometer();
            }
        });

        comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCompass();
            }
        });
    }

    public void openAccelerometer() {
        Intent intent = new Intent(this, Accelerometer.class);
        startActivity(intent);
    }

    public void openCompass() {
        Intent intent = new Intent(this, Compass.class);
        startActivity(intent);
    }
}
