package com.github.jakubzmuda.centralControlStation.investments.domain.currency;

public enum Currency {
    USD("USD"),
    PLN("PLN");

    private final String symbol;

    Currency(String symbol) {
        this.symbol = symbol;
    }

    public static Currency of(String code) {
        return switch (code) {
            case "USD" -> USD;
            case "PLN" -> PLN;
            default -> throw new IllegalArgumentException("Unsupported currency code: " + code);
        };
    }

    public static boolean isSupported(String code) {
        return switch (code) {
            case "USD" -> true;
            case "PLN" -> true;
            default -> false;
        };
    }

    public String symbol() {
        return symbol;
    }

}
