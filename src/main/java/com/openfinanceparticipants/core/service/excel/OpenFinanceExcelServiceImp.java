package com.openfinanceparticipants.core.service.excel;

import com.openfinanceparticipants.core.domain.excel.ExcelModel;
import com.openfinanceparticipants.core.exceptions.excel.OpenFinanceExcelException;
import com.openfinanceparticipants.core.ports.OpenFinanceExcelPort;
import com.openfinanceparticipants.core.service.excel.xlsx.OpenFinanceXlsx;
import com.openfinanceparticipants.core.domain.csv.Csv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class OpenFinanceExcelServiceImp extends OpenFinanceXlsx implements OpenFinanceExcelPort {

    public List<ExcelModel> convertFromCsvToXlsx(final List<Csv> csvFiles) throws OpenFinanceExcelException {
        return convertToXlsx(csvFiles);
    }
}
