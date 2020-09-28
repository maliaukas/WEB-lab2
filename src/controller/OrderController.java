package controller;

import model.CoffeeShop;
import model.CoffeeWarehouse;
import model.coffee.Coffee;
import model.comparators.ComparatorFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.Scanner;

/*
 * Класс OrderController - заказ кофе в кофейню
 * @author Александра Малявко
 * @version 2020
 */

public class OrderController {

    /**
     * Метод заказа кофе из хранилища в кофейню
     * @param shop кофейня, в которую происходит заказ
     * @param warehouse хранилище, из которого происходит заказ
     */

    public static void orderCoffee(CoffeeShop shop, CoffeeWarehouse warehouse) {
        Comparator<Coffee> comp = getComp();
        warehouse.getCoffeeList().sort(comp);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (warehouse.getCoffeeList().size() == 0) {
                System.out.println("No results!");
                break;
            }
            if (warehouse.getCoffeeList().size() == 1) {
                System.out.println("You've chosen:");
                Coffee chosenCoffee = warehouse.getCoffeeList().get(0);
                System.out.println(chosenCoffee);
                orderCoffee(shop, chosenCoffee);
                break;
            }
            printMenu(warehouse.getCoffeeList());
            switch (getCoffeeChoice()) {
                case 1: {
                    System.out.println("Enter the country:");
                    String country = scanner.nextLine();
                    warehouse = CoffeeFinder.findByCountry(warehouse, country);
                    break;
                }
                case 2: {
                    System.out.println("Enter the name:");
                    String name = scanner.nextLine();
                    warehouse = CoffeeFinder.findByName(warehouse, name);
                    break;
                }
                case 3: {
                    System.out.println("Enter the price:");
                    String price = scanner.nextLine();
                    warehouse = CoffeeFinder.findByPrice(warehouse, new BigDecimal(price));
                    break;
                }
            }
        }
    }

    /**
     * Метод, выводящий список доступного для заказа кофе
     * @param coffees список для вывода
     */

    private static void printMenu(ArrayList<Coffee> coffees) {
        System.out.println("List of available coffee:");
        for (Coffee c : coffees) {
            System.out.println();
            System.out.println(c.toString());
        }
    }

    /**
     * Метод, запрашивающий у пользователя способ выбора кофе
     * @return номер выбранного пункта меню
     */

    private static int getCoffeeChoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How would you like to choose the coffee?\n1. By country\n2. By name\n3. By price");
        return scanner.nextInt();
    }

    /**
     * Метод, запрашивающий у пользователя способ сортировки кофе для вывода
     * @return выбранный компаратор
     */

    private static Comparator<Coffee> getComp() {
        System.out.println("How would you like to sort the coffee?");

        for (ComparatorFactory.CompareBy v : ComparatorFactory.CompareBy.values()) {
            System.out.println("- " + v);
        }

        Scanner s = new Scanner(System.in);
        String res = s.next();

        return ComparatorFactory.getComparator
                (ComparatorFactory.CompareBy.valueOf(res.toUpperCase()));
    }

    private static void orderCoffee(CoffeeShop shop, Coffee coffee) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.ENGLISH);
        while (true) {
            System.out.println("Enter the weight of coffee to order: ");
            double weight = scanner.nextDouble();
            if (shop.getBudget().compareTo(coffee.getPrice(weight)) < 0) {
                System.out.println("Coffeeshop doesn't have enough money!");
                continue;
            }
            if (coffee.getWeight() < weight) {
                System.out.println("There's not enough coffee in stock!");
                continue;
            }

            shop.pay(coffee.getPrice(weight));
            coffee.sell(weight);
            shop.addCoffee(coffee);
            System.out.println("Success!");
            break;
        }
    }
}
