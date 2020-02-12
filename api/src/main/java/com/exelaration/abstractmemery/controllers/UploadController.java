package com.exelaration.abstractmemery.controllers;

import java.util.List;

import com.exelaration.abstractmemery.domains.Caption;
// import com.exelaration.abstractmemery.repositories.MemeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = "http://localhost:3000")
public class UploadController {

    @GetMapping(value = "/upload")
    public ResponseEntity<?> showText(@RequestBody Caption caption){
        Logger logger = LoggerFactory.getLogger(UploadController.class);
        logger.info("Top Text: " + caption.getTopText() + "\nBottom Text: " + caption.getBottomText());
        return new ResponseEntity<>(HttpStatus.OK);
        // System.out.println("Top Text: " + caption.getTopText() + "\nBottom Text: " + caption.getBottomText());
    }

    // @Autowired
    // private final MemeRepository repository;

    // HomeController(MemeRepository repository) {
    //     this.repository = repository;
    // }

    // @GetMapping("/upload")
    // List<Caption> returnAll() {
    //     return repository.findAll();
    // }

    // @PostMapping("/upload")
    // Caption newCaption(@RequestBody Caption newCaption) {
    //     return repository.save(newCaption);
    // }

}