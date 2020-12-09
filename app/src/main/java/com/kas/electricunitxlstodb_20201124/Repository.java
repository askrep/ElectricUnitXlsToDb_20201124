package com.kas.electricunitxlstodb_20201124;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.kas.electricunitxlstodb_20201124.dao.AppDatabase;
import com.kas.electricunitxlstodb_20201124.dao.UnitDao;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import java.util.List;

public class Repository {

    private static final String LOG_TAG = "#_REPOSITORY";
    private static Repository instance;
    private UnitDao unitDao;

    private LiveData<List<UnitEntry>> unitsLiveData;
    private LiveData<List<UnitEntry>> unitsFilteredLiveData;

    public Repository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        unitDao = appDatabase.unitDao();
/*        AppExecutors.getInstance().diskIO().execute(()->{

        });*/
        unitsLiveData = unitDao.selectAll();
        unitsFilteredLiveData = unitDao.loadUnitListFiltered("2");
        //unitsLiveData.setValue(units);
    }

    @NonNull
    public LiveData<List<UnitEntry>> getAllUnitsLiveData() {
        return unitsLiveData;
    }

    @NonNull
    public LiveData<List<UnitEntry>> getUnitListFilter(String filter) {
        return unitsFilteredLiveData;
    }
/*
    public static Repository getInstance(Application application) {
        if (instance == null) {
            synchronized (Repository.class) {
                if (instance == null) {
                    instance = new Repository(application);
                }
            }
        }
        return instance;
    }*/


}
