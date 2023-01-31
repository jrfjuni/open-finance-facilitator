package com.openfinanceparticipants.adapters.outbound.repository;

import com.openfinanceparticipants.adapters.exception.OpenFinanceDataDictionaryRepositoryException;
import com.openfinanceparticipants.core.domain.excel.ExcelModel;
import com.openfinanceparticipants.core.exceptions.OpenFinanceRepositoryException;
import com.openfinanceparticipants.core.ports.OpenFinanceDataDictionaryRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class OpenFinanceDataDictionaryRepository implements OpenFinanceDataDictionaryRepositoryPort {

    @Override
    public void saveExcelFileInLocalDirectory(final List<ExcelModel> excelModelList) throws OpenFinanceRepositoryException {

        Path directoryToSaveFiles = createDirectoryToSaveFiles();

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
    }

    @Override
    public Path getPathToSaveFiles(){
        final var currentTimeStamp =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));

        return Paths.get(FileUtils.getTempDirectory().getAbsolutePath(),"openfinance-brazil","data-dictionary",  currentTimeStamp);
    }

    @Override
    public Path createDirectoryToSaveFiles() throws OpenFinanceRepositoryException  {
        var pathToSaveFiles = getPathToSaveFiles();

        try {

            if(Files.exists(pathToSaveFiles))
                FileUtils.cleanDirectory(pathToSaveFiles.toFile());
            else
                return Files.createDirectories(pathToSaveFiles);

            return pathToSaveFiles;
        }catch (final Exception ex){
            final var pathAsSttring = pathToSaveFiles.toString();
            log.error("Error creating directory: path={} messageError={}", pathAsSttring, ex.getMessage());
            throw new OpenFinanceDataDictionaryRepositoryException(String.format("Error creating directory: %s", pathAsSttring), ex);
        }
    }

    private void throwException(final ExcelModel dataDictionary, final Exception ex) throws OpenFinanceDataDictionaryRepositoryException{
        log.error("Error to save file: fileName: name=[{}]  messageError={}", dataDictionary.fileName(), ex.getMessage());
        throw new OpenFinanceDataDictionaryRepositoryException(String.format("Error to save file: fileName=[%s]", dataDictionary.fileName()), ex);
    }
}
