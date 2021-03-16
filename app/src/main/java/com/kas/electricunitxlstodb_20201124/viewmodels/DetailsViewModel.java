package com.kas.electricunitxlstodb_20201124.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DetailsViewModel extends AndroidViewModel {

    private Application application;

    @Inject
    public DetailsViewModel(Application application) {
        super(application);
        this.application = application;

    }

}