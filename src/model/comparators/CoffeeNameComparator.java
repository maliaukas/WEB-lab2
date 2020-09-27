package model.comparators;

import model.coffee.Coffee;

import java.util.Comparator;

public class CoffeeNameComparator implements Comparator<Coffee> {
    @Override
    public int compare(Coffee o1, Coffee o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
