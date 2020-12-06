package model;

import model.coffee.Coffee;

import java.io.Serializable;

/**
 * Класс CoffeeRequest - заказ кофе со склада в формате (Кофе, Количество)
 *
 * @author Александра Малявко
 * @version 2020
 */

public class CoffeeRequest implements Serializable {
    Coffee coffee;
    public double weight;

    public CoffeeRequest(Coffee coffee, double weight) {
        this.coffee = coffee;
        this.weight = weight;
    }

    public Coffee getCoffee() {
        return coffee;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "CoffeeRequest{" +
                "coffee=" + coffee +
                ", weight=" + weight +
                '}';
    }
}
