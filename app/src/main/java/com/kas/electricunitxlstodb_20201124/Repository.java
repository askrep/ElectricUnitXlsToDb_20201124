package com.kas.electricunitxlstodb_20201124;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
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
    private LiveData<List<UnitEntry>> unitsFilteredLiveData;
    private String filter = "";

    public Repository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        unitDao = appDatabase.unitDao();
        unitsLiveData = unitDao.selectAll();
        // unitsFilteredLiveData = unitDao.loadUnitListFiltered(filter);

    }

    @NonNull
    public LiveData<List<UnitEntry>> getAllUnitsLiveData() {
        return unitsLiveData;
    }

    public LiveData<List<UnitEntry>> getFilteredUnitsLiveData(String filter) {
        LiveData<List<UnitEntry>> listLiveData = unitDao.loadUnitListFiltered(filter);
        if (listLiveData != null) {
            Log.d(TAG, "getFilteredUnits: " + "%" + filter + "%" + " len=" );//+ listLiveData.getValue().size()
        }
        return listLiveData; //"%" + filter + "%"
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

}
