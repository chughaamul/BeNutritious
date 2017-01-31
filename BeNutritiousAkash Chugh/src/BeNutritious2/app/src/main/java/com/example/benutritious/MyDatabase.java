package com.example.benutritious;

/**
 * Created by Akash.
 */

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.annotation.IntegerRes;
import android.view.View;

import java.util.ArrayList;




public class MyDatabase extends SQLiteAssetHelper {

    ArrayList<String> food_categories = new ArrayList<>();
    ArrayList<String> sub_food_categories = new ArrayList<>();

    private static final String DATABASE_NAME = "foodNutrients.db";
    private static final int DATABASE_VERSION = 1;
    public String type;
    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    public Cursor getCalories(String Age, String Gender,String Lifestyle) {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] tableColumns = {"_id", "Calorie", "gender", "lifestyle"};
        String sqlTables = "food_calorie";
        String whereClause = " _id = ? AND gender = ? and lifestyle = ?";
        String whereArgs [] = new String[] {Age,Gender,Lifestyle};
        qb.setTables(sqlTables);
        Cursor c = qb.query(db, tableColumns,whereClause, whereArgs,
                null, null, null);
        c.moveToFirst();
        return c;

    }

    public Cursor getnutrientsChart(String Age, String Gender,String Pregnant) {


        int age  = Integer.valueOf(Age);

        if (Gender == "Female")
        {
            if (Pregnant == "true");
            {
                type = "0";
            }
            if (age < 18)
            {
                type = "0";

            }
            else if(age>18 && age < 30)
        {
            type = "1";
        }

            else if(age > 30 && age < 60)
        {
            type = "2";
        }
            else{

            type = "3";
        }
        }
        else
        {
            if (age < 18)
            {
                type = "0";

            }
            else if(age>18 && age < 30)
            {
                type = "1";
            }

            else if(age > 30 && age < 60)
            {
                type = "2";
            }
            else{

                type = "3";
            }
        }

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qba = new SQLiteQueryBuilder();
        String[] tableColumns = {"calcium", "iron", "magnesium", "sodium","potassium","phosphorous"};
        String sqlTables = "nutrientsChar";
        String whereClause = "_id= ? AND gender = ? and pregnant = ?";
        String whereArgs [] = new String[] {type,Gender,Pregnant};
        qba.setTables(sqlTables);
        Cursor c = qba.query(db, tableColumns,whereClause, whereArgs,null, null, null);
        c.moveToFirst();
        return c;

    }

    public ArrayList<String> get_food_categories(View view){
        SQLiteDatabase dba = getReadableDatabase();
        SQLiteQueryBuilder qba = new SQLiteQueryBuilder();
        String[] tableColumns = {"mineral"};
        String sqlTables = "nutrientsChar";
        qba.setTables(sqlTables);
        Cursor cur = qba.query(dba, tableColumns,null,null,null, null, null);

        if (cur.moveToFirst()){
            while(cur.isAfterLast()== false){

               String food = cur.getString(0);
                food_categories.add(food);
                cur.moveToNext();
            }
        }
        return food_categories;

    }


    public ArrayList<String> get_sub_food_list(String food_category_selection){


        SQLiteDatabase dba = getReadableDatabase();
        SQLiteQueryBuilder qba = new SQLiteQueryBuilder();
        String[] tableColumns = {"_id","Food"};
        String sqlTables = "food_minerals";
        String whereClause = "_id= ?";
        String whereArgs [] = new String[] {food_category_selection};
        qba.setTables(sqlTables);
        Cursor cur = qba.query(dba, tableColumns,whereClause,whereArgs,null, null, null);

        if (cur.moveToFirst()){
            while(cur.isAfterLast()== false){

                String food = cur.getString(1);
                sub_food_categories.add(food);
                cur.moveToNext();
            }
        }
        return sub_food_categories;

    }

    public Cursor get_food_minerals(String food_selection,String sub_food_selection){

        SQLiteDatabase dba = getReadableDatabase();
        SQLiteQueryBuilder  qba = new SQLiteQueryBuilder()  ;
        String[] tableColumns = {"Serving", "Weight", "Calcium","Iron", "Sodium","Magnesium", "Phosphorus","Protein"};
        String sqlTables = "food_minerals";
        String whereClause = "_id = ? AND Food = ?";
        String whereArgs [] = new String[] {food_selection,sub_food_selection};
        qba.setTables(sqlTables);
        Cursor cur = qba.query(dba, tableColumns,whereClause,whereArgs,null,null,null);
        cur.moveToFirst();
        return cur;

    }

    public Cursor get_food_suggestion_(String mineral){
        SQLiteDatabase dba = getReadableDatabase();
        SQLiteQueryBuilder  qba = new SQLiteQueryBuilder();
        String tableName = "nutrientsChar";
        String[] tableColumns = {"mineral","val"};
        String whereClause = "mineral = ?";
        String whereArgs [] = new String[] {mineral};
        qba.setTables(tableName);
        Cursor cur = qba.query(dba,tableColumns,whereClause,whereArgs,null,null,null);
        cur.moveToFirst();
        return cur;

    }

}

