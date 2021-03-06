package model.builder;

import model.coffee.Coffee;
import model.coffee.GroundCoffee;
import model.CoffeeException;
import model.coffee_enums.GroundCoffeeGrinding;

/*
 * Класс GroundCoffeeBuilder - строитель класса GroundCoffee
 * @author Александра Малявко
 * @version 2020
 */

public class GroundCoffeeBuilder extends CoffeeBuilder {
    private final GroundCoffeeGrinding grinding;

    public GroundCoffeeBuilder(double weight, String country,
                               String name, GroundCoffeeGrinding grinding) {
        super(weight, country, name);
        this.grinding = grinding;
    }

    @Override
    public Coffee build() throws CoffeeException {
        super.check();
        return new GroundCoffee(name, roasting,
                weight, arabicaPercent,
                country, productionDate,
                pricePerKilo, grinding);
    }
}
