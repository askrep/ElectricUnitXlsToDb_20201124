package com.kas.electricunitxlstodb_20201124.di;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.kas.electricunitxlstodb_20201124.dao.AppDatabase;
import com.kas.electricunitxlstodb_20201124.dao.UnitDao;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;
import com.kas.electricunitxlstodb_20201124.data.PreferencesUtil;

import java.util.List;

public class UnitLocalDataSource {
    private static final String TAG = "UnitLocalDataSource";
    private final Application application;
    private final UnitDao unitDao;
    private final LiveData<List<UnitEntry>> unitsLiveData;

    public UnitLocalDataSource(Application application) {
        this.application = application;
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        unitDao = appDatabase.unitDao();

        unitsLiveData = unitDao.selectAll();
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
        PreferencesUtil.setThemeModeDarkOn();
    }

    public void setThemeModeDarkOff() {
        PreferencesUtil.setThemeModeDarkOff();
    }

    /*NOT WORK*/
    public boolean isPrefThemeMode() {
        Log.d(TAG, "isPrefThemeMode: " + PreferencesUtil.isThemeModeDark(application));
        return PreferencesUtil.isThemeModeDark(application);
    }

    public boolean isPrefEditModeState() {
        Log.d(TAG, "isPrefEditModeState: " + PreferencesUtil.isEditMode(application));
        return PreferencesUtil.isEditMode(application);
    }
}
