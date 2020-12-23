package com.kas.electricunitxlstodb_20201124;

import android.app.Application;
import android.content.Context;
import android.util.Log;

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


    public Repository(Application application) {
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

    public boolean isPrefThemeMode(Context context) {
        Log.d(TAG, "isPrefThemeMode: " + PreferencesUnit.isThemeModeDark(context));
        return PreferencesUnit.isThemeModeDark(context);
    }
    public void setThemeModeDarkOn() {
        PreferencesUnit.setThemeModeDarkOn();
    }

    public void setThemeModeDarkOff() {
        PreferencesUnit.setThemeModeDarkOff();
    }

    public boolean isPrefEditModeState(Application application) {
        Log.d(TAG, "isPrefEditModeState: " + PreferencesUnit.isEditMode(application));
        return PreferencesUnit.isEditMode(application);
    }

}
