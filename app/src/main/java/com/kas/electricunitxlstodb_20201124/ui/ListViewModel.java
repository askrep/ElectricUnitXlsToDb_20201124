package com.kas.electricunitxlstodb_20201124.ui;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kas.electricunitxlstodb_20201124.dao.AppDatabase;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import java.util.List;

public class ListViewModel extends AndroidViewModel {
    private static final String LOG_TAG = "# LIST ViewModel";
    private LiveData<List<UnitEntry>> tasks;

    public ListViewModel(@NonNull Application application) {
        super(application);

        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(LOG_TAG, "Load task from database");
        tasks = database.unitDao().selectAll();
    }


    public LiveData<List<UnitEntry>> getTasks() {
        return tasks;
    }
    // TODO: Implement the ViewModel
}