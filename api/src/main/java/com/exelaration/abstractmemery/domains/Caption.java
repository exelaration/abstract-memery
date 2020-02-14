package com.exelaration.abstractmemery.domains;

import lombok.Data;

@Data
public class Caption {
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