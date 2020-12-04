package com.kas.electricunitxlstodb_20201124.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MutableLiveData<String> filterLiveData = new MutableLiveData<>();

    public void setFilter(String filter){
        filterLiveData.setValue(filter);
    }

    public LiveData<String> getFilter() {
        return filterLiveData;
    }


}
