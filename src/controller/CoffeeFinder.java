package controller;

import model.CoffeeWarehouse;
import model.coffee.Coffee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class CoffeeFinder {

    public static CoffeeWarehouse findByName(CoffeeWarehouse warehouse, String name) {
        ArrayList<Coffee> res = new ArrayList<>();
        for (Coffee c : warehouse.getCoffeeList()) {
            if (c.getName().equals(name)) {
                res.add(c);
            }
        }
        return new CoffeeWarehouse(res);
    }

    public static CoffeeWarehouse findByCountry(CoffeeWarehouse warehouse, String country) {
        ArrayList<Coffee> res = new ArrayList<>();
        for (Coffee c : warehouse.getCoffeeList()) {
            if (c.getCountry().equals(country)) {
                res.add(c);
            }
        }
        return new CoffeeWarehouse(res);
    }

    public static CoffeeWarehouse findByDate(CoffeeWarehouse warehouse, Date date) {
        ArrayList<Coffee> res = new ArrayList<>();
        for (Coffee c : warehouse.getCoffeeList()) {
            if (c.getProductionDate().equals(date)) {
                res.add(c);
            }
        }
        return new CoffeeWarehouse(res);
    }

    public static CoffeeWarehouse findByArabica(CoffeeWarehouse warehouse, int arabica) {
        ArrayList<Coffee> res = new ArrayList<>();
        for (Coffee c : warehouse.getCoffeeList()) {
            if (c.getArabicaPercent() == arabica) {
                res.add(c);
            }
        }
        return new CoffeeWarehouse(res);
    }

    public static CoffeeWarehouse findByWeight(CoffeeWarehouse warehouse, int weight) {
        ArrayList<Coffee> res = new ArrayList<>();
        for (Coffee c : warehouse.getCoffeeList()) {
            if (c.getWeight() == weight) {
                res.add(c);
            }
        }
        return new CoffeeWarehouse(res);
    }

    public static CoffeeWarehouse findByPrice(CoffeeWarehouse warehouse, BigDecimal price) {
        ArrayList<Coffee> res = new ArrayList<>();
        for (Coffee c : warehouse.getCoffeeList()) {
            if (c.getPricePerKilo().compareTo(price) == 0) {
                res.add(c);
            }
        }
        return new CoffeeWarehouse(res);
    }

}
