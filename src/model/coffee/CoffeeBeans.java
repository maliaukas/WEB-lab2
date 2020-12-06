package model.coffee;

import model.CoffeeException;
import model.coffee_enums.CoffeeBeansProcessing;
import model.coffee_enums.Roasting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Класс Зерновой Кофе
 *
 * @author Александра Малявко
 * @version 2020
 */

public class CoffeeBeans extends Coffee implements Serializable {
    private final CoffeeBeansProcessing processing;

    public CoffeeBeans(String name, Roasting roasting,
                       double weight, int arabicaPercent,
                       String country, Date productionDate,
                       BigDecimal pricePerKilo, CoffeeBeansProcessing processing) {
        super(name, roasting, weight, arabicaPercent, country, productionDate, pricePerKilo);
        this.processing = processing;
    }

    @Override
    public String toString() {
        return "CoffeeBeans{" +
                "\n processing=" + processing +
                ",\n name='" + name + '\'' +
                ",\n roasting=" + roasting +
                ",\n weight=" + weight +
                ",\n arabicaPercent=" + arabicaPercent +
                ",\n country='" + country + '\'' +
                ",\n productionDate=" + productionDate +
                ",\n pricePerKilo=" + pricePerKilo +
                "}\n";
    }

    @Override
    public Coffee sell(double weight) throws CoffeeException {
        if (weight > this.weight)
            throw new CoffeeException("Not enough coffee to sell!");
        this.weight -= weight;

        return new CoffeeBeans(name,
                roasting,
                weight,
                arabicaPercent,
                country,
                productionDate,
                pricePerKilo,
                processing);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CoffeeBeans that = (CoffeeBeans) o;
        return processing == that.processing;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), processing);
    }

    public CoffeeBeansProcessing getProcessing() {
        return processing;
    }
}
