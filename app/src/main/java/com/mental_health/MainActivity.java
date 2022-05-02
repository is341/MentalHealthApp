package com.mental_health;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    String user_type;
    MaterialButton doctors_directory,questionnaire,exercises;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        doctors_directory=findViewById(R.id.doctors_directory);
        questionnaire=findViewById(R.id.questionnaire);
        exercises=findViewById(R.id.exercises);

        user_type= PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("user_type", "");
        if (user_type.equals("doctor")){
            doctors_directory.setText("Patients Directory");
            questionnaire.setVisibility(View.GONE);
            exercises.setVisibility(View.GONE);
        }


        doctors_directory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DoctorsDirectory.class);
                startActivity(intent);
            }
        });

        questionnaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TakeQuiz.class);
                startActivity(intent);
            }
        });
        exercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Exercises.class);
                startActivity(intent);
            }
        });
    }
}