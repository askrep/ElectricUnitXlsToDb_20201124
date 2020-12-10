package com.kas.electricunitxlstodb_20201124.ui;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.kas.electricunitxlstodb_20201124.Repository;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import java.util.List;

public class SharedViewModel extends AndroidViewModel {
    
    private static final String TAG = "#_SHARED_VM";
    @NonNull
    private Repository repository;
    @NonNull
    private LiveData<List<UnitEntry>> unitsLiveData;
    private LiveData<List<UnitEntry>> unitsFilteredLiveData;
    private MutableLiveData<String> filterLiveData = new MutableLiveData<>("");
    ;
    
    public SharedViewModel(Application application) {
        super(application);
        repository = Repository.getInstance(application);
        unitsLiveData = repository.getAllUnitsLiveData();
    }
    
    public void setFilterLiveData(String filter) {
        String f;
        if (filter.isEmpty()) {
            f = "%";
        } else {
            f = "%" + filter + "%";
        }
        filterLiveData.setValue(f);
    }
    
    @NonNull
    public LiveData<List<UnitEntry>> getFilteredUnitsLiveData() {
        Log.d(TAG, "getFilteredUnitsLiveData: ");
        return Transformations.switchMap(filterLiveData, filter -> repository.getFilteredUnitsLiveData(filter));
    }
    
    @NonNull
    public MutableLiveData<String> getFilterLiveData() {
        Log.d(TAG, "getFilterLiveData = " + filterLiveData.getValue());
        return filterLiveData;
    }
    
    @NonNull
    public LiveData<List<UnitEntry>> getAllUnitsLiveData() {
        return unitsLiveData;
    }
}
