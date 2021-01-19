package com.kas.electricunitxlstodb_20201124;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.kas.electricunitxlstodb_20201124.dao.UnitDao;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;
import com.kas.electricunitxlstodb_20201124.data.PreferencesUtil;
import com.kas.electricunitxlstodb_20201124.di.UnitLocalDataSource;
import com.kas.electricunitxlstodb_20201124.di.UnitRemoteDataSource;

import java.util.List;

public class UnitRepository {
    private static final String TAG = "#_UNIT_REPOSITORY";
    private static Repository instance;

    private UnitDao unitDao;

    private LiveData<List<UnitEntry>> unitsLiveData;
    private Application application;

    public void setUnitLocalDataSource(UnitLocalDataSource unitLocalDataSource) {
        this.unitLocalDataSource = unitLocalDataSource;
        unitsLiveData = unitLocalDataSource.getAllUnitsLiveData();
    }

    private UnitLocalDataSource unitLocalDataSource;
    private UnitRemoteDataSource unitRemoteDataSource;

    public UnitRepository(UnitRemoteDataSource unitRemoteDataSource) {//UnitLocalDataSource unitLocalDataSource,
       //TODO
        this.unitLocalDataSource = unitLocalDataSource;
        this.unitRemoteDataSource = unitRemoteDataSource;

    }

    public LiveData<List<UnitEntry>> getAllUnitsLiveData() {
        return unitLocalDataSource.getAllUnitsLiveData();
    }

    public LiveData<List<UnitEntry>> getFilteredUnitsLiveData(String filter) {
        return unitLocalDataSource.getFilteredUnitsLiveData(filter);
    }

    public LiveData<UnitEntry> getUnitById(int id) { //database.unitDao().loadUnitById(unitId);;
        return unitLocalDataSource.getUnitById(id);
    }

    public void insertUnit(UnitEntry unitEntry) {
        unitLocalDataSource.insertUnit(unitEntry);
    }

    public void updateUnit(UnitEntry unitEntry) {
        unitLocalDataSource.updateUnit(unitEntry);
    }

    public void deleteUnit(int unitId) {
        unitLocalDataSource.deleteUnit(unitId);
    }

    public void deleteAll() {
        unitLocalDataSource.deleteAll();
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
