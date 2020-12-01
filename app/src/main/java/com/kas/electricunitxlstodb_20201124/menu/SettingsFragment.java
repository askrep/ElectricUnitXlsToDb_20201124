package com.kas.electricunitxlstodb_20201124.menu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.kas.electricunitxlstodb_20201124.R;
import com.kas.electricunitxlstodb_20201124.data.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SettingsFragment extends PreferenceFragmentCompat {

    final static private String LOG_TAG = "# SETTINGS FRAGMENT";
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

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                intent.setType("application/*");
                String[] mimeTypes = new String[]{"application/*,application/*"}; //{"application/x-binary,application/octet-stream"}
                if (mimeTypes.length > 0) {
                    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                }

                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(Intent.createChooser(intent, "messageTitle"), OPEN_DIRECTORY_REQUEST_CODE);
                } else {
                    Log.d(LOG_TAG, "Unable to resolve Intent.ACTION_OPEN_DOCUMENT {}");
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
                // provided to this method as a parameter.  Pull that uri using "resultData.getData()"
                if (resultData != null && resultData.getData() != null) {
                    new Util.CopyFileToAppDirTask(getContext()).execute(resultData.getData());
                } else {
                    Log.d(LOG_TAG, "File uri not found {}");
                }
            } else {
                Log.d(LOG_TAG, "User cancelled file browsing {}");
            }
        }
    }

}