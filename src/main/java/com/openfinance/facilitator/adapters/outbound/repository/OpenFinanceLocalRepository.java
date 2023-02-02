package com.openfinance.facilitator.adapters.outbound.repository;

import com.openfinance.facilitator.core.domain.excel.ExcelModel;
import com.openfinance.facilitator.core.exceptions.OpenFinanceDataDictionaryRepositoryException;
import com.openfinance.facilitator.core.ports.OpenFinanceDataDictionaryRepositoryPort;
import com.openfinance.facilitator.adapters.exception.OpenFinanceLocalRepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class OpenFinanceLocalRepository implements OpenFinanceDataDictionaryRepositoryPort {

    /**
     * Method responsible to save files on user temp directory concatenated with pathWhereToSaveFiles.
     * @param excelModelList
     * @param pathWhereToSaveFiles
     * @return Directory where the files were saved.
     * @throws OpenFinanceDataDictionaryRepositoryException
     */
    @Override
    public String saveFiles(final List<ExcelModel> excelModelList, final String... pathWhereToSaveFiles) throws OpenFinanceDataDictionaryRepositoryException {
        Path directoryToSaveFiles = createDirectoryToSaveFiles(pathWhereToSaveFiles);

        for(ExcelModel model : excelModelList){
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                var tempFile = File.createTempFile(model.fileName(), model.eFileExtension().getValue(), directoryToSaveFiles.toFile());
                model.workbook().write(bos);
                Files.write(Paths.get(tempFile.toURI()), bos.toByteArray());
                bos.close();
            }catch (final IOException ioException){
                throwException(model, ioException);
            }catch (final Exception ex){
                throwException(model, ex);
            }
        }

        return directoryToSaveFiles.toString();
    }

    private Path createDirectoryToSaveFiles(final String... folders) throws OpenFinanceLocalRepositoryException {
        if(Objects.isNull(folders))
            throw new OpenFinanceLocalRepositoryException("Folders can't be null.");

        var pathToSaveFiles = Paths.get(FileUtils.getTempDirectory().getAbsolutePath(), folders);

        try {

            if(Files.exists(pathToSaveFiles))
                FileUtils.cleanDirectory(pathToSaveFiles.toFile());
            else
                return Files.createDirectories(pathToSaveFiles);

            return pathToSaveFiles;
        }catch (final Exception ex){
            final var pathAsSttring = pathToSaveFiles.toString();
            log.error("Error creating directory: path={} messageError={}", pathAsSttring, ex.getMessage());
            throw new OpenFinanceLocalRepositoryException(String.format("Error creating directory: %s", pathAsSttring), ex);
        }
    }

    private void throwException(final ExcelModel dataDictionary, final Exception ex) throws OpenFinanceLocalRepositoryException {
        log.error("Error to save file: fileName: name=[{}]  messageError={}", dataDictionary.fileName(), ex.getMessage());
        throw new OpenFinanceLocalRepositoryException(String.format("Error to save file: fileName=[%s]", dataDictionary.fileName()), ex);
    }
}
