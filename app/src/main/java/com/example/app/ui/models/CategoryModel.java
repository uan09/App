package com.example.app.ui.models;

public class CategoryModel {
    private String CategoryIconLink;
    private String categoryName;
    private int icon;

    public CategoryModel(String categoryIconLink, String categoryName, int icon) {
        this.CategoryIconLink = categoryIconLink;
        this.categoryName = categoryName;
        this.icon = icon;
    }

    public String getCategoryIconLink() {
        return CategoryIconLink;
    }

    public void setCategoryIconLink(String categoryIconLink) {
        CategoryIconLink = categoryIconLink;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
