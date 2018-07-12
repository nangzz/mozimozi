package com.prograpy.app1.appdev1.productInfo;

public class ProductInfoData {
    private int image;
    private String title;

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public ProductInfoData(int image, String title) {
        this.image = image;
        this.title = title;
    }
}