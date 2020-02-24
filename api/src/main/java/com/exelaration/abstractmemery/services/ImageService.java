package com.exelaration.abstractmemery.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exelaration.abstractmemery.domains.Image;
import com.exelaration.abstractmemery.repositories.MemeRepository;

@Service
@Transactional
@RequiredArgsConstructor
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
    
    // public void add(Image image) {
    //     memeRepository.save(image);
    // }
    // public void delete(long id) {
    //     memeRepository.deleteById(id);
    // }
    // public List<Image> getImages() {
    //     return (List<Image>) memeRepository.findAll();
    // }

    
    // public Image getImageById(long id) {
    //     Optional<Image> optionalImage = memeRepository.findById(id);
    //     return optionalImage.orElseThrow(() -> new DogNotFoundException("Couldn't find a Dog with id: " + id));
    // }
    // private Image toEntity(Image image) {
    //     Image entity = new Image();
    //     entity.setFileLocation(image.getFileLocation());
    //     return entity;
    // }
}