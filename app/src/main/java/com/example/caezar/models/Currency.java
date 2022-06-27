package com.example.caezar.models;

public class Currency {
    private String id;
    private String name;
    private String symbol;
    private String logo;
    private double price, vol, percentChange1, percentChange24;
    private int rank;
    private double marketCap;

    public Currency(String id, String name, String symbol, String logo, double price, double vol, double percentChange1) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.logo = logo;
        this.price = price;
        this.vol = vol;
        this.percentChange1 = percentChange1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getVol() {
        return vol;
    }

    public void setVol(double vol) {
        this.vol = vol;
    }

    public double getPercentChange1() {
        return percentChange1;
    }

    public void setPercentChange1(double percentChange1) {
        this.percentChange1 = percentChange1;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    public double getPercentChange24() {
        return percentChange24;
    }

    public void setPercentChange24(double percentChange24) {
        this.percentChange24 = percentChange24;
    }
}
