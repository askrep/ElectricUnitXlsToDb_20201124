package com.kas.electricunitxlstodb_20201124.dao;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public class AppDatabaseHiltModule {

    @Singleton
    @Provides
    public static AppDatabase provideAppDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                AppDatabase.DATABASE_NAME).build();
    }

    @Singleton
    @Provides
    public static UnitDao provideUnitDao(AppDatabase database) {
        return database.unitDao();
    }
}
