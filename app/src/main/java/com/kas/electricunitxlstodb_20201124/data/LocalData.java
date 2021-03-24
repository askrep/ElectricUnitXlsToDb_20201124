package com.kas.electricunitxlstodb_20201124.data;

import android.net.Uri;

import androidx.lifecycle.LiveData;

import com.kas.electricunitxlstodb_20201124.dao.UnitDao;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

public class LocalData {

    private UnitDao unitDao;
    private TableUtils tableUtils;
    private XlsxToRowListParser tableParser;

    @Inject
    public LocalData(UnitDao unitDao,  XlsxToRowListParser tableParser,TableUtils tableUtils) {
        this.unitDao = unitDao;
         this.tableParser = tableParser;
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

    public List<UnitEntry> parseXlsxInputStreamToUnitEntryList(InputStream inputStream) throws IOException {
         List<List<String>> lists = tableParser.parseTable(inputStream);
        return UnitEntryUtils.getUnitEntryListFromRowList(lists);
    }
    public List<UnitEntry> getUnitEntryListFromInputStream(InputStream inputStream) throws IOException {
        return tableUtils.getUnitEntryListFromInputStream(inputStream);
    }


    public String getFileDisplayName(Uri uri) {
         return tableUtils.getFileNameFromUri(uri);
    }
}