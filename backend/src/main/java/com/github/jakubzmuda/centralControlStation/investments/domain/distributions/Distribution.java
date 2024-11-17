package com.github.jakubzmuda.centralControlStation.investments.domain.distributions;

import com.github.jakubzmuda.centralControlStation.investments.domain.core.MonetaryValue;

import java.time.LocalDate;
import java.util.Objects;

public class Distribution {
    private String productTicker;
    private MonetaryValue monetaryValue;
    private LocalDate exDate;
    private LocalDate declareDate;
    private LocalDate payDate;
    private LocalDate recordDate;

    public Distribution(String productTicker, MonetaryValue monetaryValue) {
        this.productTicker = productTicker;
        this.monetaryValue = monetaryValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Distribution that = (Distribution) o;
        return Objects.equals(productTicker, that.productTicker) && Objects.equals(monetaryValue, that.monetaryValue) && Objects.equals(exDate, that.exDate) && Objects.equals(declareDate, that.declareDate) && Objects.equals(payDate, that.payDate) && Objects.equals(recordDate, that.recordDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productTicker, monetaryValue, exDate, declareDate, payDate, recordDate);
    }

    @Override
    public String toString() {
        return "Distribution{" +
                "source='" + productTicker + '\'' +
                ", monetaryValue=" + monetaryValue +
                ", exDate=" + exDate +
                ", declareDate=" + declareDate +
                ", payDate=" + payDate +
                ", recordDate=" + recordDate +
                '}';
    }

    public Distribution withExDate(LocalDate exDate) {
        this.exDate = exDate;
        return this;
    }

    public Distribution withDeclareDate(LocalDate declareDate) {
        this.declareDate = declareDate;
        return this;
    }

    public Distribution withPayDate(LocalDate payDate) {
        this.payDate = payDate;
        return this;
    }

    public Distribution withRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
        return this;
    }

    public String productTicker() {
        return productTicker;
    }

    public MonetaryValue monetaryValue() {
        return monetaryValue;
    }

    public LocalDate exDate() {
        return exDate;
    }

    public LocalDate declareDate() {
        return declareDate;
    }

    public LocalDate payDate() {
        return payDate;
    }

    public LocalDate recordDate() {
        return recordDate;
    }
}
