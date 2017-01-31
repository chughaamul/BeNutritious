package com.example.benutritious;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static com.example.benutritious.R.array.food_categories;

public class food_selection extends AppCompatActivity {
    public String food_category_selection;
    Cursor c_sub_food_minerals;
    int qty;
    String Serving;
    Float reqdCalcium;
    Float reqdIron;
    Float reqdSodium;
    Float reqdMagnesium;
    Float reqdPhosphorus;

    Float actCalcium;
    Float actIron;
    Float actSodium;
    Float actMagnesium;
    Float actPhosphorus;

    Float neededCalcium;
    Float neededIron;
    Float neededSodium;
    Float neededMagnesium;
    Float neededPhosphorus;
    String first_max_nutrient;
    String second_max_nutrient;

    public String sub_food_category_selection;
    public ArrayList<String> sub_food_categories = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_selection);

        Intent intent = getIntent();
        String st_reqdCalcium = intent.getStringExtra("reqdCalcium");
        String st_reqdIron = intent.getStringExtra("reqdIron");
        String st_reqdSodium = intent.getStringExtra("reqdSodium");
        String st_reqdMagnesium = intent.getStringExtra("reqdMagnesium");
        String st_reqdPhosphorus = intent.getStringExtra("reqdPhosphorus");


        reqdCalcium = Float.valueOf(st_reqdCalcium);
        reqdIron = Float.valueOf(st_reqdIron);
        reqdMagnesium = Float.valueOf(st_reqdMagnesium);
        reqdPhosphorus = Float.valueOf(st_reqdPhosphorus);
        reqdSodium = Float.valueOf(st_reqdSodium);



        AutoCompleteTextView food_category_auto = (AutoCompleteTextView) findViewById(R.id.food_category_auto_complete);
        String food_categories [] = getResources().getStringArray(R.array.food_categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,food_categories);

        food_category_auto.setAdapter(adapter);

        food_category_auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                 food_category_selection = (String)parent.getItemAtPosition(position);
                initialize_sub_food(food_category_selection);

            }
        });

    }


    public void initialize_sub_food(final String food_category_selection){


        final DecimalFormat df = new DecimalFormat("#.#");
        AutoCompleteTextView sub_food_view = (AutoCompleteTextView) findViewById(R.id.sub_food_auto_complete);
        sub_food_view.setVisibility(View.VISIBLE);
        final MyDatabase db = new MyDatabase(this);
        sub_food_categories = db.get_sub_food_list(food_category_selection);

        AutoCompleteTextView sub_food_category_auto = (AutoCompleteTextView) findViewById(R.id.sub_food_auto_complete);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,sub_food_categories);

        sub_food_category_auto.setAdapter(adapter);


        sub_food_category_auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {

                LinearLayout lin = (LinearLayout)findViewById(R.id.food_minerals_qty_servings_layout);
                lin.setVisibility(View.VISIBLE);


                sub_food_category_selection = (String)parent.getItemAtPosition(position);
                c_sub_food_minerals =  db.get_food_minerals(food_category_selection,sub_food_category_selection);
                String servings_from_db = c_sub_food_minerals.getString(0);
                String st_actCalcium = c_sub_food_minerals.getString(2);
                String st_actIron = c_sub_food_minerals.getString(3);
                String st_actSodium = c_sub_food_minerals.getString(4);
                String st_actMagnesium= c_sub_food_minerals.getString(5);
                String st_actPhosphorus = c_sub_food_minerals.getString(6);


                actCalcium = Float.valueOf(st_actCalcium);
                actIron = Float.valueOf(st_actIron);
                actMagnesium = Float.valueOf(st_actMagnesium);
                actPhosphorus = Float.valueOf(st_actPhosphorus);
                actSodium = Float.valueOf(st_actSodium);

                neededCalcium = reqdCalcium - actCalcium;
                if(reqdCalcium < 0){reqdCalcium = Float.valueOf(0);}
                neededIron = reqdIron-actIron;
                if(reqdIron < 0){reqdIron = Float.valueOf(0);}
                neededMagnesium = reqdMagnesium - actMagnesium;
                if(reqdMagnesium < 0){reqdMagnesium = Float.valueOf(0);}
                neededPhosphorus = reqdPhosphorus - actPhosphorus;
                if(reqdPhosphorus < 0){reqdPhosphorus = Float.valueOf(0);}
                neededSodium = reqdSodium - actSodium;
                if(reqdSodium < 0){reqdSodium = Float.valueOf(0);}


                df.format(neededCalcium);
                df.format(neededIron);
                df.format(neededMagnesium);
                df.format(neededPhosphorus);
                df.format(neededSodium);

                Serving = "Servings :" + servings_from_db;

                EditText quanty = (EditText) findViewById(R.id.food_minerals_quantity);
                TextView serv = (TextView)findViewById(R.id.food_minerals_servings);
                serv.setText(Serving);
               intialize_health_bar_button(view);
            }
        });

    }


    public void intialize_health_bar_button(View view){


       // LinearLayout lin_food_options = (LinearLayout)findViewById(R.id.food_display_options_combined);
        //lin_food_options.setVisibility(View.INVISIBLE);

        Button healthBar = (Button) findViewById(R.id.health_bar_button);
        healthBar.setVisibility(View.VISIBLE);


    }


    public void display_needed_minerals(View view){


        AutoCompleteTextView first_category = (AutoCompleteTextView) findViewById(R.id.food_category_auto_complete);
        AutoCompleteTextView second_category = (AutoCompleteTextView) findViewById(R.id.sub_food_auto_complete);
        LinearLayout lin_combined = (LinearLayout)findViewById(R.id.food_minerals_qty_servings_layout);
        Button healthBar = (Button) findViewById(R.id.health_bar_button);
        TextView display_minerals = (TextView)findViewById(R.id.displaying_needed_minerals_text) ;
        Button food_suggestions = (Button)findViewById(R.id.food_suggestions);

        first_category.setVisibility(View.INVISIBLE);
        second_category.setVisibility(View.INVISIBLE);
        lin_combined.setVisibility(View.INVISIBLE);
        healthBar.setVisibility(View.INVISIBLE);

        String Calcium;
        String Iron;
        String Sodium;
        String Magnesium;
        String Phosphorus;

        Calcium = String.valueOf(neededCalcium);
        Iron = String.valueOf(neededIron);
        Sodium = String.valueOf(neededSodium);
        Magnesium = String.valueOf(neededMagnesium);
        Phosphorus = String.valueOf(neededPhosphorus);



        String display_needed_minerals = "Your nutrition fact for today is \n"+
                                            "Calcium : " + Calcium + "mg\n" +
                                            "Iron : " + Iron + "mg\n" +
                                            "Sodium :" + Sodium + "mg\n" +
                                            "Magnesium :" + Magnesium + "mg\n" +
                                            "Phosphorus : " + Phosphorus + "mg\n";

        display_minerals.setVisibility(View.VISIBLE);
        display_minerals.setText(display_needed_minerals);
        food_suggestions.setVisibility(View.VISIBLE);


    }


    public void calculate_max_nutrients_required(View view){
        String[] nutrients_name = {"Calcium","Iron","Sodium","Magnesium","Phosphorus"};
        float[] nutrients_values = {neededCalcium,neededIron,neededSodium,neededMagnesium,neededPhosphorus};


        Intent intent = new Intent(this,food_suggestions_display.class);
        Cursor first_food_cur;
        Cursor second_food_cur;
        String first_food;
        String second_food;
        int first_max = 0;
        int second_max = 0;
        float first_max_value = 0;

        MyDatabase db = new MyDatabase(this);

        for (int i = 0; i < 5;i++ ){
            if (nutrients_values[i] > first_max_value){
                second_max = first_max;
                first_max_value = nutrients_values[i];
                first_max = i;
            }
            else{
            if(nutrients_values[i]>nutrients_values[second_max]) {
                second_max = i;
            }
            }
        }

        first_max_nutrient = nutrients_name[first_max];
        second_max_nutrient = nutrients_name[second_max];

        first_food_cur = db.get_food_suggestion_(first_max_nutrient);
        second_food_cur = db.get_food_suggestion_(second_max_nutrient);

        first_food = first_food_cur.getString(1);
        second_food = second_food_cur.getString(1);

        intent.putExtra("first_food",first_food);
        intent.putExtra("second_food",second_food);
        startActivity(intent);



    }


}
