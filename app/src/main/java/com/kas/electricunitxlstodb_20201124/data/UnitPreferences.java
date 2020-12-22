package com.kas.electricunitxlstodb_20201124.data;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.kas.electricunitxlstodb_20201124.R;

public class UnitPreferences {


    /**
     * Returns the mode currently set in Application.
     * Light mode == "false", Dark Mode == "true"
     *
     * @param context Context used to get the SharedPreferences
     */
    public static boolean getPrefThemeModeState(Context context) {
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

    public static boolean getPrefEditModeState(Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getBoolean(String.valueOf(R.string.pref_edit_enable), false);
    }
}
