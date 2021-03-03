package com.kas.electricunitxlstodb_20201124.menu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.kas.electricunitxlstodb_20201124.R;
import com.kas.electricunitxlstodb_20201124.viewmodels.PreferencesViewModel;
import com.kas.electricunitxlstodb_20201124.viewmodels.SharedViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

    final static private String TAG = "#_SETTINGS_FRAGMENT";
    static final private int OPEN_DIRECTORY_REQUEST_CODE = 364;

    private SettingsViewModel settingsViewModel;
    private SharedViewModel sharedViewModel;
    private PreferencesViewModel preferencesViewModel;

    private Preference clearData;
    private Preference updateData;
    private Preference addData;

    private SwitchPreferenceCompat themeSwitch;
    private SwitchPreferenceCompat editModeSwitch;

    @Inject
    public SettingsFragment() {
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        preferencesViewModel = new ViewModelProvider(this).get(PreferencesViewModel.class);
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        addData = findPreference(getString(R.string.pref_add_data));
        clearData = findPreference(getString(R.string.pref_clear_data));
        updateData = findPreference(getString(R.string.pref_update_data));

        addData.setOnPreferenceClickListener(preference -> {
            getIntentLoadUriData();
            return true;
        });

        clearData.setOnPreferenceClickListener(preference -> {
            sharedViewModel.deleteAll();
            Toast.makeText(getContext(), "Data cleared", Toast.LENGTH_SHORT).show();
            return true;
        });

        updateData.setOnPreferenceClickListener(preference -> {
            sharedViewModel.deleteAll();
            getIntentLoadUriData();
            Toast.makeText(getContext(), "Data updated", Toast.LENGTH_SHORT).show();
            return true;
        });
        // CHANGING APP THEME
        themeSwitch = findPreference(getString(R.string.pref_theme_dark));
        editModeSwitch = findPreference(getString(R.string.pref_edit_mode));
        Log.d(TAG, "onCreatePreferences: EDIT" + editModeSwitch.getSummary());
        initThemeSwitch();
    }

    private void getIntentLoadUriData() {
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
    }

    private void initThemeSwitch() {
        if (themeSwitch != null) {
            themeSwitch.setOnPreferenceChangeListener((preference, newValue) -> {
                if ((boolean) newValue) {
                    preferencesViewModel.setThemeModeDarkOn();
                } else {
                    preferencesViewModel.setThemeModeDarkOff();
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
                    String fileDisplayName = settingsViewModel.getFileName(uri);

                    if (settingsViewModel.checkIsExcelFile(fileDisplayName)) {
                        settingsViewModel.readContentFromExcel(uri);
                        addData.setSummary(fileDisplayName);
                        Toast.makeText(getContext(), "Data added", Toast.LENGTH_SHORT).show();
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
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference.equals(findPreference(getString(R.string.pref_add_data)))) {
            preference.setSummary("Summary");
        }
        return true;
    }
}