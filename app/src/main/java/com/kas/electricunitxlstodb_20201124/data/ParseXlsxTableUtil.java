package com.kas.electricunitxlstodb_20201124.data;

import android.util.Log;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class ParseXlsxTableUtil {

    private static final String TAG ="#_PARSE_XLSX_TABLE_UTIL";

    public static XSSFWorkbook getBookFromXlsxInputStream(InputStream inputStream) throws IOException {
        return new XSSFWorkbook(inputStream);
    }

    public static List<XSSFSheet> getBookSheetList(XSSFWorkbook workbook) {
        List<XSSFSheet> sheetList = new ArrayList<>();

        for (XSSFSheet sheet : workbook) {
            sheetList.add(sheet);
        }
        return sheetList;
    }

    public static List<Row> getSheetRowsList(XSSFSheet sheet) {
        List<Row> rowList = new ArrayList<>();
        for (Row row : sheet) {
            rowList.add(row);
        }
        return rowList;
    }

    public static List<String> getRowCellsListAsString(Row row) {
        List<String> cellList = new ArrayList<>();

        for (Cell cell : row) {
            if (cell.getCellType() == Cell.CELL_TYPE_STRING || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                cellList.add(cell.getStringCellValue());
            } else {
//                throw new IllegalArgumentException("Cell of Row is not String type, Row: " + row.getRowNum() + "; Cell: " + cell.getColumnIndex());
            }
        }
        Log.d(TAG, "ROW LAST CELL NAME: " + row.getLastCellNum() + "; ROW: " + row.getRowNum());
        return cellList;
    }
}
