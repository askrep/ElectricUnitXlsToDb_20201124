package com.kas.electricunitxlstodb_20201124.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kas.electricunitxlstodb_20201124.databinding.MainActivityBinding;

public class MainViewModel extends ViewModel {


    private MainActivityBinding mainActivityBinding;


    public void setActivityBinding(MainActivityBinding mainActivityBinding) {
        this.mainActivityBinding = mainActivityBinding;
    }
}
