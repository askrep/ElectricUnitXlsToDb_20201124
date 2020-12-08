package com.kas.electricunitxlstodb_20201124.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<String> filterLiveData;

    public MutableLiveData<String> getFilter() {
        if (filterLiveData == null) {
            filterLiveData = new MutableLiveData<>();
        }
        return filterLiveData;
    }
}