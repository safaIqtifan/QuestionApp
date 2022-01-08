package com.example.questionactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> output = new ArrayList<String>();
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.edittext);
        TextView x = findViewById(R.id.textView2);
        Button buttonadd = findViewById(R.id.add);
        Button buttonview = findViewById(R.id.myQ);

        x.setText(String.valueOf(count));

        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter Question", Toast.LENGTH_SHORT).show();
                } else {

                    String question = editText.getText().toString();
                    output.add(question);
                    count++;
                    x.setText(String.valueOf(count));
                    editText.getText().clear();
                }

            }
        });

        buttonview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (output.size() >= 2) {
                    Intent i = new Intent(MainActivity.this,QuestionsActivity.class);
                    i.putStringArrayListExtra("aa",output);
                    startActivity(i);
                } else {
                    Toast.makeText(MainActivity.this, "Add 2 questions or more to start .", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }
}