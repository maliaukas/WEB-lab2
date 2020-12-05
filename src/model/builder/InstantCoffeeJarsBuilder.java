package model.builder;

import model.coffee.Coffee;
import model.coffee.InstantCoffeeJars;
import model.CoffeeException;
import model.coffee_enums.InstantCoffeeType;

/*
 * Класс InstantCoffeeJarsBuilder - строитель класса InstantCoffeeJars
 * @author Александра Малявко
 * @version 2020
 */

public class InstantCoffeeJarsBuilder extends CoffeeBuilder {
    private final double jarVolume;
    private final InstantCoffeeType type;

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
