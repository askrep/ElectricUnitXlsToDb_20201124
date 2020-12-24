package com.kas.electricunitxlstodb_20201124.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

public class DetailsViewModel extends AndroidViewModel {

    private Application application;

    public DetailsViewModel(Application application) {
        super(application);
        this.application = application;

    }

}