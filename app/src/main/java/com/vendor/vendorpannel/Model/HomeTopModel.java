package com.vendor.vendorpannel.Model;

public class HomeTopModel {
    private String topTitle;
    private String topValue;

    public HomeTopModel(String topTitle, String topValue) {
        this.topTitle = topTitle;
        this.topValue = topValue;
    }

    public String getTopTitle() {
        return topTitle;
    }

    public void setTopTitle(String topTitle) {
        this.topTitle = topTitle;
    }

    public String getTopValue() {
        return topValue;
    }

    public void setTopValue(String topValue) {
        this.topValue = topValue;
    }
}
