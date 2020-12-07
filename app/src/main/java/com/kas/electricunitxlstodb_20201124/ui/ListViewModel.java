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
    private LiveData<String> filterText;
    private final AppDatabase database;

    public ListViewModel(@NonNull Application application) {
        super(application);

        database = AppDatabase.getInstance(this.getApplication());
        units = database.unitDao().selectAll();

        Log.d(LOG_TAG, "Load task from database");
    }

    public void setFilter(String filter) {
        if (filter != null && filter.length() > 0) {
            units = database.unitDao().loadUnitListFiltered("%" + filter + "%");

            Log.d(LOG_TAG, "FILTER SETUP");
        }
    }

    public LiveData<String> getFilter() {
        return filterText;
    }

    public void setUnits(List<UnitEntry> units) {

    }

    public LiveData<List<UnitEntry>> getUnits() {
        return units;
    }


}