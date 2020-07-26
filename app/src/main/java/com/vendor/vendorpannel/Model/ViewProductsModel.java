package com.vendor.vendorpannel.Model;

public class ViewProductsModel {

    private  String productTitle , price;


    public ViewProductsModel(String productTitle, String price) {
        this.productTitle = productTitle;
        this.price = price;

    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
