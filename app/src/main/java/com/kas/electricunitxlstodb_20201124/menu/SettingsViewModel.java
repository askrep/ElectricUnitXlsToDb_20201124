package com.kas.electricunitxlstodb_20201124.menu;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.kas.electricunitxlstodb_20201124.AppExecutors;
import com.kas.electricunitxlstodb_20201124.Repository;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SettingsViewModel extends AndroidViewModel {
    private static final String TAG = "#_SETTINGS_VM";
    private Application application;
    private Repository repository;

    @Inject
    public SettingsViewModel(@NonNull Application application, Repository repository) {
        super(application);
        this.application = application;
        this.repository = repository;
    }

    //TODO Use new parse table method
    public void getContentFromXlsx(Uri uri) {
        AppExecutors.getInstance().diskIO().execute(() -> {

        });
    }

    public void readContentFromExcel(Uri uri) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            try {
                InputStream inputStream = getApplication().getContentResolver().openInputStream(
                        uri); // Uri uri
                List<UnitEntry> unitEntries = repository.getUnitEntryListFromInputStream(inputStream);
                for (UnitEntry entry : unitEntries) {
                    repository.insertUnit(entry);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Failed read file " + e.getMessage());
            }
        });
    }

    public boolean checkIfExcelFile(String fileName) {
        if (fileName == null) {
            return false;
        }
        boolean isExcelFile = fileName.contains("xls") || fileName.contains("xlsx");
        return isExcelFile;
    }

    public String getFileName(Uri uri) {
        return repository.getFileDisplayName(application.getApplicationContext(), uri);
    }

    public boolean checkIsExcelFile(String fileName) {
        return checkIfExcelFile(fileName);
    }
}
