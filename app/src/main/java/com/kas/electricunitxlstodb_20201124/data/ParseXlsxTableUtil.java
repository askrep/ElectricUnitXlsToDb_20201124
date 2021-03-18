package com.kas.electricunitxlstodb_20201124.data;

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

public class ParseXlsxTableUtil {

    public static List<List<String>> parseXlsxToSheetMap(InputStream inputStream) throws IOException {
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
                outputRowList.add(outputCellList);  //Fill Row list
            }
        });

        return outputRowList;
    }

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
            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                cellList.add(cell.getStringCellValue());
            } else {
                throw new IllegalArgumentException("Cell of Row is not String type, Row: " + row.getRowNum() + "; Cell: " + cell.getColumnIndex());
            }
        }
        return cellList;
    }
}
