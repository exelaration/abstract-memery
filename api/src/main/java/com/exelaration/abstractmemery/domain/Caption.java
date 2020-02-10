package com.exelaration.abstractmemery.domain;

import lombok.Builder;
import lombok.Value;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Value
public class Caption {
    private @Id @GeneratedValue Long id;
    private String topText;
    private String bottomText;

    @Builder
    public Caption(String topText, String bottomText) {
        this.topText = topText;
        this.bottomText = bottomText;
    }
}