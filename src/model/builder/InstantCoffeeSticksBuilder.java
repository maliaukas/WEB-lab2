package model.builder;

import model.coffee.Coffee;
import model.coffee.InstantCoffeeSticks;
import model.CoffeeException;
import model.coffee_enums.InstantCoffeeType;

/*
 * Класс InstantCoffeeSticksBuilder - строитель класса InstantCoffeeSticks
 * @author Александра Малявко
 * @version 2020
 */

public class InstantCoffeeSticksBuilder extends CoffeeBuilder {
    private final int sticksNumber;
    private final InstantCoffeeType type;

    public InstantCoffeeSticksBuilder(double weight, String country,
                                      String name, int sticksNumber,
                                      InstantCoffeeType type) {
        super(weight, country, name);
        this.sticksNumber = sticksNumber;
        this.type = type;
    }

    @Override
    public Coffee build() throws CoffeeException {
        check();
        return new InstantCoffeeSticks(name, roasting,
                weight, arabicaPercent,
                country, productionDate,
                pricePerKilo, type, sticksNumber);
    }

    @Override
    protected void check() throws CoffeeException {
        super.check();
        if (sticksNumber <= 0) {
            throw new CoffeeException("Sticks number must be positive!");
        }
    }
}
