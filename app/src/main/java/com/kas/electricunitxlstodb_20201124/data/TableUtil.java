package com.kas.electricunitxlstodb_20201124.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TableUtil {

    private static final String TAG = "#_TABLE_UTIL";

    @NonNull
    public static List<UnitEntry> readContentFromTable(InputStream inputStream, final Uri uri) throws IOException {

        List<UnitEntry> unitList = new ArrayList<>();

        Log.d(TAG, "Uri is " + uri.toString());

        if (inputStream != null) {
            Row row;
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet spreadsheet = workbook.getSheetAt(0);

            Iterator<Row> iterator = spreadsheet.iterator();
            Log.d(TAG, "inputStream created");

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

    private static void fillUnitEntry(Iterator<Cell> cellIterator, UnitEntry unitEntry) {
        Cell cell = cellIterator.next();
        String location = "";
        String title;
        String description;

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                String stringCellValue = cell.getStringCellValue();

                if (cell.getColumnIndex() == 0) {
                    location = stringCellValue;
                    unitEntry.setLocation(location.trim());
                }
                if (cell.getColumnIndex() == 1) {
                    title = stringCellValue;
                    unitEntry.setTitle(title.trim());
                }
                if (cell.getColumnIndex() == 2) {
                    description = stringCellValue;
                    unitEntry.setDescription(description.trim());
                }

                Log.d(TAG, "CellType is String: " + stringCellValue + " \t\t ");
                break;
            case Cell.CELL_TYPE_NUMERIC:
                Log.d(TAG, "CellType is Numeric: " + cell.getNumericCellValue() + " \t\t ");
                break;
        }
    }

    // Returns file display name.
    @Nullable
    public static String getFileDisplayName(Context context, final Uri uri) {
        String displayName = null;
        try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null,
                null)) {
            if (cursor != null && cursor.moveToFirst()) {
                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                Log.i(TAG, "Display Name {}" + displayName);

            }
        }
        return displayName;
    }

    public static boolean checkIfExcelFile(String fileName) {
        if (fileName == null) {
            return false;
        }
        boolean isExcelFile = fileName.contains("xls") || fileName.contains("xlsx");
        Log.d(TAG, "checkIfExcelFile == " + isExcelFile);
        return isExcelFile;
    }
}
