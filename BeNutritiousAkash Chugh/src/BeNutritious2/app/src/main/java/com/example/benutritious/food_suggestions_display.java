package com.example.benutritious;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class food_suggestions_display extends AppCompatActivity {

    String first_food;
    String second_food;
    String food_suggestion_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_suggestions_display);

        Intent intent = getIntent();
        TextView food_suggestion = (TextView)findViewById(R.id.food_suggestion_display_text);

        first_food = intent.getStringExtra("first_food");
        second_food = intent.getStringExtra("second_food");

        food_suggestion_text = "Today you should try out" + first_food + second_food;

        food_suggestion.setText(food_suggestion_text);

    }
}
