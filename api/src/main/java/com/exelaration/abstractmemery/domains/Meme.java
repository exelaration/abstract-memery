package com.exelaration.abstractmemery.domains;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "memes")
public class Meme {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String memeName;

  private String topText;

  private String bottomText;

  // @OneToMany (mappedBy = "meme")
  // private List<Image> images;

  // @OneToMany (mappedBy = "meme")
  // private List<Tag> tags;
}
