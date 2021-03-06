package model;

import model.coffee.Coffee;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Класс Кофейня. Определяется списком кофе в наличии и бюджетом
 *
 * @author Александра Малявко
 * @version 2020
 */

public class CoffeeShop implements ICoffeeShopRemote {
    private final ArrayList<Coffee> coffeeStorage;

    private BigDecimal budget;

    public CoffeeShop(BigDecimal budget) {
        if (budget.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Budget must be positive!");
        }

        this.budget = budget;
        coffeeStorage = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "CoffeeShop{" +
                "coffeeStorage=" + coffeeStorage +
                ", budget=" + budget +
                '}';
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void pay(BigDecimal price) {
        budget = budget.subtract(price);
    }

    public void addCoffee(Coffee coffee) {
        coffeeStorage.add(coffee);
    }
}
