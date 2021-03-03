package com.kas.electricunitxlstodb_20201124.menu;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.kas.electricunitxlstodb_20201124.AppExecutors;
import com.kas.electricunitxlstodb_20201124.Repository;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;
import com.kas.electricunitxlstodb_20201124.data.TableUtil;

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

    public String getFileName(Uri uri) {
        return TableUtil.getFileDisplayName(application.getApplicationContext(), uri);
    }

    public boolean checkIsExcelFile(String fileName) {
        return TableUtil.checkIfExcelFile(fileName);
    }

    public void readContentFromExcel(Uri uri) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            try {
                InputStream inputStream = getApplication().getContentResolver().openInputStream(uri); // Uri uri
                List<UnitEntry> unitEntries = TableUtil.readContentFromTable(inputStream, uri);
                for (UnitEntry entry : unitEntries) {
                    repository.insertUnit(entry);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Failed read file " + e.getMessage());
            }
        });
    }
}
