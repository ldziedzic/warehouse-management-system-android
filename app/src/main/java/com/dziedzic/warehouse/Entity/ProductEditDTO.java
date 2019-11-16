package com.dziedzic.warehouse.Entity;


public class ProductEditDTO {

    private String manufacturerName;

    private String modelName;

    private int price;

    private int quantity;

    private String newManufacturerName;

    private String newModelName;

    public ProductEditDTO() {
    }

    public ProductEditDTO(String manufacturerName, String modelName, int price, int quantity, String newManufacturerName, String newModelName) {
        this.manufacturerName = manufacturerName;
        this.modelName = modelName;
        this.price = price;
        this.quantity = quantity;
        this.newManufacturerName = newManufacturerName;
        this.newModelName = newModelName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNewManufacturerName() {
        return newManufacturerName;
    }

    public void setNewManufacturerName(String newManufacturerName) {
        this.newManufacturerName = newManufacturerName;
    }

    public String getNewModelName() {
        return newModelName;
    }

    public void setNewModelName(String newModelName) {
        this.newModelName = newModelName;
    }
}

