package view;

import controller.OrderController;
import controller.OrderException;
import model.сoffee.CoffeeBeans;
import model.builder.CoffeeBeansBuilder;
import model.CoffeeException;
import model.CoffeeShop;
import model.coffee_enums.CoffeeBeansProcessing;
import model.coffee_enums.Roasting;

import java.math.BigDecimal;
import java.util.Date;

public class Runner {
    public static void main(String[] args) {
        CoffeeBeansBuilder cfb = new CoffeeBeansBuilder
                (3, "Brazil", "Sasha", CoffeeBeansProcessing.WET_HULL);
        cfb.setArabicaPercent(100);
        cfb.setPricePerKilo(20);
        cfb.setProductionDate(new Date());
        cfb.setRoasting(Roasting.DARK);

        CoffeeBeans beans;

        try {
            beans = (CoffeeBeans) cfb.Build();
        }
        catch (CoffeeException ex){
            System.out.println(ex.getMessage());
            return;
        }

        System.out.println("New coffee created: " + beans);

        CoffeeShop shop = new CoffeeShop(new BigDecimal("200"));

        System.out.println("New coffeeshop created: " + shop);

        try {
            OrderController.orderCoffee(shop, beans, 0.5);
        } catch (OrderException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Success! Coffeeshop after buying coffee: " + shop);

    }

}
