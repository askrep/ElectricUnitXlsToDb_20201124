package com.kas.electricunitxlstodb_20201124;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;

import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;
import com.kas.electricunitxlstodb_20201124.data.LocalData;
import com.kas.electricunitxlstodb_20201124.data.ParseXlsxTableUtil;
import com.kas.electricunitxlstodb_20201124.data.RemoteData;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class Repository {

    private static final String TAG = "#_REPOSITORY";
    private LocalData localData;
    private RemoteData remoteData;
    private LiveData<List<UnitEntry>> unitsLiveData;

    @Inject
    public Repository(LocalData localData, RemoteData remoteData) {
        this.localData = localData;
        this.remoteData = remoteData;

        unitsLiveData = localData.selectAll();
    }

    public List<List<String>> parseInputStreamToSheetMap(InputStream inputStream) throws IOException {
        return localData.parseInputStreamToSheetMap(inputStream);
    }

    public List<UnitEntry> getUnitEntryListFromInputStream(InputStream inputStream) throws IOException {
        return localData.getUnitEntryListFromInputStream(inputStream);
    }

    public LiveData<UnitEntry> getUnitById(int id) { //database.unitDao().loadUnitById(unitId);;
        return localData.loadUnitById(id);
    }

    public LiveData<List<UnitEntry>> getAllUnitsLiveData() {
        return unitsLiveData;
    }

    public LiveData<List<UnitEntry>> getFilteredUnitsLiveData(String filter) {
        return localData.loadUnitListFiltered(filter);
    }

    public void insertUnit(UnitEntry unitEntry) {
        localData.insertUnit(unitEntry);
    }

    public void updateUnit(UnitEntry unitEntry) {
        localData.updateUnit(unitEntry);
    }

    public void deleteUnit(int unitId) {
        localData.deleteUnit(unitId);
    }

    public void deleteAll() {
        localData.deleteAll();
    }

    public String getFileDisplayName(Context applicationContext, Uri uri) {
        return localData.getFileDisplayName(applicationContext, uri);
    }
}
