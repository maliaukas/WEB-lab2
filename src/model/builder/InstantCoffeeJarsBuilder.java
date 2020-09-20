package model.builder;

import model.сoffee.Coffee;
import model.сoffee.InstantCoffeeJars;
import model.CoffeeException;
import model.coffee_enums.InstantCoffeeType;

public class InstantCoffeeJarsBuilder extends CoffeeBuilder {
    private double jarVolume;
    private InstantCoffeeType type;

    public InstantCoffeeJarsBuilder(double weight, String country,
                                    String name, double jarVolume,
                                    InstantCoffeeType type) {
        super(weight, country, name);
        this.jarVolume = jarVolume;
        this.type = type;
    }

    @Override
    public Coffee Build() throws CoffeeException {
        Check();
        return new InstantCoffeeJars(name, roasting,
                weight, arabicaPercent,
                country, productionDate,
                pricePerKilo, type, jarVolume);
    }

    @Override
    protected void Check() throws CoffeeException {
        super.Check();
        if (jarVolume <= 0) {
            throw new CoffeeException("Jar volume must be positive!");
        }
    }
}
