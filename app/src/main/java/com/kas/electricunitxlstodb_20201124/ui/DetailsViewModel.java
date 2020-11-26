package com.kas.electricunitxlstodb_20201124.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kas.electricunitxlstodb_20201124.dao.AppDatabase;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

public class DetailsViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    LiveData<UnitEntry> unitEntry;

    public DetailsViewModel(AppDatabase database, int unitId) {
        unitEntry = database.unitDao().loadUnitById(unitId);
    }

    public LiveData<UnitEntry> getUnitEntry(){
        return unitEntry;
    }
}