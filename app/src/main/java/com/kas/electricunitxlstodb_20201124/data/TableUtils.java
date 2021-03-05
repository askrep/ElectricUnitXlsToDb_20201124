package com.kas.electricunitxlstodb_20201124.data;

import android.net.Uri;
import android.util.Log;

import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TableUtils {

    private static final String TAG = "#_TABLE_UTIL";

    @Inject
    public TableUtils() {
    }

    public List<UnitEntry> getUnitEntryListFromInputStream(InputStream inputStream) throws IOException {

        if (inputStream != null) {
            Row row;
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet spreadsheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = spreadsheet.iterator();
            List<UnitEntry> unitList = new ArrayList<>();

            while (iterator.hasNext()) {
                row = iterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                UnitEntry unitEntry = new UnitEntry(null, null, null);

                while (cellIterator.hasNext()) {
                    fillUnitEntry(cellIterator, unitEntry);
                }

                unitList.add(unitEntry);
            }
            inputStream.close();
            return unitList;
        }
        return null;
    }

    private void fillUnitEntry(@NotNull Iterator<Cell> cellIterator, UnitEntry unitEntry) {
        Cell cell = cellIterator.next();
        String location = "";
        String title;
        String description;

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                String stringCellValue = cell.getStringCellValue();

                switch (cell.getColumnIndex()) {
                    case 0:
                        location = stringCellValue;
                        unitEntry.setLocation(location.trim());
                        break;
                    case 1:
                        title = stringCellValue;
                        unitEntry.setTitle(title.trim());
                        break;
                    case 2:
                        description = stringCellValue;
                        unitEntry.setDescription(description.trim());
                        break;
                }

                Log.d(TAG, "CellType is String: " + stringCellValue + " \t\t ");
                break;
            case Cell.CELL_TYPE_NUMERIC:
                Log.d(TAG, "CellType is Numeric: " + cell.getNumericCellValue() + " \t\t ");
                break;
        }
    }

    /**
     * Return file name from its Uri
     *
     * @param uri of file
     * @return String of file name
     */
    public String getFileName(Uri uri) {
        String[] string = uri.toString().split("%2F");
        return string[string.length - 1];
    }
}
