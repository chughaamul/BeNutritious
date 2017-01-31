package com.example.benutritious;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.R.attr.button;
import static android.R.attr.type;
import static android.R.transition.fade;


public class calorie_display extends AppCompatActivity {
    String calorie;
    String age;
    String gender;
    Boolean temp_pregnant;
    String pregnant;
    Cursor nutrients;
    String calcium;
    String iron;
    String magnesium;
    String sodium;
    String potassium;
    String phosphorous;
    ValueAnimator animator = new ValueAnimator();
    public ArrayList<String> food_categories = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_display);
        MyDatabase db = new MyDatabase(this);

        TextView value_text = (TextView)findViewById(R.id.value_text_view);

        Intent intent = getIntent();

        calorie = intent.getStringExtra("Value");
        int calorie_int = Integer.valueOf(calorie);
        startCountAnimation(calorie_int,value_text);
        age= intent.getStringExtra("age");
        gender = intent.getStringExtra("gender");
        temp_pregnant = Boolean.getBoolean("pregnant");
        pregnant = String.valueOf(temp_pregnant);

        nutrients = db.getnutrientsChart(age,gender,pregnant);
        calcium = nutrients.getString(0);
        iron = nutrients.getString(1);
        magnesium = nutrients.getString(2);
        sodium = nutrients.getString(3);
        potassium = nutrients.getString(4);
        phosphorous = nutrients.getString(5);

    }




    private void startCountAnimation(int final_value, final TextView textView) {

        animator.setObjectValues(0, final_value);
        animator.setDuration(4000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.setText("" + (int) animation.getAnimatedValue());
            }
        });



        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                int animProgress = (Integer) animation.getAnimatedValue();

            }
        });
        animator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                new CountDownTimer(5000, 1000) {
                    public void onFinish() {
                        // When timer is finished
                        // Execute your code here
                        TextView value_text = (TextView)findViewById(R.id.value_text_view);
                        TextView you_need_calorie = (TextView) findViewById(R.id.you_need_calories);
                        TextView calorie_text = (TextView)findViewById(R.id.text_calories);
                        value_text.setVisibility(View.INVISIBLE);
                        you_need_calorie.setVisibility(View.INVISIBLE);
                        calorie_text.setVisibility(View.INVISIBLE);
                        set_dive_deeper_visible();
                    }

                    public void onTick(long millisUntilFinished) {
                        // millisUntilFinished    The amount of time until finished.
                    }
                }.start();
            }
        });
        animator.start();


    }public  void setMinerals(View view){

        MyDatabase dba = new MyDatabase(this);
        TextView txt = (TextView) findViewById(R.id.after_calorie_display_exact_minerals);
        TextView you_precisely_text = (TextView)findViewById(R.id.you_precisely_need_text);
        you_precisely_text.setVisibility(View.VISIBLE);
        txt.setVisibility(View.VISIBLE);
        Button make_more_sense_button = (Button) findViewById(R.id.make_more_sese);
        make_more_sense_button.setVisibility(View.VISIBLE);
        String mineral_text = "Calcium : "+ (calcium) + "mg\n" + "Iron :" + (iron) + "mg \n" +
                "Potassium : " + (potassium) + "mg\n" + "Magnesium :" + (magnesium) + "mg\n" +
                "Phshphorous :" + (phosphorous) + "mg\n" + "Sodium :" + (sodium) + "mg\n";

        txt.setText(mineral_text);


    }
    public void set_dive_deeper_visible(){
        Button dive_deeper = (Button) findViewById(R.id.dive_deeper);
        dive_deeper.setVisibility(View.VISIBLE);

    }
    public void dive_deeper_function(View view){
        Button dive_deeper = (Button) findViewById(R.id.dive_deeper);
        dive_deeper.setVisibility(View.INVISIBLE);
        setMinerals(view);

    }

    public void make_more_sense_function(View view){
        Intent intent = new Intent(this,food_selection.class);
        intent.putExtra("reqdCalcium",calcium);
        intent.putExtra("reqdIron",iron);
        intent.putExtra("reqdSodium",sodium);
        intent.putExtra("reqdMagnesium",magnesium);
        intent.putExtra("reqdPhosphorus",phosphorous);
        startActivity(intent);

    }

}
