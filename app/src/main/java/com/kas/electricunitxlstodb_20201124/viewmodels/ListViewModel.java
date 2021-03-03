package com.kas.electricunitxlstodb_20201124.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ListViewModel extends ViewModel {

    private static final String LOG_TAG = "#_LIST_ViewModel";

    @NonNull
    private LiveData<List<UnitEntry>> unitsLiveData;

    @Inject
    public ListViewModel() {
    }

}