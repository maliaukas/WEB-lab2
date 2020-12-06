package model;

import model.coffee.Coffee;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Класс Склад кофе
 *
 * @author Александра Малявко
 * @version 2020
 */

public class CoffeeWarehouse implements Serializable {
    private final ArrayList<Coffee> coffee;

    public CoffeeWarehouse() {
        coffee = new ArrayList<>();
    }

    public CoffeeWarehouse(ArrayList<Coffee> coffee) {
        this.coffee = coffee;
    }

    public void addCoffee(Coffee c) {
        coffee.add(c);
    }

    public void removeCoffee(Coffee c) {
        coffee.remove(c);
    }

    public ArrayList<Coffee> getCoffeeList() {
        return coffee;
    }

    @Override
    public String toString() {
        return "CoffeeWarehouse{" +
                "coffee=" + coffee +
                '}';
    }
}
