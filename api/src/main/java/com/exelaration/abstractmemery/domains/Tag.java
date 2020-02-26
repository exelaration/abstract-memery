package com.exelaration.abstractmemery.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name="tags")
public class Tag {
    
    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private String tag;

    @ManyToOne 
    private Image image;
}