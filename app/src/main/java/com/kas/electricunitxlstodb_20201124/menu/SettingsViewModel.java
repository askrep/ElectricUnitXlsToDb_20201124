package com.kas.electricunitxlstodb_20201124.menu;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.kas.electricunitxlstodb_20201124.AppExecutors;
import com.kas.electricunitxlstodb_20201124.data.TableUtil;

import java.io.IOException;

public class SettingsViewModel extends AndroidViewModel {
    private static final String TAG = "#_SETTINGS_VM";

    public SettingsViewModel(@NonNull Application application) {
        super(application);
    }

    //TODO "String fileDisplayName = TableUtil.getFileDisplayName(getContext(), uri);"
    public String getFileName(Context context, Uri uri) {
        return TableUtil.getFileDisplayName(context, uri);
    }

    //TODO "if (TableUtil.checkIfExcelFile(fileDisplayName))"
    public boolean checkIsExcelFile(String fileName) {
        return TableUtil.checkIfExcelFile(fileName);
    }

    //TODO "TableUtil.readContentFromTable(getContext(), uri);"
    public void readContentFromExcel(Context context, Uri uri) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            try {
                TableUtil.readContentFromTable(context, uri);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Failed read file " + e.getMessage());
            }
        });
    }
}
