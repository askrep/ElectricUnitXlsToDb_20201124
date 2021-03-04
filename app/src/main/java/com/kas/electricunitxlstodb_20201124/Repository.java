package com.kas.electricunitxlstodb_20201124;

import androidx.lifecycle.LiveData;

import com.kas.electricunitxlstodb_20201124.dao.UnitDao;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import java.util.List;

import javax.inject.Inject;

public class Repository {

    private static final String TAG = "#_REPOSITORY";

    private UnitDao unitDao;
    private LiveData<List<UnitEntry>> unitsLiveData;


    @Inject
    public Repository(UnitDao unitDao) {
        this.unitDao = unitDao;
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
