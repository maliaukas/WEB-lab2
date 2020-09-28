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
    private int sticksNumber;
    private InstantCoffeeType type;

    public InstantCoffeeSticksBuilder(double weight, String country,
                                      String name, int sticksNumber,
                                      InstantCoffeeType type) {
        super(weight, country, name);
        this.sticksNumber = sticksNumber;
        this.type = type;
    }

    @Override
    public Coffee Build() throws CoffeeException {
        Check();
        return new InstantCoffeeSticks(name, roasting,
                weight, arabicaPercent,
                country, productionDate,
                pricePerKilo, type, sticksNumber);
    }

    @Override
    protected void Check() throws CoffeeException {
        super.Check();
        if (sticksNumber <= 0) {
            throw new CoffeeException("Sticks number must be positive!");
        }
    }
}
