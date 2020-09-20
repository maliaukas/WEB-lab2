package model.coffee;

import model.coffee_enums.InstantCoffeeType;
import model.coffee_enums.Roasting;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Растворимый кофе в банке
 */

public class InstantCoffeeJars extends InstantCoffee {
    private double jarVolume;

    public InstantCoffeeJars(String name, Roasting roasting,
                             double weight, int arabicaPercent,
                             String country, Date productionDate,
                             BigDecimal pricePerKilo, InstantCoffeeType type,
                             double jarVolume) {
        super(name, roasting, weight, arabicaPercent, country, productionDate, pricePerKilo, type);
        this.jarVolume = jarVolume;
    }

    @Override
    public void sell(double weight) {
        this.weight -= weight;
    }

    public double getJarVolume() {
        return jarVolume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        InstantCoffeeJars that = (InstantCoffeeJars) o;
        return Double.compare(that.jarVolume, jarVolume) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), jarVolume);
    }

}
