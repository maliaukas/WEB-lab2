package model.comparators;

import model.coffee.Coffee;

import java.util.Comparator;

/**
 * Компаратор кофе по цене за килограмм
 *
 * @author Александра Малявко
 * @version 2020
 */

public class CoffeePriceComparator implements Comparator<Coffee> {
    @Override
    public int compare(Coffee o1, Coffee o2) {
        return o1.getPricePerKilo().compareTo(o2.getPricePerKilo());
    }
}
