package com.kas.electricunitxlstodb_20201124.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kas.electricunitxlstodb_20201124.Repository;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    @NonNull
    private Repository repository;

    @NonNull
    private LiveData<List<UnitEntry>> unitsLiveData;
    private LiveData<List<UnitEntry>> unitsFilteredLiveData;
    private MutableLiveData<String> filter;

    public MainViewModel(Application application) {
        super(application);
        repository = new Repository(application);

        unitsLiveData = repository.getAllUnitsLiveData();
        // unitsFilteredLiveData = repository.getUnitListFilter(filter.getValue());

    }

    @NonNull
    public LiveData<List<UnitEntry>> getUnitsLiveData() {
        return unitsLiveData;
    }

    @NonNull
    public LiveData<List<UnitEntry>> getUnitsFilteredLiveData() {
        return unitsFilteredLiveData;
    }

    @NonNull
    public MutableLiveData<String> getFilter() {
        if (filter == null) {
            filter = new MutableLiveData<>();
        }
        return filter;
    }
}
