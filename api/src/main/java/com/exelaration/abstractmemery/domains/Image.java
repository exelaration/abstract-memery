package com.exelaration.abstractmemery.domains;

public class Image {

    private String fileData;
    private String fileName;

    public Image (String fileData, String fileName) {
        this.fileData = fileData;
        this.fileName = fileName;
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}