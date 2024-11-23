package com.github.jakubzmuda.centralControlStation.investments.domain.currency;

public enum Currency {
    USD("USD"),
    PLN("PLN");

    private final String symbol;

    Currency(String symbol) {
        this.symbol = symbol;
    }

    public String symbol() {
        return symbol;
    }

}
