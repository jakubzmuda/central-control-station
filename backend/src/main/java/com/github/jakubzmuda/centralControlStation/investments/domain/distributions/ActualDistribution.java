package com.github.jakubzmuda.centralControlStation.investments.domain.distributions;

import com.github.jakubzmuda.centralControlStation.investments.domain.core.MonetaryValue;
import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.DistributionId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class ActualDistribution {

    @Id
    private DistributionId id;

    private String productTicker;
    private MonetaryValue monetaryValue;
    private LocalDate exDate;
    private LocalDate declareDate;
    private LocalDate payDate;
    private LocalDate recordDate;

    private ActualDistribution() {
    }

    public ActualDistribution(DistributionId distributionId, String productTicker, MonetaryValue monetaryValue) {
        this.id = distributionId;
        this.productTicker = productTicker;
        this.monetaryValue = monetaryValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActualDistribution that = (ActualDistribution) o;
        return Objects.equals(productTicker, that.productTicker) && Objects.equals(monetaryValue, that.monetaryValue) && Objects.equals(exDate, that.exDate) && Objects.equals(declareDate, that.declareDate) && Objects.equals(payDate, that.payDate) && Objects.equals(recordDate, that.recordDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productTicker, monetaryValue, exDate, declareDate, payDate, recordDate);
    }

    @Override
    public String toString() {
        return "ActualDistribution{" +
                "source='" + productTicker + '\'' +
                ", monetaryValue=" + monetaryValue +
                ", exDate=" + exDate +
                ", declareDate=" + declareDate +
                ", payDate=" + payDate +
                ", recordDate=" + recordDate +
                '}';
    }

    public ActualDistribution withExDate(LocalDate exDate) {
        this.exDate = exDate;
        return this;
    }

    public ActualDistribution withDeclareDate(LocalDate declareDate) {
        this.declareDate = declareDate;
        return this;
    }

    public ActualDistribution withPayDate(LocalDate payDate) {
        this.payDate = payDate;
        return this;
    }

    public ActualDistribution withRecordDate(LocalDate recordDate) {
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
