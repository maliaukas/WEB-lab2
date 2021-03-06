package model.coffee;

import model.CoffeeException;
import model.coffee_enums.Roasting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Абстрактный класс Кофе
 *
 * @author Александра Малявко
 * @version 2020
 */

public abstract class Coffee implements Serializable {

    protected String name;
    protected String country;
    protected Roasting roasting;
    protected double weight;
    protected int arabicaPercent;
    protected Date productionDate;
    protected BigDecimal pricePerKilo;


    public Coffee(String name, Roasting roasting,
                  double weight, int arabicaPercent,
                  String country, Date productionDate,
                  BigDecimal pricePerKilo) {
        this.name = name;
        this.roasting = roasting;
        this.weight = weight;
        this.arabicaPercent = arabicaPercent;
        this.country = country;
        this.productionDate = productionDate;
        this.pricePerKilo = pricePerKilo;
    }

    public abstract Coffee sell(double weight) throws CoffeeException;

    public String getName() {
        return name;
    }

    public BigDecimal getPrice(double weight) {
        return pricePerKilo.multiply(new BigDecimal(Double.toString(weight)));
    }

    public BigDecimal getPricePerKilo() {
        return pricePerKilo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coffee coffee = (Coffee) o;
        return Double.compare(coffee.weight, weight) == 0 &&
                arabicaPercent == coffee.arabicaPercent &&
                roasting == coffee.roasting &&
                country.equals(coffee.country) &&
                productionDate.equals(coffee.productionDate) &&
                pricePerKilo.equals(coffee.pricePerKilo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roasting, weight,
                arabicaPercent, country,
                productionDate, pricePerKilo);
    }

    public Roasting getRoasting() {
        return roasting;
    }


    public double getWeight() {
        return weight;
    }


    public int getArabicaPercent() {
        return arabicaPercent;
    }


    public String getCountry() {
        return country;
    }


    public Date getProductionDate() {
        return productionDate;
    }

}
