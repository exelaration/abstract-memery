package com.exelaration.abstractmemery.domains;
import lombok.Data;

@Data
public class Image {

    private String fileData;
    private String fileName;
    private String fileLocation;

    public Image (String fileData, String fileName, String fileLocation) {
        this.fileData = fileData;
        this.fileName = fileName;
        this.fileLocation = fileLocation;
    }
}