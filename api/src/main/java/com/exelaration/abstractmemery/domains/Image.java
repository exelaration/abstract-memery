package com.exelaration.abstractmemery.domains;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "images")
public class Image extends AuditModel {

  @Id
  // @GeneratedValue(strategy=GenerationType.AUTO)
  private long id;

  @Transient private String fileData;

  private String fileName;

  private String fileLocation;

  @Transient private Set<Tag> tags;
}
