package com.kas.electricunitxlstodb_20201124;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kas.electricunitxlstodb_20201124.dao.AppDatabase;
import com.kas.electricunitxlstodb_20201124.dao.UnitDao;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;
import com.kas.electricunitxlstodb_20201124.data.PreferencesUnit;

import java.util.List;

public class Repository {

    private static final String TAG = "#_REPOSITORY";
    private static Repository instance;

    private UnitDao unitDao;

    private LiveData<List<UnitEntry>> unitsLiveData;
    private Application application;

    public Repository(Application application) {
        this.application = application;
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        unitDao = appDatabase.unitDao();
        unitsLiveData = unitDao.selectAll();
    }

    public static Repository getInstance(Application application) {
        if (instance == null) {
            synchronized (Repository.class) {
                if (instance == null) {
                    instance = new Repository(application);
                }
            }
        }
        return instance;
    }

    public LiveData<List<UnitEntry>> getAllUnitsLiveData() {
        return unitsLiveData;
    }

    public LiveData<List<UnitEntry>> getFilteredUnitsLiveData(String filter) {
        return unitDao.loadUnitListFiltered(filter);
    }

    public LiveData<UnitEntry> getUnitById(int id) { //database.unitDao().loadUnitById(unitId);;
        return unitDao.loadUnitById(id);
    }

    public void insertUnit(UnitEntry unitEntry) {
        unitDao.insertUnit(unitEntry);
    }

    public void updateUnit(UnitEntry unitEntry) {
        unitDao.updateUnit(unitEntry);
    }

    public void deleteUnit(int unitId) {
        unitDao.deleteUnit(unitId);
    }

    public void deleteAll() {
        unitDao.deleteAll();
    }

    public void setThemeModeDarkOn() {
        PreferencesUnit.setThemeModeDarkOn();
    }

    public void setThemeModeDarkOff() {
        PreferencesUnit.setThemeModeDarkOff();
    }

    /*NOT WORK*/
    /*    public boolean isPrefThemeMode(Context context) {
        Log.d(TAG, "isPrefThemeMode: " + PreferencesUnit.isThemeModeDark(context));
        return PreferencesUnit.isThemeModeDark(context);
    }*/
/*
    public boolean isPrefEditModeState() {
        Log.d(TAG, "isPrefEditModeState: " + PreferencesUnit.isEditMode(application));
        return PreferencesUnit.isEditMode(application);
    }*/
}
