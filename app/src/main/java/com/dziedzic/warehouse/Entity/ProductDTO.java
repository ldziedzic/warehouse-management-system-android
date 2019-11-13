package com.dziedzic.warehouse.Entity;


public class ProductDTO {

    private String manufacturerName;


    private String modelName;

    private int price;

    private int quantity;

    public ProductDTO() {
    }


    public String getManufacturerName() {
        return manufacturerName;
    }

    public String getModelName() {
        return modelName;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}

