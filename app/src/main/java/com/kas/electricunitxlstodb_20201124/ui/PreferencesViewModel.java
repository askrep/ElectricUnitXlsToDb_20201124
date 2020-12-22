package com.kas.electricunitxlstodb_20201124.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.kas.electricunitxlstodb_20201124.Repository;

public class PreferencesViewModel extends AndroidViewModel {
    private static final String TAG = "#_MAIN_VM";
    private final Repository repository;
    private final Application application;

    public PreferencesViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        repository = Repository.getInstance(this.application);
    }

    public boolean getThemeModeState() {
        return repository.getPrefThemeMode(application);
    }

    public boolean getPrefEditModeState() {
        return repository.getPrefEditModeState(application);
    }

    public void setThemeModeDarkOn() {
        repository.setThemeModeDarkOn();
    }

    public void setThemeModeDarkOff() {
        repository.setThemeModeDarkOff();
    }
}
