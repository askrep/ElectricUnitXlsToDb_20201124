package com.kas.electricunitxlstodb_20201124.viewmodels;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.kas.electricunitxlstodb_20201124.AppExecutors;
import com.kas.electricunitxlstodb_20201124.Repository;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SharedViewModel extends ViewModel {

    private static final String TAG = "#_SHARED_VM";
    private Repository repository;

    private LiveData<List<UnitEntry>> unitsLiveData;
    private MutableLiveData<String> filterLiveData = new MutableLiveData<>("");

    @Inject
    public SharedViewModel(Repository repository) {
        this.repository = repository;
        unitsLiveData = repository.getAllUnitsLiveData();
    }

    public LiveData<List<UnitEntry>> getUnitsLiveData() {
        Function<String, LiveData<List<UnitEntry>>> stringLiveDataFunction;
        stringLiveDataFunction = string -> (string == null ||
                string.isEmpty()) ? getAllUnitsLiveData() : getFilteredUnitsLiveData();
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

}
