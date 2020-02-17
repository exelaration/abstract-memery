package com.exelaration.abstractmemery.controllers;

import com.exelaration.abstractmemery.domains.Caption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "http://localhost:3000")
public class UploadController {

    @GetMapping(value = "/", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> showText(@RequestBody Caption caption){
        Logger logger = LoggerFactory.getLogger(UploadController.class);
        logger.info("\nGet Request\nTop Text: " + caption.getTopText() + "\nBottom Text: " + caption.getBottomText());
        return new ResponseEntity<>(caption, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity newCaption(@RequestBody Caption caption) {
        Logger logger = LoggerFactory.getLogger(UploadController.class);
        logger.info("\nPost Request\nTop Text: " + caption.getTopText() + "\nBottom Text: " + caption.getBottomText());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}