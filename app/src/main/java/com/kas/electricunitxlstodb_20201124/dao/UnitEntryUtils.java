package com.kas.electricunitxlstodb_20201124.dao;

import java.util.ArrayList;
import java.util.List;

public class UnitEntryUtils {
    public static UnitEntry getUnitEntryWithVariousStringFields(List<String> fields) {
        UnitEntry unitEntry = new UnitEntry(null, null, null, null);
        unitEntry.location = fields.get(0);
        unitEntry.cabinet = fields.get(1);
        unitEntry.title = fields.get(2);
        //todo fix stupid check
        if (fields.size() > 3) {
            unitEntry.description = fields.get(3);
        }
        return unitEntry;
    }

    public static List<UnitEntry> getUnitEntryList(List<List<String>> doubleListsOfString) {
        List<UnitEntry> unitEntryList = new ArrayList<>();
        for (List<String> stringList : doubleListsOfString) {
            unitEntryList.add(getUnitEntryWithVariousStringFields(stringList));
        }
        return unitEntryList;
    }
}
