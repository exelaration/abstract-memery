package com.exelaration.abstractmemery.services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.exelaration.abstractmemery.domains.Image;

@Service
@Transactional
public interface ImageService {

    public Image save(MultipartFile file);

}