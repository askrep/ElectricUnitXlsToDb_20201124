package com.kas.electricunitxlstodb_20201124.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kas.electricunitxlstodb_20201124.dao.AppDatabase;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class TableUtil {
    
    private static final String TAG = "#_TABLE_UTIL";
    static AppDatabase database;
    
    @NonNull
    public static String readContentFromTable(Context context, final Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri); // Uri uri
        Log.d(TAG, "Uri is " + uri.toString());
        
        database = AppDatabase.getInstance(context);
        if (inputStream != null) {
            Row row;
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            if(workbook== null){
                Log.d(TAG, "readContentFromTable: " + workbook);
                return TAG+" XSSFWorkbook"+" is Null";
            }
            XSSFSheet spreadsheet = workbook.getSheetAt(0);
            
            Iterator<Row> iterator = spreadsheet.iterator();
            Log.d(TAG, "inputStream created");
            while (iterator.hasNext()) {
                row = iterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                UnitEntry unitEntry = new UnitEntry(null, null);
                
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String title;
                    String description;
                    
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            String stringCellValue = cell.getStringCellValue();
                            
                            if (cell.getColumnIndex() == 0) {
                                title = stringCellValue;
                                unitEntry.setTitle(title);
                            }
                            if (cell.getColumnIndex() == 1) {
                                description = stringCellValue;
                                unitEntry.setDescription(description);
                            }
                            
                            Log.d(TAG, "CellType is String: " + stringCellValue + " \t\t ");
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            Log.d(TAG, "CellType is Numeric: " + cell.getNumericCellValue() + " \t\t ");
                            break;
                    }
                }
                database.unitDao().insertUnit(unitEntry);
            }
            inputStream.close();
            return "DONE";
        }
        
        return null;
    }
    
    // Returns file display name.
    @Nullable
    public static String getFileDisplayName(Context context, final Uri uri) {
        String displayName = null;
        try (Cursor cursor = context.getContentResolver()
                .query(uri, null, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                displayName = cursor.getString(
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
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
