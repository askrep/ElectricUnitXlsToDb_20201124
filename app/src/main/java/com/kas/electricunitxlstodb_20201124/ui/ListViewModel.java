package com.kas.electricunitxlstodb_20201124.ui;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kas.electricunitxlstodb_20201124.Repository;
import com.kas.electricunitxlstodb_20201124.dao.AppDatabase;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import java.util.List;

public class ListViewModel extends AndroidViewModel {
    
    private static final String LOG_TAG = "#_LIST_ViewModel";
    
    @NonNull
    private LiveData<List<UnitEntry>> unitsLiveData;
    
    private final AppDatabase database;
    
    public ListViewModel(@NonNull Application application) {
        super(application);
        
        database = AppDatabase.getInstance(this.getApplication());
        //units = database.unitDao().selectAll();
        Repository repository = new Repository(application);
        unitsLiveData = repository.getUnitsLiveData();
        
        Log.d(LOG_TAG, "Load task from database");
    }
    
    public LiveData<List<UnitEntry>> getUnitsLiveData() {
        return unitsLiveData;
    }
    
}