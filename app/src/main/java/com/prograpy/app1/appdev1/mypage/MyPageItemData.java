package com.prograpy.app1.appdev1.mypage;

public class MyPageItemData {
    private int image;
    private String title;
    private String brand;
    private String price;

    public int getImage() {
        return this.image;
    }

    public String getTitle() {
        return this.title;
    }

    public String getActor() {
        return this.brand;
    }

    public String getTag() {
        return this.price;
    }

    public MyPageItemData(int image, String title, String brand, String price) {
        this.image = image;
        this.title = title;
        this.brand = brand;
        this.price = price;
    }
}
