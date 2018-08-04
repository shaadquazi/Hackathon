package com.example.shaad.snpppapp.Utility;

/**
 * Created by Shaad on 31-03-2018.
 */

public class Product {
    private String barcode;
    private String category;
    private String name;
    private String description;
    private String sellingPrice;
    private String mrp;

    private String dateAdded;
    private String dateModified;
    private String createdBy;
    private String modifiedBy;
    private String latitude;
    private String longitude;

    private String marketName;
    private String city;
    private String pincode;

    public Product() {
    }

    public Product(String barcode, String category, String name, String description, String sellingPrice, String mrp, String marketName, String city, String pincode) {
        this.barcode = barcode;
        this.category = category;
        this.name = name;
        this.description = description;
        this.sellingPrice = sellingPrice;
        this.mrp = mrp;
        this.marketName = marketName;
        this.city = city;
        this.pincode = pincode;
    }

    public Product(String barcode, String category, String name, String description, String sellingPrice, String mrp, String dateAdded, String dateModified, String createdBy, String modifiedBy, String latitude, String longitude, String marketName, String city, String pincode) {
        this.barcode = barcode;
        this.category = category;
        this.name = name;
        this.description = description;
        this.sellingPrice = sellingPrice;
        this.mrp = mrp;
        this.dateAdded = dateAdded;
        this.dateModified = dateModified;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.latitude = latitude;
        this.longitude = longitude;
        this.marketName = marketName;
        this.city = city;
        this.pincode = pincode;
    }

    public Product(String barcode, String category, String name, String description, String sellingPrice, String mrp, String marketName, String city, String pincode, String createdBy){
        this.barcode = barcode;
        this.category = category;
        this.name = name;
        this.description = description;
        this.sellingPrice = sellingPrice;
        this.mrp = mrp;
        this.marketName = marketName;
        this.city = city;
        this.pincode = pincode;
        this.createdBy = createdBy;
    }

    public String toString(){
        return name + ", " + category + ", " + description + ", " + sellingPrice + ", " + mrp +
                ", " + marketName + ", " + city + ", " + pincode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
