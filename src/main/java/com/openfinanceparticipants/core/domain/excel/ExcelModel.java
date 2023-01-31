package com.openfinanceparticipants.core.domain.excel;

import com.openfinanceparticipants.core.domain.enums.EFileExtension;
import org.apache.poi.ss.usermodel.Workbook;

public record ExcelModel(String fileName, EFileExtension eFileExtension, Workbook workbook) { }
