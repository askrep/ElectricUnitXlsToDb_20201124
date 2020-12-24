package com.kas.electricunitxlstodb_20201124.ui;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.preference.PreferenceManager;

import com.kas.electricunitxlstodb_20201124.R;
import com.kas.electricunitxlstodb_20201124.Repository;

public class PreferencesViewModel extends AndroidViewModel {
    private static final String TAG = "#_PREF_VM";
    private final Repository repository;
    private Application application;

    public PreferencesViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        repository = Repository.getInstance(application);
    }

    /*NOT WORK*/
    public boolean isThemeModeState() {
        //return repository.isPrefThemeMode(application);
        return false;
    }
    /*NOT WORK*/
    public boolean isEditModeState() {
        //return repository.isPrefEditModeState(application);
        Log.d(TAG, "isEditModeState: " + PreferenceManager.getDefaultSharedPreferences(application).getString(String.valueOf(R.string.pref_edit_mode), "false"));
        return PreferenceManager.getDefaultSharedPreferences(application).getBoolean(String.valueOf(R.string.pref_edit_mode), false);

    }

    public void setThemeModeDarkOn() {
        repository.setThemeModeDarkOn();
    }

    public void setThemeModeDarkOff() {
        repository.setThemeModeDarkOff();
    }
}
