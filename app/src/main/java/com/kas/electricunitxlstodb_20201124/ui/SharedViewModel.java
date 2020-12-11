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
    private Repository repository;
    private LiveData<List<UnitEntry>> unitsLiveData;
    private MutableLiveData<String> filterLiveData = new MutableLiveData<>("");
    LiveData<UnitEntry> unitEntry;

    public SharedViewModel(Application application) {
        super(application);
        repository = Repository.getInstance(application);
        unitsLiveData = repository.getAllUnitsLiveData();

    }

    public LiveData<List<UnitEntry>> getUnitsLiveData() {
        LiveData<List<UnitEntry>> listLiveData = Transformations.switchMap(filterLiveData,
                string -> (string == null || string.isEmpty()) ? getAllUnitsLiveData() : getFilteredUnitsLiveData());
        Log.d(TAG, "getFilteredUnitsLiveData: ");
        return listLiveData;
    }

    @NonNull
    public LiveData<List<UnitEntry>> getAllUnitsLiveData() {
        return unitsLiveData;
    }

    @NonNull
    public LiveData<List<UnitEntry>> getFilteredUnitsLiveData() {
        LiveData<List<UnitEntry>> listLiveData = Transformations.switchMap(filterLiveData, filter -> repository.getFilteredUnitsLiveData(filter));
        Log.d(TAG, "getFilteredUnitsLiveData: ");
        return listLiveData;
    }

    public void setFilterLiveData(String filter) {
        String f;
        if (filter.isEmpty()) {
            f = "%";
        } else {
            f = "%" + filter + "%";
        }
        filterLiveData.postValue(f);
    }

    @NonNull
    public LiveData<String> getFilterLiveData() {
        return filterLiveData;
    }


    public void insertUnit(UnitEntry unitEntry){
        repository.insertUnit(unitEntry);
    }

    public LiveData<UnitEntry> getUnitEntry(int id) {
        return repository.getUnitById(id);
    }

    public void updateUnit(UnitEntry unitEntry) {
        repository.updateUnit(unitEntry);
    }

    public void deleteUnit(int unitId) {
        repository.deleteUnit(unitId);
    }
}
