package com.exelaration.abstractmemery.controllers;

import java.util.List;

import com.exelaration.abstractmemery.domain.Caption;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private final MemeRepository repository;

    HomeController(MemeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    List<Caption> returnAll() {
        return repository.findAll();
    }

    @PostMapping("/")
    Caption newCaption(@RequestBody Caption newCaption) {
        return repository.save(newCaption);
    }

}