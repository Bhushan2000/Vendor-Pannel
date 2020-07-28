package com.vendor.vendorpannel.Model;

public class HomeBottomModel {
    private String title;
    private String Id;
    private int image;


    public HomeBottomModel(String title,int image ) {
        this.title = title;
        this.image = image;


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
