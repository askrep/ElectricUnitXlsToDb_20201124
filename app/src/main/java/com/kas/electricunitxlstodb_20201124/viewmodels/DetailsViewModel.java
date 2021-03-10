package com.kas.electricunitxlstodb_20201124.viewmodels;

import android.app.Application;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.AndroidViewModel;

public class DetailsViewModel extends AndroidViewModel {

    private Application application;

    @ViewModelInject
    public DetailsViewModel(Application application) {
        super(application);
        this.application = application;

    }

}