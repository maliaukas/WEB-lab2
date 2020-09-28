package model.comparators;

import model.coffee.Coffee;

import java.util.Comparator;

/**
 * Компаратор кофе по стране-производителю
 *
 * @author Александра Малявко
 * @version 2020
 */

public class CoffeeCountryComparator implements Comparator<Coffee> {
    @Override
    public int compare(Coffee o1, Coffee o2) {
        return o1.getCountry().compareTo(o2.getCountry());
    }
}
