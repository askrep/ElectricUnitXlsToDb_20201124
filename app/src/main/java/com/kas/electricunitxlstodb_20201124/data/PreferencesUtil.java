package com.kas.electricunitxlstodb_20201124.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

public class PreferencesUtil {
    
    private static final String TAG = "#_PREFERENCES_UTIL";
    
    /**
     * Returns the theme mode currently set in Application.
     * Light mode == "false", Dark Mode == "true"
     *
     * @param context Context used to get the SharedPreferences
     */
    public static boolean isThemeModeDark(Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getBoolean("Dark-Theme", false); // R.string.pref_theme_dark
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
    public static boolean isEditMode(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isEditMode = sharedPreferences.getBoolean("Edit-Mode", false);
        Log.d(TAG, "isEditMode: " + isEditMode);
        return isEditMode;
    }
}
