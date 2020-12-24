package com.kas.electricunitxlstodb_20201124.menu;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.kas.electricunitxlstodb_20201124.AppExecutors;
import com.kas.electricunitxlstodb_20201124.data.TableUtil;

import java.io.IOException;

public class SettingsViewModel extends AndroidViewModel {
    private static final String TAG = "#_SETTINGS_VM";
    private Application application;

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        this.application = application ;
    }

    public String getFileName(Uri uri) {
        return TableUtil.getFileDisplayName(application.getApplicationContext(), uri);
    }

    public boolean checkIsExcelFile(String fileName) {
        return TableUtil.checkIfExcelFile(fileName);
    }

    public void readContentFromExcel(Uri uri) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            try {
                TableUtil.readContentFromTable(application.getApplicationContext(), uri);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Failed read file " + e.getMessage());
            }
        });
    }
}
