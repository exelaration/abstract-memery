package com.exelaration.abstractmemery.services;

import java.util.List;
import java.util.Optional;

import com.exelaration.abstractmemery.domains.Image;

public interface MetadataService {

    public List<Image> findAll();

    public Optional<Image> findById(Long id);

    public Image save(Image image);

    public void deleteById(Long id);
}