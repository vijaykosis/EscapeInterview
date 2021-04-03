package com.example.EscapeInterview.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Distance extends City implements Serializable {

    private int km;


    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Distance)) return false;
        if (!super.equals(o)) return false;
        Distance distance = (Distance) o;
        return km == distance.km;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), km);
    }

    @Override
    public String toString() {
        return "Distance{" +
                "km=" + km +
                '}';
    }
}
