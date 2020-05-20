package com.exelaration.abstractmemery.repositories;

import com.exelaration.abstractmemery.domains.Meme;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemeRepository extends JpaRepository<Meme, Integer> {

  ArrayList<Meme> findTop10ByIsPublicTrueOrderByIdDesc();

  ArrayList<Meme>
      findTop10ByTopTextIgnoreCaseContainsOrBottomTextIgnoreCaseContainsOrMemeNameIgnoreCaseContains(
          String topText, String bottomText, String memeName);

  ArrayList<Meme> findByUserIdOrderByIdDesc(int userId);
}
