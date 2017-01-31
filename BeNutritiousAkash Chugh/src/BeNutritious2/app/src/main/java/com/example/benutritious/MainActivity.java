package com.example.benutritious;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Array;
import java.sql.SQLException;

import static android.R.attr.publicKey;
import static android.R.attr.value;

import static com.example.benutritious.R.id.age_text_view;
import static com.example.benutritious.R.id.name_text_view;
import static com.example.benutritious.R.id.preg_rad_grp;
import static com.example.benutritious.R.string.age_str;

public class MainActivity extends AppCompatActivity {

    private Cursor food_calorie;
    private MyDatabase db;
    String name;
    int age;
    String gender;
    String lifestyle;
    boolean pregnant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        TextView preg_question = (TextView) findViewById(R.id.pregnant_text_view);
        RadioGroup preg_rad = (RadioGroup) findViewById(preg_rad_grp);
        preg_question.setVisibility(View.INVISIBLE);
        preg_rad.setVisibility(View.INVISIBLE);

//         creating a database db and accessing the value
//        db = new MyDatabase(this);
//        food_calorie = db.getEmployees();
//        TextView calorieValue = (TextView) findViewById(R.id.name_db);
//        calorieValue.setText(food_calorie.getString(1));

        // setting the drop down menu
        Spinner drop_down = (Spinner) findViewById(R.id.lifestyle_drop_down);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lifestyle_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drop_down.setAdapter(adapter);

    }

    public void onProceedSecond(View view) {

        boolean age_set = check_age();
        RadioGroup mal_fem = (RadioGroup)findViewById(R.id.gender_rad_button);

        if (age_set &&  age_limit()) {
            if (gender != null) {
                EditText name_text_view = (EditText) findViewById(R.id.name_text_view);
                name = name_text_view.getText().toString();

                Spinner lifestyle_spinner = (Spinner) findViewById(R.id.lifestyle_drop_down);
                lifestyle = lifestyle_spinner.getSelectedItem().toString();

                Intent intent = new Intent(this, menu.class);
                intent.putExtra("name", name);
                intent.putExtra("age", age);
                intent.putExtra("gender", gender);
                intent.putExtra("pregnant", pregnant);
                intent.putExtra("lifestyle", lifestyle);
                startActivity(intent);
            }
            else{

                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("Chauvinists");
                builder1.setMessage("Naaaaa !! You can tell us your gender");
                builder1.setCancelable(true);
                builder1.setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert1 = builder1.create();
                alert1.show();


            }
        }
    }

    public void onPregnant_check(View view) {

        final RadioGroup preg_rad = (RadioGroup) findViewById(preg_rad_grp);

        preg_rad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.preg_yes_button:
                        pregnant = true;
                        break;
                    case R.id.preg_no_button:
                        pregnant = false;
                        break;


                }

            }
        });

    }


    public void viMale(View view) {
        RadioButton mal_radio_button = (RadioButton)findViewById(R.id.male_rad_button);
        boolean age_set =  check_age();
        if (age_set) {
            boolean age_lim = age_limit();
            if (age_lim) {
                mal_radio_button.setChecked(true);
                gender = "Male";
                final TextView preg_question = (TextView) findViewById(R.id.pregnant_text_view);
                final RadioGroup preg_rad = (RadioGroup) findViewById(preg_rad_grp);
                EditText age_text_view = (EditText) findViewById(R.id.age_text_view);
                String age_str;
                age_str = age_text_view.getText().toString();
                age = Integer.valueOf(age_str);
                if(age > 76){age = 76;}
                preg_question.setVisibility(View.INVISIBLE);
                preg_rad.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void vifemale(View view) {
        RadioButton femal_radio_button = (RadioButton)findViewById(R.id.female_rad_button);
        boolean age_set =  check_age();
        if (age_set) {
           boolean age_lim=  age_limit();
            if (age_lim) {
                femal_radio_button.setChecked(true);
                gender = "Female";
                final TextView preg_question = (TextView) findViewById(R.id.pregnant_text_view);
                final RadioGroup preg_rad = (RadioGroup) findViewById(preg_rad_grp);
                String age_str;
                EditText age_text_view = (EditText) findViewById(R.id.age_text_view);
                age_str = age_text_view.getText().toString();
                age = Integer.valueOf(age_str);
                if(age > 76){age = 76;}
                if (age > 25 && age < 32) {
                    preg_question.setVisibility(View.VISIBLE);
                    preg_rad.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    public boolean check_age() {


        RadioGroup gender_radio_group = (RadioGroup) findViewById(R.id.gender_rad_button);
        gender_radio_group.clearCheck();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Don't be shy");
        builder1.setMessage("Please set your age first");
        builder1.setCancelable(true);
        builder1.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        EditText check_age = (EditText)findViewById(R.id.age_text_view);
        String ed_text = check_age.getText().toString().trim();
        if(ed_text.isEmpty() || ed_text.length() == 0 || ed_text.equals("")|| ed_text.equals("0")) {
            AlertDialog alert11 = builder1.create();
            alert11.show();
            return false;

        }
        else {return true;}

    }

    public boolean age_limit (){

        EditText age_lim = (EditText)findViewById(R.id.age_text_view);
        int age_l = Integer.valueOf(age_lim.getText().toString());

        if(age_l < 116){return true;}
        else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Cmmon Don't lie");
            builder1.setMessage("Please set your correct age");
            builder1.setCancelable(true);
            builder1.setNeutralButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert1 = builder1.create();
            alert1.show();

            return false;}
    }
}

