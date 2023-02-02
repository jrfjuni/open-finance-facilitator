package com.openfinance.facilitator;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class OpenFinanceFacilitatorTest {

    protected String getResourceFileAsString(final String fileNameWithPath) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileNameWithPath);
        return StreamUtils.copyToString(is, StandardCharsets.UTF_8);
    }

    protected List<?> getResourceFileAsList(final String fileNameWithPath, final Class<?> convertTo) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Ignore json fields not present in the class.
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        final JavaType type = mapper.getTypeFactory()
                .constructCollectionType(List.class, convertTo);

        return mapper.readValue(getResourceFileAsString(fileNameWithPath), type);
    }

    protected void deleteFile(File file){
        if(!Objects.isNull(file))
            file.deleteOnExit();
    }
}
