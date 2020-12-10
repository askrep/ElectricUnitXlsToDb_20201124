package com.kas.electricunitxlstodb_20201124.dao;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UnitEntry.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = "#_APP_DATABASE";
    private static final String DATABASE_NAME = "units_db";

    private static final Object LOCK = new Object();
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "CREATING NEW DB");
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class,
                        AppDatabase.DATABASE_NAME).build();
            }
        }
        Log.d(LOG_TAG, "GET DB INSTANCE");

        return instance;
    }

    public abstract UnitDao unitDao();
}
