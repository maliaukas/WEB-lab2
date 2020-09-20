package controller;

import model.сoffee.Coffee;
import model.CoffeeShop;

public class OrderController {
    public static void orderCoffee(CoffeeShop shop, Coffee coffee, double weight)
            throws OrderException {
        if (shop.getBudget().compareTo(coffee.getPrice(weight)) < 0) {
            throw new OrderException("Coffeeshop doesn't have enough money!");
        }
        if (coffee.getWeight() < weight) {
            throw new OrderException("There's not enough coffee in stock!");
        }
        shop.pay(coffee.getPrice(weight));
        coffee.sell(weight);
        shop.addCoffee(coffee);
    }
}
