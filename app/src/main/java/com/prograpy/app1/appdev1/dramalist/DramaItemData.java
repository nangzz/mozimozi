package com.prograpy.app1.appdev1.dramalist;

public class DramaItemData {
    private int image;
    private String title;
    private String actor;
    private String tag;

    public int getImage() {
        return this.image;
    }

    public String getTitle() {
        return this.title;
    }

    public String getActor() {
        return this.actor;
    }

    public String getTag() {
        return this.tag;
    }

    public DramaItemData(int image, String title, String actor, String tag) {
        this.image = image;
        this.title = title;
        this.actor = actor;
        this.tag = tag;
    }
}
