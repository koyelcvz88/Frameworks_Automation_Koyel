package com.thetestingacademy.utils;

import com.thetestingacademy.model.DataModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    private Workbook workbook;
    private Sheet sheet;

    public ExcelReader(String fileName, String sheetName) {

        try {
            InputStream inputStream =
                    getClass().getClassLoader().getResourceAsStream(fileName);

            if (inputStream == null) {
                throw new RuntimeException("Excel file not found: " + fileName);
            }

            workbook = new XSSFWorkbook(inputStream);
            sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new RuntimeException("Sheet not found: " + sheetName);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to load Excel", e);
        }
    }

    public List<DataModel> getAllData() {

        List<DataModel> list = new ArrayList<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            Row row = sheet.getRow(i);
            if (row == null) continue;

            DataModel data = new DataModel();

            data.setSuiteType(getValue(row.getCell(0)));
            data.setFlowType(getValue(row.getCell(1)));

            data.setRequestType(getValue(row.getCell(2)));
            data.setRequestSubType(getValue(row.getCell(3)));
            data.setRequestSegment(getValue(row.getCell(4)));

            data.setOcFeesPayer(getValue(row.getCell(5)));
            data.setInternalCounsel(getValue(row.getCell(6)));
            data.setOutsideCounselFirm(getValue(row.getCell(7)));

            data.setContactAttorney(getValue(row.getCell(8)));
            data.setIsOcConflicted(getValue(row.getCell(9)));

            data.setRequestClose(getValue(row.getCell(10)));

            data.setState(getValue(row.getCell(11)));

            list.add(data);
        }

        return list;
    }

    // =========================================================
    // ONLY FIXED PART (CRITICAL)
    // =========================================================
    private String getValue(Cell cell) {

        if (cell == null) return null;

        try {
            cell.setCellType(CellType.STRING);

            String value = cell.getStringCellValue();

            if (value == null) return null;

            value = value.trim();

            return value.isEmpty() ? null : value;

        } catch (Exception e) {
            throw new RuntimeException("Error reading Excel cell", e);
        }
    }
}