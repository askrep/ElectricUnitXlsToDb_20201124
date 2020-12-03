package com.kas.electricunitxlstodb_20201124;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory;

import com.kas.electricunitxlstodb_20201124.dao.AppDatabase;
import com.kas.electricunitxlstodb_20201124.ui.DetailsViewModel;

public class DetailsViewModelFactory extends NewInstanceFactory {
    AppDatabase database;
    int unitId;

    public DetailsViewModelFactory(AppDatabase database, int unitId) {
        this.database = database;
        this.unitId = unitId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailsViewModel(database, unitId);
    }
}
