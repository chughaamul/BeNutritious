package com.example.benutritious;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import static android.R.attr.value;

public class menu extends AppCompatActivity {
String name;
    String age;
    String gender;
    String lifestyle;
    Boolean pregnant;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        int temp_age = intent.getIntExtra("age",0);
        age = String.valueOf(temp_age);
        gender = intent.getStringExtra("gender");
        lifestyle = intent.getStringExtra("lifestyle");
        pregnant = intent.getBooleanExtra("pregnant",false);
    }


    public void calcDailyCalorie (View view){

        MyDatabase db = new MyDatabase(this);
        Cursor caloriesValue = db.getCalories(age,gender,lifestyle);
        value = caloriesValue.getString(1);
        Intent intent = new Intent(this,calorie_display.class);
        intent.putExtra("Value",value);
        intent.putExtra("age",age);
        intent.putExtra("gender",gender);
        intent.putExtra("pregnant",pregnant);
        startActivity(intent);

    }


    }





