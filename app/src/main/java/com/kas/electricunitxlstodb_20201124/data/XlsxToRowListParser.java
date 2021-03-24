package com.kas.electricunitxlstodb_20201124.data;

import android.util.Log;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class XlsxToRowListParser implements TableParser {
    private static final String TAG = "#_XLSX_TO_ROW";

    private int validCellNumber;
    private List<List<String>> rowListOfCellLists;

    @Inject
    public XlsxToRowListParser() {
        this.validCellNumber = 4;
    }

    public List<List<String>> parseTable(InputStream inputStream) throws IOException {
         rowListOfCellLists = new ArrayList<>();

        XSSFWorkbook book = ParseXlsxTableUtil.getBookFromXlsxInputStream(inputStream);
        List<XSSFSheet> sheetList = ParseXlsxTableUtil.getBookSheetList(book);

        /** SHEETS */
        sheetList.forEach((sheet) -> {

            String sheetName = sheet.getSheetName();
            List<Row> rowList = ParseXlsxTableUtil.getSheetRowsList(sheet);

            /** ROWS */
            for (Row row : rowList) {
                List<String> cellList = ParseXlsxTableUtil.getRowCellsListAsString(row);
                List<String> outputCellList = new ArrayList<>();

                /** CELLS */
                for (int i = 0; i < cellList.size(); i++) {
                    String cell = cellList.get(i);
                    outputCellList.add(cell);    //Fill Cell list
                    //Log.d(TAG, "parseTable: SHEET: \"" + sheetName + "\" ROW: " + row.getRowNum() + " Cell_no:" + i + " - CELL: " + cell);
                }
                rowListOfCellLists.add(outputCellList);  //Fill Row list
            }
        });

        return rowListOfCellLists;
    }

    public List<List<String>> getRowListOfCellLists() {
        return rowListOfCellLists;
    }

    public int getValidCellNumber() {
        return validCellNumber;
    }

    public void setValidCellNumber(int validCellNumber) {
        this.validCellNumber = validCellNumber;
    }
}
