package com.kas.electricunitxlstodb_20201124.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import java.util.List;

public class ListViewModel extends AndroidViewModel {

    private static final String LOG_TAG = "#_LIST_ViewModel";

    @NonNull
    private LiveData<List<UnitEntry>> unitsLiveData;

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

}