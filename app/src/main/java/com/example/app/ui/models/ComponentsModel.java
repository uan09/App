package com.example.app.ui.models;

public class ComponentsModel {
    String newBuilds;
    int image;

    public ComponentsModel(String newBuilds, int image) {
        this.newBuilds = newBuilds;
        this.image = image;
    }

    public String getNewBuilds() {
        return newBuilds;
    }

    public int getImage() {
        return image;
    }
}
