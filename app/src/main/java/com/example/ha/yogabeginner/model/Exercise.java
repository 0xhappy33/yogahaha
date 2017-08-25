package com.example.ha.yogabeginner.model;

/**
 * Created by Ha Truong on 8/24/2017.
 * This is a YogaBeginner
 * into the com.example.ha.yogabeginner.model
 */

public class Exercise {
    private int image_id;
    private String name;

    public Exercise(int image_id, String name) {
        this.image_id = image_id;
        this.name = name;
    }

    public int getImage_id() {
        return image_id;
    }

    public Exercise setImage_id(int image_id) {
        this.image_id = image_id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Exercise setName(String name) {
        this.name = name;
        return this;
    }
}
