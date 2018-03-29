package com.prograpy.app1.appdev1.dramalist;

public class ItemData {
    int image;
    String title;
    String actor;
    String tag;

    int getImage() {
        return this.image;
    }

    String getTitle() {
        return this.title;
    }

    String getActor() {
        return this.actor;
    }

    String getTag() {
        return this.tag;
    }

    ItemData(int image, String title, String actor, String tag) {
        this.image = image;
        this.title = title;
        this.actor = actor;
        this.tag = tag;
    }
}
