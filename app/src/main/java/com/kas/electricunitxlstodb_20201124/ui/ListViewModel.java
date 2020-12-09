package com.kas.electricunitxlstodb_20201124.ui;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import java.util.List;

public class ListViewModel extends AndroidViewModel {

    private static final String LOG_TAG = "#_LIST_ViewModel";
    @NonNull
    private MutableLiveData<List<UnitEntry>> unitsLiveData = new MutableLiveData<>();

    public ListViewModel(@NonNull Application application) {
        super(application);
        //AppDatabase database = AppDatabase.getInstance(this.getApplication());
        //units = database.unitDao().selectAll();

        Log.d(LOG_TAG, "Load task from database");
    }

    public MutableLiveData<List<UnitEntry>> getUnitsLiveData() {
        return unitsLiveData;
    }

}