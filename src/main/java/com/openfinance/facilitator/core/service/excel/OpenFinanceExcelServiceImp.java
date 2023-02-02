package com.openfinance.facilitator.core.service.excel;

import com.openfinance.facilitator.core.domain.csv.Csv;
import com.openfinance.facilitator.core.domain.excel.ExcelModel;
import com.openfinance.facilitator.core.exceptions.excel.OpenFinanceExcelException;
import com.openfinance.facilitator.core.ports.OpenFinanceExcelPort;
import com.openfinance.facilitator.core.service.excel.xlsx.OpenFinanceXlsx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class OpenFinanceExcelServiceImp extends OpenFinanceXlsx implements OpenFinanceExcelPort {

    public List<ExcelModel> convertFromCsvToXlsx(final List<Csv> csvFiles) {
        return convertToXlsx(csvFiles);
    }
}
