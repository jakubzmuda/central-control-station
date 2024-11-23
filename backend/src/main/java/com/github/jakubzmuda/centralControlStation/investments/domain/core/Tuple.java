package com.github.jakubzmuda.centralControlStation.investments.domain.core;

import java.util.Objects;

public class Tuple<T> {

    private final T first;
    private final T second;

    private Tuple(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public static <T> Tuple<T> of(T first, T second) {
        return new Tuple<>(first, second);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?> tuple = (Tuple<?>) o;
        return Objects.equals(first, tuple.first) && Objects.equals(second, tuple.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "Tuple{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }

    public T first() {
        return first;
    }

    public T second() {
        return second;
    }

}
