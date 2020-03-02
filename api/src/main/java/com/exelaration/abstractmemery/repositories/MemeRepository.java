package com.exelaration.abstractmemery.repositories;

import com.exelaration.abstractmemery.domains.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemeRepository extends JpaRepository<Image, Long> {}
