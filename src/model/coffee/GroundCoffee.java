package model.coffee;

import model.CoffeeException;
import model.coffee_enums.GroundCoffeeGrinding;
import model.coffee_enums.Roasting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Класс Молотый Кофе
 * @author Александра Малявко
 * @version 2020
 */

public class GroundCoffee extends Coffee implements Serializable {
    private final GroundCoffeeGrinding grinding;

    public GroundCoffee(String name, Roasting roasting,
                        double weight, int arabicaPercent,
                        String country, Date productionDate,
                        BigDecimal pricePerKilo,
                        GroundCoffeeGrinding grinding) {
        super(name, roasting, weight, arabicaPercent, country, productionDate, pricePerKilo);
        this.grinding = grinding;
    }


    @Override
    public Coffee sell(double weight) throws CoffeeException {
        if (weight > this.weight)
            throw new CoffeeException("Not enough coffee to sell!");
        this.weight -= weight;
        return new GroundCoffee(name,
                roasting,
                weight,
                arabicaPercent,
                country,
                productionDate,
                pricePerKilo,
                grinding);
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

    @Override
    public String toString() {
        return "GroundCoffee{" +
                "\n grinding=" + grinding +
                ",\n name='" + name + '\'' +
                ",\n country='" + country + '\'' +
                ",\n roasting=" + roasting +
                ",\n weight=" + weight +
                ",\n arabicaPercent=" + arabicaPercent +
                ",\n productionDate=" + productionDate +
                ",\n pricePerKilo=" + pricePerKilo +
                "}\n";
    }
}
