package model.comparators;

import model.coffee.Coffee;

import java.util.Comparator;

/**
 * Компаратор кофе по проценту содержания арабики
 *
 * @author Александра Малявко
 * @version 2020
 */


public class CoffeeArabicaComparator implements Comparator<Coffee> {
    @Override
    public int compare(Coffee o1, Coffee o2) {
        return Integer.compare(o1.getArabicaPercent(), o2.getArabicaPercent());
    }
}
