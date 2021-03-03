package com.kas.electricunitxlstodb_20201124;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kas.electricunitxlstodb_20201124.dao.UnitDao;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityScoped;

public class Repository {

    private static final String TAG = "#_REPOSITORY";
    private static Repository repository;

    private UnitDao unitDao;

    private LiveData<List<UnitEntry>> unitsLiveData;
    private Application application;

    @Inject
    public Repository(@ActivityScoped Application application, UnitDao unitDao) {
        this.unitDao = unitDao;
        this.application = application;
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


}
