package com.kas.electricunitxlstodb_20201124.viewmodels;

import androidx.lifecycle.ViewModel;

import com.kas.electricunitxlstodb_20201124.data.PreferencesUtil;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PreferencesViewModel extends ViewModel {

    private static final String TAG = "#_PREF_VM";
//    private final Repository repository;

    @Inject
    public PreferencesViewModel() {
       // this.repository = repository;
    }

/*    public boolean isThemeModeState() {
        Log.d(TAG, "isPrefThemeMode: " + PreferencesUtil.isThemeModeDark(application));
        return PreferencesUtil.isThemeModeDark(application);
    }*/

/*    public boolean isEditModeState() {
        Log.d(TAG, "isPrefEditModeState: " + PreferencesUtil.isEditMode(application));
        return PreferencesUtil.isEditMode(application);
    }*/

    public void setThemeModeDarkOn() {
        PreferencesUtil.setThemeModeDarkOn();
    }

    public void setThemeModeDarkOff() {
        PreferencesUtil.setThemeModeDarkOff();
    }
}
