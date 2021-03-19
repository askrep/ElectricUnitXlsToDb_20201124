package com.kas.electricunitxlstodb_20201124.data;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XlsxToRowListParser implements TableParser {

    private int validCellNumber;

    public XlsxToRowListParser(int validCellNumber) {
        this.validCellNumber = validCellNumber;
    }

    public List<List<String>> parseXlsx(InputStream inputStream) throws IOException {
        //TODO move parsing to upper layer
        List<List<String>> outputRowList = new ArrayList<>();

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

                outputCellList.add(sheetName);

                /** CELLS */
                for (String cell : cellList) {
                    outputCellList.add(cell);    //Fill Cell list

                }
                while (outputCellList.size() != validCellNumber) {
                    outputCellList.add("null");
                }
                outputRowList.add(outputCellList);  //Fill Row list
            }
        });

        return outputRowList;
    }
}
