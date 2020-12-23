package com.kas.electricunitxlstodb_20201124.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.kas.electricunitxlstodb_20201124.Repository;

public class PreferencesViewModel extends AndroidViewModel {
    private static final String TAG = "#_PREF_VM";
    private final Repository repository;
    private final Application application;

    public PreferencesViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        repository = Repository.getInstance(application);
    }

    public boolean isThemeModeState() {
        return repository.isPrefThemeMode(application);
    }

    public boolean isEditModeState() {
        return repository.isPrefEditModeState(application);
    }

    public void setThemeModeDarkOn() {
        repository.setThemeModeDarkOn();
    }

    public void setThemeModeDarkOff() {
        repository.setThemeModeDarkOff();
    }
}
