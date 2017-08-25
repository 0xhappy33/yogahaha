package com.example.ha.yogabeginner.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ha Truong on 8/25/2017.
 * This is a YogaBeginner
 * into the com.example.ha.yogabeginner.database
 */

public class YogaDB extends SQLiteAssetHelper{

    private static final String DB_NAME = "yoga.db";
    private static final int DB_VER = 1;

    public YogaDB(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public int getSettingMode(){

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Mode"};
        String sqlTable = "Setting";

        qb.setTables(sqlTable);
        Cursor cursor = qb.query(db, sqlSelect,null,null,null,null,null);
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex("Mode"));
    }

    public void saveSettingMode(int value){
        SQLiteDatabase db = getReadableDatabase();
        String query = "UPDATE Setting SET Mode = " + value;
        db.execSQL(query);
    }

    public List<String> getWorkoutDays(){

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Day"};
        String sqlTable = "WorkoutDays";

        qb.setTables(sqlTable);
        Cursor cursor = qb.query(db, sqlSelect,null,null,null,null,null);

        List<String> result = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                result.add(cursor.getString(cursor.getColumnIndex("Day")));
            }while (cursor.moveToNext());
        }
        return result;
    }

    public void saveDay(String value){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO WorkoutDays(Day) VALUES('%s');", value);
        db.execSQL(query);
    }
}
