package model.comparators;

import model.coffee.Coffee;

import java.util.Comparator;

public class ComparatorFactory {

    public enum CompareBy {
        ARABICA,
        COUNTRY,
        DATE,
        NAME,
        PRICE,
        WEIGHT
    }

    public static Comparator<Coffee> getComparator(CompareBy field) {
        switch (field) {
            case DATE:
                return new CoffeeDateComparator();
            case PRICE:
                return new CoffeePriceComparator();
            case NAME:
                return new CoffeeNameComparator();
            case WEIGHT:
                return new CoffeeWeightComparator();
            case ARABICA:
                return new CoffeeArabicaComparator();
            case COUNTRY:
                return new CoffeeCountryComparator();
            default:
                throw new EnumConstantNotPresentException(field.getClass(), field.name());
        }
    }
}

