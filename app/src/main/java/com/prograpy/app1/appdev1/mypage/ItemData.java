package com.prograpy.app1.appdev1.mypage;

public class ItemData {
    int image;
    String title;
    String brand;
    String price;

    int getImage() {
        return this.image;
    }

    String getTitle() {
        return this.title;
    }

    String getActor() {
        return this.brand;
    }

    String getTag() {
        return this.price;
    }

    ItemData(int image, String title, String brand, String price) {
        this.image = image;
        this.title = title;
        this.brand = brand;
        this.price = price;
    }
}
