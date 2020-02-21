package com.exelaration.abstractmemery.repositories;

import com.exelaration.abstractmemery.domains.Image;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemeRepository extends CrudRepository<Image, Long> {

}