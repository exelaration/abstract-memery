package com.exelaration.abstractmemery.domains;

import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
// @Entity
@Table(name = "tags")
public class Tag {

  @Id
  // @GeneratedValue(strategy=GenerationType.AUTO)
  private long id;

  private String tag;

  // @ManyToOne
  private Image image;
}
