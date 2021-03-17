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

    public static Map<String, List<String[]>> parseXlsxToSheetMap(InputStream inputStream) throws IOException {
        Map<String, List<String[]>> outputSheetsMap = new HashMap<>();

        XSSFWorkbook book = ParseXlsxTableUtil.getBookFromXlsxInputStream(inputStream);
        Map<String, XSSFSheet> sheetsMap = ParseXlsxTableUtil.getBookSheetsMap(book);

        sheetsMap.forEach((sheetName, sheet) -> {

            List<String[]> outputRowList = new ArrayList<>();

            List<Row> rowList = ParseXlsxTableUtil.getSheetRowsList(sheet);

            for (Row row : rowList) {
                List<String> cellList = ParseXlsxTableUtil.getRowCellsListAsString(row);
                String[] cells = new String[row.getRowNum()];
                cells[0] = sheetName;

                for (int i = 0; i < cellList.size(); i++) {
                    String stringCell = cellList.get(i);
                    cells[1 + i] = stringCell;
                }
                outputRowList.add(cells);
            }
            outputSheetsMap.put(sheetName, outputRowList);
        });

        return outputSheetsMap;
    }

    public static XSSFWorkbook getBookFromXlsxInputStream(InputStream inputStream) throws IOException {
        return new XSSFWorkbook(inputStream);
    }

    public static Map<String, XSSFSheet> getBookSheetsMap(XSSFWorkbook workbook) {
        Map<String, XSSFSheet> sheetMap = new HashMap<>();

        for (XSSFSheet sheet : workbook) {
            sheetMap.put(sheet.getSheetName(), sheet);
        }
        return sheetMap;
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
