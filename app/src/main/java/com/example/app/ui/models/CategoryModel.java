package com.example.app.ui.models;

public class CategoryModel {
    private String categoryName;
    private int icon;
    private String CategoryType;

    public CategoryModel(String categoryType, String categoryName, int icon) {
        this.CategoryType = categoryType;
        this.categoryName = categoryName;
        this.icon = icon;
    }

    public String getCategoryType() {
        return CategoryType;
    }

    public void setCategoryType(String categoryType) {
        this.CategoryType = categoryType;
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
