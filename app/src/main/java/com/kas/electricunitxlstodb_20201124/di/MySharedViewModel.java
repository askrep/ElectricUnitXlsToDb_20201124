package com.kas.electricunitxlstodb_20201124.di;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.kas.electricunitxlstodb_20201124.AppExecutors;
import com.kas.electricunitxlstodb_20201124.UnitRepository;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import java.util.List;

public class MySharedViewModel extends ViewModel {
    private static final String TAG = "#_MY-SHARED_VM";
    private UnitRepository myRepository;

    private LiveData<List<UnitEntry>> unitsLiveData;
    private MutableLiveData<String> filterLiveData = new MutableLiveData<>("");

    public MySharedViewModel(UnitRepository myRepository) {
        this.myRepository = myRepository;
        unitsLiveData = myRepository.getAllUnitsLiveData();
    }

    public LiveData<List<UnitEntry>> getUnitsLiveData() {
        Function<String, LiveData<List<UnitEntry>>> stringLiveDataFunction;
        stringLiveDataFunction = string -> (string == null || string.isEmpty()) ? getAllUnitsLiveData() : getFilteredUnitsLiveData();
        return Transformations.switchMap(filterLiveData, stringLiveDataFunction);
    }

    @NonNull
    public LiveData<List<UnitEntry>> getAllUnitsLiveData() {
        return unitsLiveData;
    }

    @NonNull
    public LiveData<List<UnitEntry>> getFilteredUnitsLiveData() {
        LiveData<List<UnitEntry>> listLiveData = Transformations.switchMap(filterLiveData,
                filter -> myRepository.getFilteredUnitsLiveData(filter));
        return listLiveData;
    }

    public void setFilterLiveData(String filter) {
        String f = filter.isEmpty() ? "%" : "%" + filter + "%";
        filterLiveData.postValue(f);
    }

    public LiveData<UnitEntry> getUnitEntry(int id) {
        return myRepository.getUnitById(id);
    }

    public void insertUnit(UnitEntry unitEntry) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            myRepository.insertUnit(unitEntry);
        });
    }

    public void updateUnit(UnitEntry unitEntry) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            myRepository.updateUnit(unitEntry);
        });
    }

    public void deleteUnit(int unitId) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            myRepository.deleteUnit(unitId);
        });
    }

    public void deleteAll() {
        AppExecutors.getInstance().diskIO().execute(() -> {
            myRepository.deleteAll();
        });
    }

    @NonNull
    public LiveData<String> getFilterLiveData() {
        return filterLiveData;
    }
}
