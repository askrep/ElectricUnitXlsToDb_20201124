package com.kas.electricunitxlstodb_20201124.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.kas.electricunitxlstodb_20201124.R;

public class PreferencesUnit {

    private static final String TAG = "#_PREFERENCES_UTIL";

    /**
     * Returns the theme mode currently set in Application.
     * Light mode == "false", Dark Mode == "true"
     *
     * @param context Context used to get the SharedPreferences
     */
    public static boolean isThemeModeDark(Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getBoolean(String.valueOf(R.string.pref_theme_dark), false);
    }

    /**
     * Disable application theme Dark mode.
     */
    public static void setThemeModeDarkOff() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    /**
     * Enable application theme Dark mode.
     */
    public static void setThemeModeDarkOn() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }


    /**
     * Returns the state of edit switch.
     * Edit on == "false", edit off == "true"
     *
     * @param context Context used to get the SharedPreferences
     */
    public static boolean isEditMode(Application application) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(application.getApplicationContext());
        boolean aBoolean = defaultSharedPreferences.getBoolean(String.valueOf(R.string.pref_edit_mode), false);
        Log.d(TAG, "isEditMode: " + aBoolean);
        return aBoolean;
    }
}
