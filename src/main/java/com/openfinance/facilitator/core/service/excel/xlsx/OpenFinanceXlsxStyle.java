package com.openfinance.facilitator.core.service.excel.xlsx;

import com.openfinance.facilitator.core.service.excel.OpenFinanceExcelSupport;
import lombok.Getter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Iterator;

@Getter
public class OpenFinanceXlsxStyle extends OpenFinanceExcelSupport {

    private CellStyle cellStyleWrapText;

    private CellStyle cellStyleHeader;

    public void createDefaultSyles(final XSSFWorkbook workbook){
        createCellStyleHeader(workbook);
        createCellStyleWrapText(workbook);
    }

    public void autoSizeColumns(XSSFWorkbook workbook){
        final var numbersOfSheets = workbook.getNumberOfSheets();

        for(int i = 0; i < numbersOfSheets; i++){
            var sheet = workbook.getSheetAt(i);

            if(sheet.getPhysicalNumberOfRows() > 0){
                var row = sheet.getRow(sheet.getFirstRowNum());

                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    final var columnIndex = cell.getColumnIndex();
                    sheet.autoSizeColumn(columnIndex);
                }
            }
        }
    }

    private void createCellStyleHeader(final XSSFWorkbook workbook){
        XSSFFont xssfFont = workbook.createFont();
        xssfFont.setBold(Boolean.TRUE);
        xssfFont.setFontHeightInPoints((short)12);

        cellStyleHeader = workbook.createCellStyle();
        cellStyleHeader.setFont(xssfFont);
        cellStyleHeader.setAlignment(HorizontalAlignment.CENTER);
    }

    private void createCellStyleWrapText(final XSSFWorkbook workbook){
        cellStyleWrapText = workbook.createCellStyle();
        cellStyleWrapText.setWrapText(true);
    }
}
