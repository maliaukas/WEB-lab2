package model.comparators;

import model.coffee.Coffee;

import java.util.Comparator;

public class CoffeeWeightComparator implements Comparator<Coffee> {
    @Override
    public int compare(Coffee o1, Coffee o2) {
        return Double.compare(o1.getWeight(), o2.getWeight());
    }
}
