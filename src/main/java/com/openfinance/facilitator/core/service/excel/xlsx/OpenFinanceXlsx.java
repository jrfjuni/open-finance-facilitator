package com.openfinance.facilitator.core.service.excel.xlsx;

import com.opencsv.CSVReader;
import com.openfinance.facilitator.core.domain.csv.Csv;
import com.openfinance.facilitator.core.domain.enums.EFileExtension;
import com.openfinance.facilitator.core.domain.excel.ExcelModel;
import com.openfinance.facilitator.core.exceptions.excel.OpenFinanceExcelException;
import com.openfinance.facilitator.core.utils.OpenFinanceNumberUtils;
import com.openfinance.facilitator.core.exceptions.excel.OpenFinanceXlsxExcepion;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class OpenFinanceXlsx extends OpenFinanceXlsxStyle {

    public List<ExcelModel> convertToXlsx(final List<Csv> csvFiles) {
        List<ExcelModel> excelModels = new ArrayList<>();

        for(Csv csvFile: csvFiles){
            try {
                final ExcelModel model = crateXlsx(csvFile);
                excelModels.add(model);
            } catch (OpenFinanceExcelException e) {
                log.error("Error to create xlsx file: {}", e);
            }
        }

        return excelModels;
    }

    private ExcelModel crateXlsx(final Csv csvFile) throws OpenFinanceExcelException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        createDefaultSyles(workbook);

        final CSVReader csvReader = getCSVReader(csvFile);
        XSSFSheet sheet = workbook.createSheet(csvFile.getName());

        try {

            String[] rowCsvContent = null;
            int rowNum = 0;

            while ((rowCsvContent = csvReader.readNext()) != null) {
                final var rowCsvContentSplitted = rowCsvContent[0].split(";");

                if (rowNum == 0)
                    createHeader(sheet, rowCsvContentSplitted);
                else
                    createRowValue(sheet, rowNum, rowCsvContentSplitted);

                rowNum++;
            }

            autoSizeColumns(workbook);

            return new ExcelModel(csvFile.getName(), EFileExtension.XLSX, workbook);
        }catch (final Exception ex) {
            log.error("Error to create xlsx file: name=[{}] messageError=[{}].", csvFile.getName(), ex.getMessage());
            throw new OpenFinanceXlsxExcepion(String.format("Error to create xlsx file: %s", csvFile.getName()), ex);
        }
    }

    private void createHeader(XSSFSheet sheet, final String[] headerNames){
        XSSFRow currentRow = sheet.createRow(0);

        for(int i = 0; i < headerNames.length; i++){
            String value =  headerNames[i];

            if(StringUtils.isNotBlank(value)){
                value = value.toUpperCase();
                var cell = currentRow.createCell(i);
                cell.setCellValue(value);
                cell.setCellStyle(getCellStyleHeader());
            }
        }
    }

    private void createRowValue(XSSFSheet sheet, final int rowNum, final String[] rowValues){
        XSSFRow currentRow = sheet.createRow(rowNum);

        for(int i = 0; i < rowValues.length; i++){
            final String value =  rowValues[i];

            if(StringUtils.isNotBlank(value)){
                var cell = currentRow.createCell(i);

                if(OpenFinanceNumberUtils.isInteger(value))
                    cell.setCellValue(Integer.parseInt(value));
                else if(OpenFinanceNumberUtils.isDouble(value))
                    cell.setCellValue(Double.parseDouble(value));
                else{
                    cell.setCellValue(value);
                    cell.setCellStyle(getCellStyleWrapText());
                }
            }
        }
    }
}
