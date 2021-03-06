package model.coffee;

import model.coffee_enums.InstantCoffeeType;
import model.coffee_enums.Roasting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Абстрактный класс Растворимый Кофе
 * @author Александра Малявко
 * @version 2020
 */

public abstract class InstantCoffee extends Coffee  implements Serializable {
    private final InstantCoffeeType type;

    public InstantCoffee(String name, Roasting roasting,
                         double weight, int arabicaPercent,
                         String country, Date productionDate,
                         BigDecimal pricePerKilo, InstantCoffeeType type) {
        super(name, roasting, weight, arabicaPercent, country, productionDate, pricePerKilo);
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        InstantCoffee that = (InstantCoffee) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type);
    }

    public InstantCoffeeType getType() {
        return type;
    }

}
