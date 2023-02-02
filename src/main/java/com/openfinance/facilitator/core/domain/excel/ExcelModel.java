package com.openfinance.facilitator.core.domain.excel;

import com.openfinance.facilitator.core.domain.enums.EFileExtension;
import org.apache.poi.ss.usermodel.Workbook;

public record ExcelModel(String fileName, EFileExtension eFileExtension, Workbook workbook) { }
