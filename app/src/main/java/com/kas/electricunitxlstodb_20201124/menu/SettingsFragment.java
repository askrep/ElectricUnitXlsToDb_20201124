package com.kas.electricunitxlstodb_20201124.menu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.kas.electricunitxlstodb_20201124.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    final static private String LOG_TAG = "# SETTINGS FRAGMENT";
    static final private int OPEN_DIRECTORY_REQUEST_CODE = 364;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        Preference loadData = null;
        try {
            loadData = findPreference(getString(R.string.pref_load_data));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "Preference link ==" + loadData);
        if (loadData != null) {
            loadData.setOnPreferenceClickListener(preference -> {

                Intent loadIntent = new Intent();
                loadIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                loadIntent.addCategory(Intent.CATEGORY_OPENABLE);
                loadIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                loadIntent.setType("application/*");
                String[] mimeTypes = new String[]{"application/x-binary,application/octet-stream"};
                if (mimeTypes.length > 0) {
                    loadIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                }

                if (loadIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(Intent.createChooser(loadIntent, "messageTitle"), OPEN_DIRECTORY_REQUEST_CODE);
                } else {
                    Log.d(LOG_TAG,"Unable to resolve Intent.ACTION_OPEN_DOCUMENT {}");
                }
                return true;
            });
        }
    }
}