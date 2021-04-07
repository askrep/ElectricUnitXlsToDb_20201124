package com.kas.electricunitxlstodb_20201124.data;

import android.util.Log;

import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnitEntryUtils {
    private static final String TAG = "#_UnitEntryUtils";
    public static final int LOCATION_CELL_INDEX = 0;
    public static final int CABINET_CELL_INDEX = 1;
    public static final int TITLE_CELL_INDEX = 2;
    public static final int DESCRIPTION_CELL_INDEX = 3;

    /**
     * Filling fields of the UnitEntry from String List with size == 4
     * Where:
     * fields.get(0) == UnitEntry Location
     * fields.get(1) == UnitEntry Cabinet
     * fields.get(2) == UnitEntry Title
     * fields.get(3) == UnitEntry Description
     *
     * @param unitEntry UnitEntry
     * @param fields    List<String> size==4;
     * @return UnitEntry
     */
    public static UnitEntry fillUnitEntryFromStringList(UnitEntry unitEntry, List<String> fields) {
        if (unitEntry == null || fields == null) {
            Log.d(TAG, "getUnitEntryWithVariousStringFields: UnitEntry == NULL");
            throw new UnsupportedOperationException();
        }

        for (int i = 0; i < fields.size(); i++) {
            switch (i) {
                case 0:
                    unitEntry.setLocation(fields.get(LOCATION_CELL_INDEX));
                    break;
                case 1:
                    unitEntry.setCabinet(fields.get(CABINET_CELL_INDEX));
                    break;
                case 2:
                    unitEntry.setTitle(fields.get(TITLE_CELL_INDEX));
                    break;
                case 3:
                    unitEntry.setDescription(fields.get(DESCRIPTION_CELL_INDEX));
                    break;
                default:
                    Log.d(TAG, "fillUnitEntryFromStringList: Wrong Index");
            }
        }
        return unitEntry;
    }

    /**
     * Return a String List with size==4 from the UnitEntry
     * <p>
     * fields.add(0) == UnitEntry Location
     * fields.add(1) == UnitEntry Cabinet
     * fields.add(2) == UnitEntry Title
     * fields.add(3) == UnitEntry Description
     *
     * @param unitEntry UnitEntry
     * @param fields    List<String> size==4;
     * @return List<String> the list of cells
     */
    public static List<String> getStringListFromUnitEntry(List<String> fields, UnitEntry unitEntry) {
        if (unitEntry == null) {
            Log.d(TAG, "getStringListFromUnitEntry: INPUT==NULL");
            throw new UnsupportedOperationException();
        }
        fields = Arrays.asList(unitEntry.getLocation(),
                unitEntry.getCabinet(),
                unitEntry.getTitle(),
                unitEntry.getDescription());
        return fields;
    }

    /**
     * Return List of filled UnitEntries from two-dimensional List<List<String>>
     * Where first list is a Row list, second (inner) is a Cell list
     * Minimum size == 4 and Title () not empty
     *
     * @param rowList two-dimensional List<List<String>>
     * @return List<UnitEntry> List of filled UnitEntries
     */
    public static List<UnitEntry> getUnitEntryListFromRowList(List<List<String>> rowList) {
        List<UnitEntry> unitEntryList = new ArrayList<>();
        for (List<String> stringList : rowList) {
            Log.d(TAG, "getUnitEntryList: CELL LIST size: " + stringList.size());

            if (stringList.size() >= 4 && !stringList.get(TITLE_CELL_INDEX).isEmpty()) {
                UnitEntry unitEntry = new UnitEntry();
                unitEntryList.add(fillUnitEntryFromStringList(unitEntry, stringList));
            }
        }
        return unitEntryList;
    }

    /**
     * Return a two-dimensional List<List<String>> from the UnitEntry List
     * Where first list is a Row list, second (inner) is a Cell list
     *
     * @param unitEntryList List<UnitEntry>
     * @return List<List < String>> two-dimensional row list
     */
    public static List<List<String>> getRowListFromUnitEntryList(List<UnitEntry> unitEntryList) {
        if (unitEntryList == null) {
            Log.d(TAG, "getRowListFromUnitEntryList: input == NULL");
            throw new UnsupportedOperationException();
        }
        List<List<String>> rowList = new ArrayList<>();
        for (UnitEntry unitEntry : unitEntryList) {
            List<String> fields = new ArrayList<>();
            rowList.add(getStringListFromUnitEntry(fields, unitEntry));
        }
        return rowList;
    }
}
