package com.example.yeni.model;

public class Products {
    private String Name, Image, Price, Discount, MenuID;

    public Products() {
    }

    public Products(String name, String image, String price, String discount, String menuID) {
        Name = name;
        Image = image;
        Price = price;
        Discount = discount;
        MenuID = menuID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getMenuID() {
        return MenuID;
    }

    public void setMenuID(String menuID) {
        MenuID = menuID;
    }
}
