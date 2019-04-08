package com.emrep.flights.model;

public enum EnumProvider {

    CHEAP_FLIGHTS("https://obscure-caverns-79008.herokuapp.com/cheap"),
    BUSINESS_FLIGHTS("https://obscure-caverns-79008.herokuapp.com/business");

    private String url;

    EnumProvider(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
