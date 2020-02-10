package com.exelaration.abstractmemery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

interface MemeRepository extends JpaRepository<Caption, Long> {

}