package com.kas.electricunitxlstodb_20201124.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UnitEntry.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = "#_APP_DATABASE";
    public static final String DATABASE_NAME = "units_db";

    public abstract UnitDao unitDao();
}
