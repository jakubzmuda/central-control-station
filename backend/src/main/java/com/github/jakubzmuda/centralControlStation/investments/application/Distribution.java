package com.github.jakubzmuda.centralControlStation.investments.application;

import com.github.jakubzmuda.centralControlStation.investments.domain.core.MonetaryValue;

import java.util.Objects;

public class Distribution {
    String source;
    MonetaryValue monetaryValue;

    public Distribution(String source, MonetaryValue monetaryValue) {
        this.source = source;
        this.monetaryValue = monetaryValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Distribution that = (Distribution) o;
        return Objects.equals(source, that.source) && Objects.equals(monetaryValue, that.monetaryValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, monetaryValue);
    }

    @Override
    public String toString() {
        return "Distribution{" +
                "source='" + source + '\'' +
                ", monetaryValue=" + monetaryValue +
                '}';
    }

    public String source() {
        return source;
    }

    public MonetaryValue monetaryValue() {
        return monetaryValue;
    }
}
