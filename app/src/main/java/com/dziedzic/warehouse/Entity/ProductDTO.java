package com.dziedzic.warehouse.Entity;


import android.os.Parcel;
import android.os.Parcelable;

public class ProductDTO implements Parcelable {

    private String manufacturerName;

    private String modelName;

    private int price;

    private int quantity;

    private boolean active;

    private String guid;

    public ProductDTO() {
    }

    public ProductDTO(Parcel in) {
        this.manufacturerName = in.readString();
        this.modelName = in.readString();
        this.price = in.readInt();
        this.quantity = in.readInt();
        this.active = in.readBoolean();
        this.guid = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.manufacturerName);
        dest.writeString(this.modelName);
        dest.writeInt(this.price);
        dest.writeInt(this.quantity);
        dest.writeBoolean(this.active);
        dest.writeString(this.guid);
    }

    public static final Creator CREATOR = new Creator() {
        public ProductDTO createFromParcel(Parcel in) {
            return new ProductDTO(in);
        }

        public ProductDTO[] newArray(int size) {
            return new ProductDTO[size];
        }
    };

    public int describeContents() {
        return 0;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}

