package com.kas.electricunitxlstodb_20201124;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kas.electricunitxlstodb_20201124.dao.AppDatabase;
import com.kas.electricunitxlstodb_20201124.dao.UnitDao;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

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

    public LiveData<List<UnitEntry>> getAllUnitsLiveData() {
        return unitsLiveData;
    }

    public LiveData<List<UnitEntry>> getFilteredUnitsLiveData(String filter) {
        return unitDao.loadUnitListFiltered(filter);
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

    public LiveData<UnitEntry> getUnitById(int id) { //database.unitDao().loadUnitById(unitId);;
        return unitDao.loadUnitById(id);
    }

    //  database.unitDao().insertUnit(unitEntry);
    public void insertUnit(UnitEntry unitEntry) {
        unitDao.insertUnit(unitEntry);
    }

    // database.unitDao().updateUnit(unitEntry);
    public void updateUnit(UnitEntry unitEntry) {
        unitDao.updateUnit(unitEntry);
    }

    // database.unitDao().deleteUnit(unitId);
    public void deleteUnit(int unitId) {
        unitDao.deleteUnit(unitId);
    }

    public void deleteAll() {
        unitDao.deleteAll();
    }
}
