package model.сoffee;

import model.coffee_enums.InstantCoffeeType;
import model.coffee_enums.Roasting;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class InstantCoffeeSticks extends InstantCoffee {
    private int sticksNumber;

    public InstantCoffeeSticks(String name, Roasting roasting,
                               double weight, int arabicaPercent,
                               String country, Date productionDate,
                               BigDecimal pricePerKilo, InstantCoffeeType type,
                               int sticksNumber) {
        super(name, roasting, weight, arabicaPercent, country, productionDate, pricePerKilo, type);
        this.sticksNumber = sticksNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        InstantCoffeeSticks that = (InstantCoffeeSticks) o;
        return sticksNumber == that.sticksNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sticksNumber);
    }

    public int getSticksNumber() {
        return sticksNumber;
    }

    @Override
    public void sell(double weight) {
        this.weight -= weight;
    }
}
