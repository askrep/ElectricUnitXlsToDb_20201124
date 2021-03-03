package com.kas.electricunitxlstodb_20201124.dao;

import android.content.Context;

import androidx.room.Room;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public class AppDatabaseHiltModule {

    @Provides
    public AppDatabase provideAppDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                AppDatabase.DATABASE_NAME).build();
    }

    @Provides
    public UnitDao provideUnitDao(AppDatabase database) {
        return database.unitDao();
    }
}
