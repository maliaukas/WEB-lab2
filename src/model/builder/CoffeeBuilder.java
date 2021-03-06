package model.builder;

import model.CoffeeException;
import model.coffee.Coffee;
import model.coffee_enums.Roasting;

import java.math.BigDecimal;
import java.util.Date;

/*
 * Абстрактный класс CoffeeBuilder - строитель класса Coffee
 * @author Александра Малявко
 * @version 2020
 */

public abstract class CoffeeBuilder {
    protected Roasting roasting;
    protected double weight;
    protected int arabicaPercent;
    protected String country;
    protected Date productionDate;
    protected BigDecimal pricePerKilo;
    protected String name;

    public CoffeeBuilder(double weight, String country, String name) {
        this.weight = weight;
        this.country = country;
        this.name = name;
    }

    public abstract Coffee build() throws CoffeeException;

    protected void check() throws CoffeeException {
        if (pricePerKilo.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CoffeeException("Price must be positive!");
        }
        if (weight <= 0) {
            throw new CoffeeException("Weight must be positive!");
        }
        if (arabicaPercent > 100 || arabicaPercent < 0) {
            throw new CoffeeException("Percent of Arabica must be from 0 to 100!");
        }
        if (country.isEmpty()) {
            throw new CoffeeException("Country must have value!");
        }
        if (name.isEmpty()) {
            throw new CoffeeException("Name must have value!");
        }
    }

    public void setPricePerKilo(double pricePerKilo) {
        this.pricePerKilo = new BigDecimal(Double.toString(pricePerKilo));
    }

    public void setPricePerKilo(BigDecimal pricePerKilo) {
        this.pricePerKilo = pricePerKilo;
    }

    public void setRoasting(Roasting roasting) {
        this.roasting = roasting;
    }

    public void setArabicaPercent(int arabicaPercent) {
        this.arabicaPercent = arabicaPercent;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

}
