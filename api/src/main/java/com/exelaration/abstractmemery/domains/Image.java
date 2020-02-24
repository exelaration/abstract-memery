package com.exelaration.abstractmemery.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name="images")
public class Image {
    
    @Id 
    // @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Transient private String fileData;
    @Column(name="filename") private String fileName;
    @Column(name="filelocation") private String fileLocation;

    // public Image (String fileData, String fileName, String fileLocation) {
    //     this.fileData = fileData;
    //     this.fileName = fileName;
    //     this.fileLocation = fileLocation;
    // }
}