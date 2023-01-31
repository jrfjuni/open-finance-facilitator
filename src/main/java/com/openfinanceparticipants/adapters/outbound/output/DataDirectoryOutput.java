package com.openfinanceparticipants.adapters.outbound.output;

import lombok.Getter;

import java.nio.file.Path;

@Getter
public class DataDirectoryOutput {

    private String pathToFiles;

    public static DataDirectoryOutput of(final Path path){
        return new DataDirectoryOutput(path);
    }

    private DataDirectoryOutput(final Path path){
        this.pathToFiles = path.toString();
    }
}
