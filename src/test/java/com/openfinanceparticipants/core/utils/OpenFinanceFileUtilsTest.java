package com.openfinanceparticipants.core.utils;

import com.openfinanceparticipants.core.domain.enums.EFileExtension;
import com.openfinanceparticipants.core.exceptions.OpenFinanceException;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class OpenFinanceFileUtilsTest {

    private static final String FILE_NAME = "fileNameTest";

    private static final String CONTENT = """
            {
                "type": "TEST",
                "application": "OPEN-FINANCE-BRAZIL"
            }
            """;

    @Test
    void give_validParamsInformation_than_createFile() throws OpenFinanceException {
        final var tempFile = OpenFinanceFileUtils.createTempFileFromObject(FILE_NAME, EFileExtension.JSON, CONTENT);
        assertNotNull(tempFile);
        assertTrue(tempFile.getName().contains(FILE_NAME));

        if(!Objects.isNull(tempFile))
            tempFile.deleteOnExit();
    }

    @Test
    void give_invalidParamsInformation_than_exception() throws OpenFinanceException {
       assertThrows(OpenFinanceException.class,
                () -> OpenFinanceFileUtils.createTempFileFromObject(null, EFileExtension.JSON, null),
               "Error to create a temp file.");
    }
}
