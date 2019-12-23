package com.madonasyombua.growwithgoogleteamproject.data.models;

public class Portfolio {
    private String title, shortDescription, longDescription;
    private int portfolioImage;

    public Portfolio() {
    }

    public Portfolio(String title, String shortDescription, String longDescription, int portfolioImage) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.portfolioImage = portfolioImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public int getPortfolioImage() {
        return portfolioImage;
    }

    public void setPortfolioImage(int portfolioImage) {
        this.portfolioImage = portfolioImage;
    }

}