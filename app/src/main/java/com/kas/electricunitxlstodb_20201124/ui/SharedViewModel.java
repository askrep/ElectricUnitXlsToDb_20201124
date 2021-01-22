package com.kas.electricunitxlstodb_20201124.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.preference.PreferenceManager;

import com.kas.electricunitxlstodb_20201124.AppExecutors;
import com.kas.electricunitxlstodb_20201124.ElectricalUnitApplication;
import com.kas.electricunitxlstodb_20201124.R;
import com.kas.electricunitxlstodb_20201124.Repository;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import java.util.List;

public class SharedViewModel extends AndroidViewModel {
    
    private static final String TAG = "#_SHARED_VM";
    private Repository repository;
    private Application context;
    private LiveData<List<UnitEntry>> unitsLiveData;
    private MutableLiveData<String> filterLiveData = new MutableLiveData<>("");
    @ViewModelInject
    public SharedViewModel(ElectricalUnitApplication application) {
        super(application);
        repository = Repository.getInstance(application);
        context = application;
        unitsLiveData = repository.getAllUnitsLiveData();
    }
    
    public LiveData<List<UnitEntry>> getUnitsLiveData() {
        Function<String, LiveData<List<UnitEntry>>> stringLiveDataFunction;
        stringLiveDataFunction = string -> (string == null || string.isEmpty()) ? getAllUnitsLiveData() : getFilteredUnitsLiveData();
        LiveData<List<UnitEntry>> listLiveData = Transformations.switchMap(filterLiveData,
                                                                           stringLiveDataFunction);
        return listLiveData;
    }
    
    @NonNull
    public LiveData<List<UnitEntry>> getAllUnitsLiveData() {
        return unitsLiveData;
    }
    
    @NonNull
    public LiveData<List<UnitEntry>> getFilteredUnitsLiveData() {
        LiveData<List<UnitEntry>> listLiveData = Transformations.switchMap(filterLiveData,
                                                                           filter -> repository.getFilteredUnitsLiveData(filter));
        return listLiveData;
    }
    
    public void setFilterLiveData(String filter) {
        String f = filter.isEmpty() ? "%" : "%" + filter + "%";
        filterLiveData.postValue(f);
    }
    
    public LiveData<UnitEntry> getUnitEntry(int id) {
        return repository.getUnitById(id);
    }
    
    public void insertUnit(UnitEntry unitEntry) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            repository.insertUnit(unitEntry);
        });
    }
    
    public void updateUnit(UnitEntry unitEntry) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            repository.updateUnit(unitEntry);
        });
    }
    
    public void deleteUnit(int unitId) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            repository.deleteUnit(unitId);
        });
    }
    
    public void deleteAll() {
        AppExecutors.getInstance().diskIO().execute(() -> {
            repository.deleteAll();
        });
    }
    
    @NonNull
    public LiveData<String> getFilterLiveData() {
        return filterLiveData;
    }
    
    public boolean isEditModeState() {
        return PreferenceManager.getDefaultSharedPreferences(getApplication()).getBoolean(String.valueOf(R.string.pref_edit_mode), false);
    }
}
