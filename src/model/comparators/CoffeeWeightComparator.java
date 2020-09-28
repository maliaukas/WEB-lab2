package model.comparators;

import model.coffee.Coffee;

import java.util.Comparator;

/**
 * Компаратор кофе по массе
 *
 * @author Александра Малявко
 * @version 2020
 */

public class CoffeeWeightComparator implements Comparator<Coffee> {
    @Override
    public int compare(Coffee o1, Coffee o2) {
        return Double.compare(o1.getWeight(), o2.getWeight());
    }
}
