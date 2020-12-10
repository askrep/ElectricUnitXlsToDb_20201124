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
    private MutableLiveData<String> filterLiveData = new MutableLiveData<>("%");
    ;

    public SharedViewModel(Application application) {
        super(application);
        repository = Repository.getInstance(application);
        unitsLiveData = repository.getAllUnitsLiveData();
        //unitsFilteredLiveData = repository.getFilteredUnitsLiveData(filter.getValue());
        unitsFilteredLiveData = Transformations.switchMap(filterLiveData, filter -> repository.getFilteredUnitsLiveData(filter));
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
    public LiveData<List<UnitEntry>> getAllUnitsLiveData() {
        return unitsLiveData;
    }

    @NonNull
    public LiveData<List<UnitEntry>> getFilteredUnitsLiveData() {
        Log.d(TAG, "getFilteredUnitsLiveData: ");
        return repository.getFilteredUnitsLiveData(filterLiveData.getValue());
    }

    @NonNull
    public MutableLiveData<String> getFilterLiveData() {
        if (filterLiveData == null) {
            filterLiveData = new MutableLiveData<>();
        }

        Log.d(TAG, "getFilterLiveData = " + filterLiveData.getValue());
        return filterLiveData;
    }
}
