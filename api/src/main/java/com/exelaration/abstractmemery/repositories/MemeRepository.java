package com.exelaration.abstractmemery.repositories;

import com.exelaration.abstractmemery.domains.Meme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemeRepository extends JpaRepository<Meme, Integer> {}
