package model;

import model.coffee.Coffee;

import java.util.ArrayList;

/**
 * Класс Хранилище кофе
 *
 * @author Александра Малявко
 * @version 2020
 */

public class CoffeeWarehouse {
    private ArrayList<Coffee> coffee;

    public CoffeeWarehouse() {
        coffee = new ArrayList<>();
    }

    public CoffeeWarehouse(ArrayList<Coffee> coffee) {
        this.coffee = coffee;
    }

    public void addCoffee(Coffee c) {
        coffee.add(c);
    }

    public ArrayList<Coffee> getCoffeeList() {
        return coffee;
    }
}
