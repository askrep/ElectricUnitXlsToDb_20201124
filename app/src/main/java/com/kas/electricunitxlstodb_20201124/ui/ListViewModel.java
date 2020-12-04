package com.kas.electricunitxlstodb_20201124.ui;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kas.electricunitxlstodb_20201124.dao.AppDatabase;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import java.util.List;

public class ListViewModel extends AndroidViewModel {

    private static final String LOG_TAG = "# LIST ViewModel";
    private LiveData<List<UnitEntry>> units;
    private final AppDatabase database;

    public ListViewModel(@NonNull Application application) {
        super(application);

        database = AppDatabase.getInstance(this.getApplication());
        Log.d(LOG_TAG, "Load task from database");
        //tasks = database.unitDao().loadUnitListByText("%2%");
        units = database.unitDao().selectAll();
    }

    public void setUnitsFilter(String filter) {
        if (filter != null && filter.length() > 0)
            units = database.unitDao().loadUnitListFiltered("%" + filter + "%");
    }

    public void setUnits(List<UnitEntry> units) {

    }

    public LiveData<List<UnitEntry>> getUnits() {
        return units;
    }
}