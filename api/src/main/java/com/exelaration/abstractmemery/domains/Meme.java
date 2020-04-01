package com.exelaration.abstractmemery.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "memes")
public class Meme {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String memeName;

  @Transient private String memeUrl;

  private String topText;

  private String bottomText;

  @Column(name = "image_id")
  private int imageId;
}
