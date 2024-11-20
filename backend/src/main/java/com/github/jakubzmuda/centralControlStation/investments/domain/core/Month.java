package com.github.jakubzmuda.centralControlStation.investments.domain.core;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum Month {
    JANUARY("january"),
    FEBRUARY("february"),
    MARCH("march"),
    APRIL("april"),
    MAY("may"),
    JUNE("june"),
    JULY("july"),
    AUGUST("august"),
    SEPTEMBER("september"),
    OCTOBER("october"),
    NOVEMBER("november"),
    DECEMBER("december");

    private final String name;

    Month(String name) {
        this.name = name;
    }

    public static Month of(String month) {
        return switch (month) {
            case "january" -> JANUARY;
            case "february" -> FEBRUARY;
            case "march" -> MARCH;
            case "april" -> APRIL;
            case "may" -> MAY;
            case "june" -> JUNE;
            case "july" -> JULY;
            case "august" -> AUGUST;
            case "september" -> SEPTEMBER;
            case "october" -> OCTOBER;
            case "november" -> NOVEMBER;
            case "december" -> DECEMBER;
            default -> throw new IllegalArgumentException("Unknown month: " + month);
        };
    }

    public static Month of(LocalDate date) {
        return of(date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toLowerCase());
    }

    public static List<String> monthNames() {
        return Arrays.stream(Month.values())
                .map(Month::toString).toList();
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
}
