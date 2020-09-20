package model.сoffee;

import model.coffee_enums.GroundCoffeeGrinding;
import model.coffee_enums.Roasting;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Молотый кофе
 */

public class GroundCoffee extends Coffee {
    private GroundCoffeeGrinding grinding;

    public GroundCoffee(String name, Roasting roasting,
                        double weight, int arabicaPercent,
                        String country, Date productionDate,
                        BigDecimal pricePerKilo,
                        GroundCoffeeGrinding grinding) {
        super(name, roasting, weight, arabicaPercent, country, productionDate, pricePerKilo);
        this.grinding = grinding;
    }


    @Override
    public void sell(double weight) {
        this.weight -= weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GroundCoffee that = (GroundCoffee) o;
        return grinding == that.grinding;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), grinding);
    }

    public GroundCoffeeGrinding getGrinding() {
        return grinding;
    }
}
