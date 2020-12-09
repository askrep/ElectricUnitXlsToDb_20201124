package com.kas.electricunitxlstodb_20201124;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kas.electricunitxlstodb_20201124.dao.AppDatabase;
import com.kas.electricunitxlstodb_20201124.dao.UnitDao;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import java.util.List;

public class Repository {
    
    private static Repository instance;
    private UnitDao unitDao;
    
    private LiveData<List<UnitEntry>> unitsLiveData;
    private MutableLiveData<List<UnitEntry>> unitsFilteredLiveData;
    
    public Repository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        unitDao = appDatabase.unitDao();
         unitsLiveData = unitDao.selectAll();
    }
    
    @NonNull
    public LiveData<List<UnitEntry>> getUnitsLiveData() {
 
        return unitsLiveData;
    }
    
/*    public static Repository getInstance(Application application) {
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
