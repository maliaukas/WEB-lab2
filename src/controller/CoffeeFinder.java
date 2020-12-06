package controller;

import model.CoffeeWarehouse;
import model.coffee.Coffee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/*
 * Класс CoffeeFinder - поиск кофе в Хранилище
 * @author Александра Малявко
 * @version 2020
 */

public class CoffeeFinder {

    /**
     * Метод поиска кофе в хранилище по названию
     *
     * @param warehouse хранилище, в котором осуществляется поиск
     * @param name      название кофе
     * @return новое хранилище с указанными параметрами
     */

    public static CoffeeWarehouse findByName(CoffeeWarehouse warehouse, String name) {
        ArrayList<Coffee> res = new ArrayList<>();
        for (Coffee c : warehouse.getCoffeeList()) {
            if (c.getName().equals(name)) {
                res.add(c);
            }
        }
        return new CoffeeWarehouse(res);
    }

    /**
     * Метод поиска кофе в хранилище по стране-производителю
     *
     * @param warehouse хранилище, в котором осуществляется поиск
     * @param country   название страны-производителя
     * @return новое хранилище с указанными параметрами
     */

    public static CoffeeWarehouse findByCountry(CoffeeWarehouse warehouse, String country) {
        ArrayList<Coffee> res = new ArrayList<>();
        for (Coffee c : warehouse.getCoffeeList()) {
            if (c.getCountry().equals(country)) {
                res.add(c);
            }
        }
        return new CoffeeWarehouse(res);
    }

    /**
     * Метод поиска кофе в хранилище по дате изготовления
     *
     * @param warehouse хранилище, в котором осуществляется поиск
     * @param date      дата изготовления
     * @return новое хранилище с указанными параметрами
     */
    public static CoffeeWarehouse findByDate(CoffeeWarehouse warehouse, Date date) {
        ArrayList<Coffee> res = new ArrayList<>();
        for (Coffee c : warehouse.getCoffeeList()) {
            if (c.getProductionDate().equals(date)) {
                res.add(c);
            }
        }
        return new CoffeeWarehouse(res);
    }

    /**
     * Метод поиска кофе в хранилище по проценту содержания арабики
     *
     * @param warehouse хранилище, в котором осуществляется поиск
     * @param arabica   процент арабика
     * @return новое хранилище с указанными параметрами
     */

    public static CoffeeWarehouse findByArabica(CoffeeWarehouse warehouse, int arabica) {
        ArrayList<Coffee> res = new ArrayList<>();
        for (Coffee c : warehouse.getCoffeeList()) {
            if (c.getArabicaPercent() == arabica) {
                res.add(c);
            }
        }
        return new CoffeeWarehouse(res);
    }

    /**
     * Метод поиска кофе в хранилище по массе
     *
     * @param warehouse хранилище, в котором осуществляется поиск
     * @param weight    масса
     * @return новое хранилище с указанными параметрами
     */

    public static CoffeeWarehouse findByWeight(CoffeeWarehouse warehouse, int weight) {
        ArrayList<Coffee> res = new ArrayList<>();
        for (Coffee c : warehouse.getCoffeeList()) {
            if (c.getWeight() == weight) {
                res.add(c);
            }
        }
        return new CoffeeWarehouse(res);
    }

    /**
     * Метод поиска кофе в хранилище по цене за килограмм
     *
     * @param warehouse хранилище, в котором осуществляется поиск
     * @param price     цена
     * @return новое хранилище с указанными параметрами
     */

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
