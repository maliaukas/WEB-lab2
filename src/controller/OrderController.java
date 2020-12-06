package controller;

import model.CoffeeException;
import model.CoffeeRequest;
import model.CoffeeWarehouse;
import model.ICoffeeShopRemote;
import model.builder.CoffeeBeansBuilder;
import model.builder.GroundCoffeeBuilder;
import model.builder.InstantCoffeeJarsBuilder;
import model.coffee.Coffee;
import model.coffee.CoffeeBeans;
import model.coffee.GroundCoffee;
import model.coffee.InstantCoffeeJars;
import model.coffee_enums.CoffeeBeansProcessing;
import model.coffee_enums.GroundCoffeeGrinding;
import model.coffee_enums.InstantCoffeeType;
import model.coffee_enums.Roasting;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

/*
 * Класс OrderController - управляет заказами кофе с определенного склада
 *
 * @author Александра Малявко
 * @version 2020
 */

public class OrderController implements ICoffeeControllerRemote {
    private static final Logger logger =
            LogManager.getLogger(OrderController.class.getName());

    /**
     * Склад кофе, управление которым осуществляет данный контроллер
     */
    private final CoffeeWarehouse warehouse;

    /**
     * Конструктор, заполняющий склад предопределенными значениями
     */
    public OrderController() {
        this.warehouse = setUpWarehouse();
    }

    /**
     * Метод, выполняющий начальное заполнение склада
     *
     * @return склад
     */
    private static CoffeeWarehouse setUpWarehouse() {
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
            logger.error("Error while building coffee!");
            return null;
        }

        CoffeeWarehouse warehouse = new CoffeeWarehouse();
        warehouse.addCoffee(coffeeBeans);
        warehouse.addCoffee(jarsCoffee);
        warehouse.addCoffee(groundCoffee);

        logger.debug("Warehouse successfully created!");
        return warehouse;
    }

    /**
     * Геттер склада
     *
     * @return склад
     */
    @Override
    public CoffeeWarehouse getWarehouse() {
        return warehouse;
    }

    /**
     * Метод, выполняющий заказ кофе в кофейню
     *
     * @param shop     кофейня, в которую выполняется заказ
     * @param requests список заказов
     * @throws RemoteException в случае ошибки работы с RMI
     */
    @Override
    public synchronized void orderCoffee(ICoffeeShopRemote shop,
                                         ArrayList<CoffeeRequest> requests)
            throws RemoteException {
        for (var request : requests) {
            Coffee coffee = request.getCoffee();
            double weight = request.getWeight();

            // извлекаем требуемый кофе из хранилища
            warehouse.removeCoffee(coffee);
            Coffee soldCoffee;
            try {
                // пытаемся получить требуемое кол-во
                soldCoffee = coffee.sell(weight);
            } catch (CoffeeException e) {
                // если не получилось
                logger.error("Error while buying coffee " +
                        coffee.toString() + ": "
                        + e.getMessage());

                // возвращаем кофе в хранилище
                warehouse.addCoffee(coffee);
                continue;
            }

            // если удалось получить требуемое кол-во, получаем оплату от кофейни
            shop.pay(coffee.getPrice(weight));
            // добавляем требуемый кофе в кофейню
            shop.addCoffee(soldCoffee);

            // возвращаем оставшийся кофе в хранилище
            warehouse.addCoffee(coffee);
            logger.info("Sold successfully: " + soldCoffee.toString());
        }
        logger.info("Warehouse state: " + warehouse.toString());
    }
}
