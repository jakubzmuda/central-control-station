package com.github.jakubzmuda.centralControlStation.investments.domain.core;

import java.util.Objects;

public class MonetaryValue {
    private Currency currency;
    private float amount;

    public MonetaryValue(Currency currency, float amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public static MonetaryValue of(Currency currency, float amount) {
        return new MonetaryValue(currency, amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonetaryValue that = (MonetaryValue) o;
        return Float.compare(amount, that.amount) == 0 && currency == that.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }

    @Override
    public String toString() {
        return "MonetaryValue{" +
                "currency=" + currency +
                ", value=" + amount +
                '}';
    }

    public Currency currency() {
        return currency;
    }

    public float amount() {
        return amount;
    }

    public MonetaryValue multiply(float amount) {
        return new MonetaryValue(currency, this.amount * amount);
    }
}
