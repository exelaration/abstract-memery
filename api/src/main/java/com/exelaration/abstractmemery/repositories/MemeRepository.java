package com.exelaration.abstractmemery.repositories;

import com.exelaration.abstractmemery.domains.Meme;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemeRepository extends JpaRepository<Meme, Integer> {

  @Query(value = "Select meme_name FROM memes ORDER BY id DESC LIMIT 10;", nativeQuery = true)
  ArrayList<String> getMemes();
}
