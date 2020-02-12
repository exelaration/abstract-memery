package com.exelaration.abstractmemery.domains;

import lombok.Data;

// import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.Id;

@Data
// @Entity
public class Caption {
    // private @Id @GeneratedValue Long id;
    private String topText;
    private String bottomText;

    Caption(String topText, String bottomText) {
        this.topText = topText;
        this.bottomText = bottomText;
    }

    public String getTopText() {
        return topText;
    }

    public String getBottomText() {
        return bottomText;
    }


}