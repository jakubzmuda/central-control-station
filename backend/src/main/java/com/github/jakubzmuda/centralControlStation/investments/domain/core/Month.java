package com.github.jakubzmuda.centralControlStation.investments.domain.core;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Arrays;
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
        return Arrays.stream(values())
                .filter(m -> m.name.equals(month))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown month: " + month));
    }

    public static Month of(LocalDate date) {
        return of(date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toLowerCase());
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
}
