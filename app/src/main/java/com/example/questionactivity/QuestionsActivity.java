package com.example.questionactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity implements SensorEventListener {

    Sensor s1, s2;
    SensorManager sm;
    TextView questionTv;
    ArrayList<String> question;
    int index = 0;
    boolean isFar = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        questionTv = findViewById(R.id.TV);
        Intent i = getIntent();
        question = i.getStringArrayListExtra("aa");

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        s1 = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        s2 = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (s1 != null) {
            sm.registerListener(this, s1, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor NOT FOUND", Toast.LENGTH_SHORT).show();
        }

        if (s2 != null) {
            sm.registerListener(this, s2, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor NOT FOUND", Toast.LENGTH_SHORT).show();
        }

        Thread thread = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    if(isFar){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (index < question.size()) {
                                    questionTv.setText(question.get(index));
                                    index++;
                                } else {
                                    index = 0;
                                }
                            }
                        });
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        thread.start();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            int x = (int) event.values[0];
            if (x == 0) {
                isFar = false;
            } else {
                isFar = true;
            }
        }
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (event.values[2] <= -5) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        sm.registerListener(this, s1, sm.SENSOR_DELAY_UI);
        sm.registerListener(this, s2, sm.SENSOR_DELAY_UI);

    }
}