package com.openfinance.facilitator.adapters.outbound.output;

import lombok.Getter;

import java.nio.file.Path;

@Getter
public class DataDirectoryOutput {

    private String pathToFiles;

    public static DataDirectoryOutput of(final String whereFilesWereSaved){
        return new DataDirectoryOutput(whereFilesWereSaved);
    }

    private DataDirectoryOutput(final String whereFilesWereSaved){
        this.pathToFiles = whereFilesWereSaved;
    }
}
