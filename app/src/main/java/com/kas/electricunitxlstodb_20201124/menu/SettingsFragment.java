package com.kas.electricunitxlstodb_20201124.menu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.kas.electricunitxlstodb_20201124.AppExecutors;
import com.kas.electricunitxlstodb_20201124.R;
import com.kas.electricunitxlstodb_20201124.data.TableUtil;

import java.io.IOException;

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

    final static private String TAG = "#_SETTINGS_FRAGMENT";
    static final private int OPEN_DIRECTORY_REQUEST_CODE = 364;

    private Preference loadData;
    private SwitchPreferenceCompat themeSwitch;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        loadData = findPreference(getString(R.string.pref_load_data));
        loadData.setPersistent(true);
        initLoadData();

        // CHANGING APP THEME
        themeSwitch = findPreference(getString(R.string.pref_theme_dark));
        initThemeSwitch();
    }

    private void initLoadData() {
        loadData.setOnPreferenceClickListener(preference -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            intent.setType("application/vnd.ms-excel"); //application/*
            String[] mimeTypes = new String[]{"application/vnd.ms-excel,application/*"}; //{"application/x-binary,application/octet-stream"}
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(Intent.createChooser(intent, "messageTitle"), OPEN_DIRECTORY_REQUEST_CODE);
            } else {
                Log.d(TAG, "Unable to resolve Intent.ACTION_OPEN_DOCUMENT {}");
            }
            return true;
        });
    }

    private void initThemeSwitch() {
        if (themeSwitch != null) {
            themeSwitch.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean switchValue = (boolean) newValue;
                if (switchValue) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                return true;
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent resultData) {
        // The ACTION_OPEN_DOCUMENT intent was sent with the request code OPEN_DIRECTORY_REQUEST_CODE.
        // If the request code seen here doesn't match, it's the response to some other intent,
        // and the below code shouldn't run at all.
        if (requestCode == OPEN_DIRECTORY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // The document selected by the user won't be returned in the intent.
                // Instead, a URI to that document will be contained in the return intent
                // provided to this method as a parameter.
                if (resultData != null && resultData.getData() != null) {

                    Uri uri = resultData.getData();
                    String fileDisplayName = TableUtil.getFileDisplayName(getContext(), uri);

                    if (TableUtil.checkIfExcelFile(fileDisplayName)) {

                        AppExecutors.getInstance().diskIO().execute(() -> {
                            try {
                                TableUtil.readContentFromTable(getContext(), uri);
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.d(TAG, "Failed read file " + e.getMessage());
                            }
                        });
                        loadData.setSummary(fileDisplayName);
                    }
                } else {
                    Log.d(TAG, "File uri not found {}");
                }
            } else {
                Log.d(TAG, "User cancelled file browsing {}");
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference.equals(findPreference(getString(R.string.pref_load_data)))) {
            preference.setSummary("Summary");
        }
        return true;
    }
}