package com.openfinanceparticipants.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openfinanceparticipants.core.domain.enums.EFileExtension;
import com.openfinanceparticipants.core.exceptions.OpenFinanceException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class OpenFinanceFileUtils {

    public static File createTempFileFromObject(final String fileName, final EFileExtension fileExtension,
                                                final Object object) throws OpenFinanceException {
        try {

            // Creating a temp file
            final File tempFile = File.createTempFile(fileName, fileExtension.getValue());

            // Converting object content file to string
            final var postmanObjectAsString = new ObjectMapper().writeValueAsString(object);

            // Converting string content file to byte[] with Charsets
            final var postmanStringAsByte = postmanObjectAsString.getBytes(StandardCharsets.UTF_8);

            // Writing content
            Files.write(Paths.get(tempFile.toURI()), postmanStringAsByte);

            return tempFile;
        }catch (final Throwable ex) {
            log.error("Error to create a temp file: {}", ex.getMessage());
            throw new OpenFinanceException("Error to create a temp file.");
        }
    }
}
