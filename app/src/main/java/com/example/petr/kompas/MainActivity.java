package com.example.petr.kompas;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    ImageView imageViewArrow;
    private static SensorManager sensorManager;
    private Sensor sensor;
    private float aktualniPozice = 0f;
    TextView textViewUhel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageViewArrow = (ImageView) findViewById(R.id.imageViewArrow);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        textViewUhel = (TextView) findViewById(R.id.textViewUhel);
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int stupne = Math.round(event.values[0]);
        stupne -= 90;//korekce smeru sipky (nase miri do prava tj. otocit o -90 stupnu
        RotateAnimation rotate = new RotateAnimation(aktualniPozice, -stupne, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setFillAfter(true);
        imageViewArrow.startAnimation(rotate);
        textViewUhel.setText(Integer.toString(stupne));
        aktualniPozice=-stupne;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
