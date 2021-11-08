package com.example.ferreroimagerecognition;

public class StoreVisionBo {
    private String category,sku,Brand;
    private int outofstock;
    private int sos;
    public int suggestedPrice;
    public int actualPrice;

    public int getSuggestedPrice() {
        return suggestedPrice;
    }

    public void setSuggestedPrice(int suggestedPrice) {
        this.suggestedPrice = suggestedPrice;
    }

    public int getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(int actualPrice) {
        this.actualPrice = actualPrice;
    }

    public int getVariance() {
        return Variance;
    }

    public void setVariance(int variance) {
        Variance = variance;
    }

    public int Variance;

    public int isScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private int score;


    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    private int gap;

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public int getSos() {
        return sos;
    }

    public void setSos(int sos) {
        this.sos = sos;
    }

    public int getOpportunity() {
        return opportunity;
    }

    public void setOpportunity(int opportunity) {
        this.opportunity = opportunity;
    }

    private int opportunity;
    private int availability;
    private int nooffacing;
    public StoreVisionBo(String category, int outofstock, int availability, int nooffacing) {
        this.category = category;
        this.outofstock = outofstock;
        this.availability = availability;
        this.nooffacing = nooffacing;
    }



    public StoreVisionBo(){

    }

    public StoreVisionBo(StoreVisionBo storeVisionBo){
        this.category=storeVisionBo.getCategory();
        this.outofstock=storeVisionBo.getOutofstock();
        this.availability=storeVisionBo.getAvailability();
        this.nooffacing=storeVisionBo.getNooffacing();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getOutofstock() {
        return outofstock;
    }

    public void setOutofstock(int outofstock) {
        this.outofstock = outofstock;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public int getNooffacing() {
        return nooffacing;
    }

    public void setNooffacing(int nooffacing) {
        this.nooffacing = nooffacing;
    }

}
