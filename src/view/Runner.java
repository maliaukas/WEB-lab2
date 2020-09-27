package view;

import controller.OrderController;
import model.CoffeeException;
import model.CoffeeShop;
import model.CoffeeWarehouse;
import model.builder.CoffeeBeansBuilder;
import model.builder.GroundCoffeeBuilder;
import model.builder.InstantCoffeeJarsBuilder;
import model.coffee.CoffeeBeans;
import model.coffee.GroundCoffee;
import model.coffee.InstantCoffeeJars;
import model.coffee_enums.CoffeeBeansProcessing;
import model.coffee_enums.GroundCoffeeGrinding;
import model.coffee_enums.InstantCoffeeType;
import model.coffee_enums.Roasting;

import java.math.BigDecimal;
import java.util.Date;

public class Runner {
    public static void main(String[] args) {
        CoffeeBeansBuilder beansBuilder = new CoffeeBeansBuilder
                (3, "Brazil", "Sasha",
                        CoffeeBeansProcessing.WET_HULL);
        beansBuilder.setArabicaPercent(100);
        beansBuilder.setPricePerKilo(20);
        beansBuilder.setProductionDate(new Date());
        beansBuilder.setRoasting(Roasting.DARK);

        GroundCoffeeBuilder groundCoffeeBuilder = new GroundCoffeeBuilder
                (4.5, "Russia", "Jacobs",
                        GroundCoffeeGrinding.COURSE_GRIND);
        groundCoffeeBuilder.setArabicaPercent(75);
        groundCoffeeBuilder.setPricePerKilo(5);
        groundCoffeeBuilder.setProductionDate(new Date());
        groundCoffeeBuilder.setRoasting(Roasting.MEDIUM);

        InstantCoffeeJarsBuilder jarsBuilder = new InstantCoffeeJarsBuilder
                (10, "Columbia", "Coffee in jars",
                        250, InstantCoffeeType.GRANULATED);

        jarsBuilder.setArabicaPercent(90);
        jarsBuilder.setPricePerKilo(10);
        jarsBuilder.setProductionDate(new Date());
        jarsBuilder.setRoasting(Roasting.LIGHT);

        CoffeeBeans coffeeBeans;
        GroundCoffee groundCoffee;
        InstantCoffeeJars jarsCoffee;

        try {
            coffeeBeans = (CoffeeBeans) beansBuilder.Build();
            groundCoffee = (GroundCoffee) groundCoffeeBuilder.Build();
            jarsCoffee = (InstantCoffeeJars) jarsBuilder.Build();
        } catch (CoffeeException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        CoffeeWarehouse warehouse = new CoffeeWarehouse();
        warehouse.addCoffee(coffeeBeans);
        warehouse.addCoffee(jarsCoffee);
        warehouse.addCoffee(groundCoffee);

        CoffeeShop shop = new CoffeeShop(new BigDecimal("200"));

        OrderController.orderCoffee(shop, warehouse);
    }
}
