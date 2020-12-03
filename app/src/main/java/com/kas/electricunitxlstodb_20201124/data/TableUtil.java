package com.kas.electricunitxlstodb_20201124.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.OpenableColumns;
import android.util.Log;

import androidx.annotation.Nullable;

import com.kas.electricunitxlstodb_20201124.dao.AppDatabase;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class TableUtil {

    private static final String LOG_TAG = "#UTIL";
    static AppDatabase database;

    public static String readContentFromTable(Context context, final Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri); // Uri uri
        database = AppDatabase.getInstance(context);
        if (inputStream != null) {
            Row row;
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet spreadsheet = workbook.getSheetAt(0);

            Iterator<Row> iterator = spreadsheet.iterator();
            Log.d(LOG_TAG, "inputStream created");
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

                            Log.d(LOG_TAG, "CellType is String: " + stringCellValue + " \t\t ");
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            Log.d(LOG_TAG, "CellType is Numeric: " + cell.getNumericCellValue() + " \t\t ");
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
                Log.i(LOG_TAG, "Display Name {}" + displayName);

            }
        }
        return displayName;
    }

    public static boolean checkIfExcelFile(File file) {
        if (file == null) {
            return false;
        }
        String name = file.getName();
        //"."Escape characters are required
        String[] list = name.split("\\.");
        //Less than two elements after partitioning indicate that the type name cannot be obtained
        if (list.length < 2) {
            return false;
        }
        String typeName = list[list.length - 1];
        //Satisfies xls or xlsx to be able to
        return "xls".equals(typeName) || "xlsx".equals(typeName);
    }
/*

    public static class ReadFileToDatabaseTask extends AsyncTask<Uri, Void, String> {

        private final Context context;

        public ReadFileToDatabaseTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  mProgressDialog.setMessage("Please Wait..");
//            mProgressDialog.show();
            Log.d(LOG_TAG, "CopyFile onPreExecute");
        }

        protected String doInBackground(Uri... uris) {
            try {
                Log.d(LOG_TAG, "CopyFile doInBackground");

                String cellString = readContentFromTable(context, uris[0]);
                return cellString;
            } catch (IOException e) {
                Log.d(LOG_TAG, "Failed to copy file {}" + e.getMessage());
                return null;
            }
        }

        protected void onPostExecute(String cachedFilePath) {

            if (cachedFilePath != null) {
                Log.d(LOG_TAG, "Cached file path {}" + cachedFilePath);
            } else {
                Log.d(LOG_TAG, "Writing failed {}");
            }

        }
    }
*/

}
