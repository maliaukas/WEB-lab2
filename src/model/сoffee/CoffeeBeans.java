package model.сoffee;

import model.coffee_enums.CoffeeBeansProcessing;
import model.coffee_enums.Roasting;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Зерновой кофе
 */

public class CoffeeBeans extends Coffee {
    private CoffeeBeansProcessing processing;

    public CoffeeBeans(String name, Roasting roasting,
                       double weight, int arabicaPercent,
                       String country, Date productionDate,
                       BigDecimal pricePerKilo, CoffeeBeansProcessing processing) {
        super(name, roasting, weight, arabicaPercent, country, productionDate, pricePerKilo);
        this.processing = processing;
    }

    @Override
    public void sell(double weight) {
        this.weight -= weight;
    }


    @Override
    public String toString() {
        return "CoffeeBeans{" +
                "processing=" + processing +
                ", name='" + name + '\'' +
                ", roasting=" + roasting +
                ", weight=" + weight +
                ", arabicaPercent=" + arabicaPercent +
                ", country='" + country + '\'' +
                ", productionDate=" + productionDate +
                ", pricePerKilo=" + pricePerKilo +
                '}';
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
