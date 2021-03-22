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

    //TODO Refactor table utils if needed or delete
    public List<UnitEntry> getUnitEntryListFromInputStream(InputStream inputStream) throws IOException {

        if (inputStream != null) {
            Row row;
            XSSFSheet sheet;
            List<UnitEntry> unitList = new ArrayList<>();
            /**read table*/
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            Iterator<XSSFSheet> sheetIterator = workbook.iterator();

            /**read all sheets*/
            while (sheetIterator.hasNext()) {
                sheet = sheetIterator.next();
                Iterator<Row> rowIterator = sheet.iterator();

                /**read all rows*/
                while (rowIterator.hasNext()) {
                    row = rowIterator.next();

                    Iterator<Cell> cellIterator = row.cellIterator();
                    UnitEntry unitEntry = new UnitEntry(null, null, null,null);
                    /**read all cells*/
                    while (cellIterator.hasNext()) {
                        fillUnitEntry(cellIterator, unitEntry);
                    }

                    unitList.add(unitEntry);
                }
            }
            inputStream.close();
            return unitList;
        }
        return null;
    }

    private void fillUnitEntry(@NotNull Iterator<Cell> cellIterator, UnitEntry unitEntry) {
        Cell cell = cellIterator.next();
        String location;
        String cabinet;
        String title;
        String description;

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                String stringCellValue = cell.getStringCellValue();

                switch (cell.getColumnIndex()) {

                    case 0:
                        cabinet = stringCellValue;
                        unitEntry.setCabinet(cabinet.trim());
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
