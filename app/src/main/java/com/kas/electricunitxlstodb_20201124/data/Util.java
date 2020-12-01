package com.kas.electricunitxlstodb_20201124.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.OpenableColumns;
import android.util.Log;

import androidx.annotation.Nullable;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Iterator;

public class Util {

    public static final String FILE_BROWSER_CACHE_DIR = "CertCache";
    private static final String LOG_TAG = "#UTIL";


    private static String writeFileContent(Context context, final Uri uri) throws IOException {
        InputStream selectedFileInputStream =
                context.getContentResolver().openInputStream(uri);
        if (selectedFileInputStream != null) {
            final File certCacheDir = new File(context.getExternalFilesDir(null), FILE_BROWSER_CACHE_DIR);
            boolean isCertCacheDirExists = certCacheDir.exists();
            if (!isCertCacheDirExists) {
                isCertCacheDirExists = certCacheDir.mkdirs();
            }
            if (isCertCacheDirExists) {
                String filePath = certCacheDir.getAbsolutePath() + "/" + getFileDisplayName(context, uri);
                OutputStream selectedFileOutPutStream = new FileOutputStream(filePath);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = selectedFileInputStream.read(buffer)) > 0) {
                    selectedFileOutPutStream.write(buffer, 0, length);
                }
                selectedFileOutPutStream.flush();
                selectedFileOutPutStream.close();
                return filePath;
            }
            selectedFileInputStream.close();
        }
        return null;
    }

    private static String readContentFromTable(Context context, final Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri); // Uri uri
        Row row;
        if (inputStream != null) {
            XSSFSheet spreadsheet = null;
            try {
                XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
                spreadsheet = workbook.getSheetAt(0);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Iterator<Row> iterator = spreadsheet.iterator();
            Log.d(LOG_TAG, "inputStream created");
            while (iterator.hasNext()) {
                row = (XSSFRow) iterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    switch (cell.getCellType()) {
                        case STRING:
                            Log.d(LOG_TAG, "CellType is String" + cell.getStringCellValue() + " \t\t ");
                            break;
                        case NUMERIC:
                            Log.d(LOG_TAG, "CellType is Numeric" + cell.getNumericCellValue() + " \t\t ");
                            break;
                    }
                }
            }
            inputStream.close();
            return "DONE";
        }

        return null;
    }

    // Returns file display name.
    @Nullable
    private static String getFileDisplayName(Context context, final Uri uri) {
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

    public static class CopyFileToAppDirTask extends AsyncTask<Uri, Void, String> {

        private final Context context;

        private ProgressDialog mProgressDialog;

        public CopyFileToAppDirTask(Context context) {
            this.context = context;
            mProgressDialog = new ProgressDialog(context);
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
                // return writeFileContent(context, uris[0]);
                return readContentFromTable(context, uris[0]);
            } catch (IOException e) {
                Log.d(LOG_TAG, "Failed to copy file {}" + e.getMessage());
                return null;
            }
        }

        protected void onPostExecute(String cachedFilePath) {
            mProgressDialog.dismiss();
            if (cachedFilePath != null) {
                Log.d(LOG_TAG, "Cached file path {}" + cachedFilePath);
            } else {
                Log.d(LOG_TAG, "Writing failed {}");
            }

        }
    }

}
