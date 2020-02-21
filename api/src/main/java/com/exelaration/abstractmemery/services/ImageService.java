package com.exelaration.abstractmemery.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exelaration.abstractmemery.domains.Image;
import com.exelaration.abstractmemery.repositories.MemeRepository;

@Service
@Transactional
public class ImageService {

    @Autowired 
    private MemeRepository memeRepository;

    public List<Image> findAll() {
        return (List<Image>) memeRepository.findAll();
    }

    public Optional<Image> findById(Long id) {
        return memeRepository.findById(id);
    }

    public Image save(Image image) {
        return memeRepository.save(image);
    }

    public void deleteById(Long id) {
        memeRepository.deleteById(id);
    }
}