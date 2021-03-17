package com.kas.electricunitxlstodb_20201124.data;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;

import com.kas.electricunitxlstodb_20201124.dao.UnitDao;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class LocalData {

    private UnitDao unitDao;
    private TableUtils tableUtils;

    @Inject
    public LocalData(UnitDao unitDao, TableUtils tableUtils) {
        this.unitDao = unitDao;
        this.tableUtils = tableUtils;
    }

    public LiveData<UnitEntry> loadUnitById(int id) {
        return unitDao.loadUnitById(id);
    }

    public LiveData<List<UnitEntry>> selectAll() {
        return unitDao.selectAll();
    }

    public LiveData<List<UnitEntry>> loadUnitListFiltered(String filter) {
        return unitDao.loadUnitListFiltered(filter);
    }

    public void insertUnit(UnitEntry unitEntry) {
        unitDao.insertUnit(unitEntry);
    }

    public void updateUnit(UnitEntry unitEntry) {
        unitDao.updateUnit(unitEntry);
    }

    public void deleteUnit(int unitId) {
        unitDao.deleteUnit(unitId);
    }

    public void deleteAll() {
        unitDao.deleteAll();
    }

    public Map<String, List<String[]>> parseInputStreamToSheetMap(InputStream inputStream) throws IOException {
        return ParseXlsxTableUtil.parseXlsxToSheetMap(inputStream);
    }

    public List<UnitEntry> getUnitEntryListFromInputStream(InputStream inputStream) throws IOException {
        return tableUtils.getUnitEntryListFromInputStream(inputStream);
    }


    public String getFileDisplayName(Context applicationContext, Uri uri) {
         return tableUtils.getFileName(uri);
    }
}