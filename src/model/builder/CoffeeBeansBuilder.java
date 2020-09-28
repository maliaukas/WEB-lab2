package model.builder;

import model.CoffeeException;
import model.coffee.Coffee;
import model.coffee.CoffeeBeans;
import model.coffee_enums.CoffeeBeansProcessing;

/*
 * Класс CoffeeBeansBuilder - строитель класса CoffeeBeans
 * @author Александра Малявко
 * @version 2020
 */

public class CoffeeBeansBuilder extends CoffeeBuilder {
    private CoffeeBeansProcessing processing;

    public CoffeeBeansBuilder(double weight, String country,
                              String name, CoffeeBeansProcessing processing) {
        super(weight, country, name);
        this.processing = processing;
    }

    @Override
    public Coffee Build() throws CoffeeException {
        super.Check();
        return new CoffeeBeans(name, roasting,
                weight, arabicaPercent,
                country, productionDate,
                pricePerKilo, processing);
    }
}
