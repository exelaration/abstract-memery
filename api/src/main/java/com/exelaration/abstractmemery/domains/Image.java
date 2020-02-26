package com.exelaration.abstractmemery.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name="images")
public class Image extends AuditModel{
    
    @Id 
    @GeneratedValue(generator = "id_generator")
    @SequenceGenerator(
            name = "id_generator",
            sequenceName = "global_id_sequence",
            allocationSize = 1
    )
    private long id;
    @Transient private String fileData;
    private String fileName;
    private String fileLocation;
}