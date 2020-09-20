package model.builder;

import model.coffee.Coffee;
import model.coffee.GroundCoffee;
import model.CoffeeException;
import model.coffee_enums.GroundCoffeeGrinding;

public class GroundCoffeeBuilder extends CoffeeBuilder {
    private GroundCoffeeGrinding grinding;

    public GroundCoffeeBuilder(double weight, String country,
                               String name, GroundCoffeeGrinding grinding) {
        super(weight, country, name);
        this.grinding = grinding;
    }

    @Override
    public Coffee Build() throws CoffeeException {
        super.Check();
        return new GroundCoffee(name, roasting,
                weight, arabicaPercent,
                country, productionDate,
                pricePerKilo, grinding);
    }
}
