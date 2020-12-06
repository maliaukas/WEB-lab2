package view;

import controller.CoffeeFinder;
import controller.ICoffeeControllerRemote;
import model.CoffeeRequest;
import model.CoffeeShop;
import model.CoffeeWarehouse;
import model.ICoffeeShopRemote;
import model.coffee.Coffee;
import model.comparators.ComparatorFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.Scanner;

/**
 * Класс-меню, осуществляющий интерактивный заказ кофе в кофейню из определенного склада,
 * связанного с контроллером
 *
 * @author Александра Малявко
 * @version 2020
 */
public class OrderMenu {
    private static final Logger logger =
            LogManager.getLogger(OrderMenu.class.getName());

    /**
     * Удаленный контроллер удаленного склада
     */
    private final ICoffeeControllerRemote controller;

    /**
     * Список заказов
     */
    private final ArrayList<CoffeeRequest> requests;

    /**
     * Копия состояния склада на какой-то момент времени,
     * либо отфильтрованный по пользовательским параметрам список кофе
     */
    private CoffeeWarehouse warehouseCopy;

    /**
     * Конструктор
     *
     * @param controller контроллер, с которым будет производиться работа
     * @throws RemoteException в случае некорректной работы с RMI
     */
    OrderMenu(ICoffeeControllerRemote controller) throws RemoteException {
        this.controller = controller;
        warehouseCopy = controller.getWarehouse();
        requests = new ArrayList<>();
    }

    /**
     * Метод, запрашивающий у пользователя способ сортировки кофе для вывода
     *
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

    /**
     * Метод, запрашивающий у пользователя способ выбора кофе
     *
     * @return номер выбранного пункта меню
     */
    private static int getCoffeeChoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("""
                How would you like to choose the coffee?
                1. By country
                2. By name
                3. By price
                """);
        return scanner.nextInt();
    }

    /**
     * Метод, формирующий новый заказ (или обновляющий уже созданный)
     *
     * @param coffee кофе
     * @param weight количество кофе
     */
    private void addRequest(Coffee coffee, double weight) {
        for (var req : requests) {
            if (req.getCoffee().equals(coffee)) {
                req.weight += weight;
                logger.debug("Request updated: " + req);
                return;
            }
        }
        var request = new CoffeeRequest(coffee, weight);
        requests.add(request);
        logger.debug("New request added: " + request);
    }

    /**
     * Метод, уточняющий у пользователя кол-во кофе, которое требуется заказать,
     * и формирующий новый заказ
     *
     * @param shop   кофейня, в которую выполняется поставка
     * @param coffee выбранный кофе
     */
    private void orderCoffee(CoffeeShop shop, Coffee coffee) {
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

            addRequest(coffee, weight);
            return;
        }
    }

    /**
     * Метод, узнающий, нужно ли продолжить формировать список заказов
     * или выполнить уже сформированные
     *
     * @return true - если продолжить, false - если выполнить
     */
    private boolean continueOrder() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("""
                1. Continue order
                2. Order selected coffee
                """);
        return scanner.nextInt() == 1;
    }

    /**
     * Метод, выводящий список доступного для заказа кофе
     */
    private void printMenu() {
        System.out.println("List of available coffee:");
        for (Coffee c : warehouseCopy.getCoffeeList()) {
            System.out.println();
            System.out.println(c.toString());
        }
    }

    /**
     * Метод заказа кофе из хранилища в кофейню
     *
     * @param shop кофейня, в которую происходит заказ
     */
    public void orderCoffee(CoffeeShop shop) throws RemoteException, AlreadyBoundException {
        Comparator<Coffee> comp = getComp();
        warehouseCopy.getCoffeeList().sort(comp);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (warehouseCopy.getCoffeeList().size() == 0) {
                System.out.println("No results or warehouse is empty!");
                warehouseCopy = controller.getWarehouse();
                continue;
            }

            if (warehouseCopy.getCoffeeList().size() == 1) {
                System.out.println("You've chosen:");
                Coffee chosenCoffee = warehouseCopy.getCoffeeList().get(0);
                System.out.println(chosenCoffee);

                orderCoffee(shop, chosenCoffee);

                if (continueOrder()) {
                    warehouseCopy = controller.getWarehouse();
                    continue;
                } else {
                    ICoffeeShopRemote coffeeShopRemote = (ICoffeeShopRemote)
                            UnicastRemoteObject.exportObject(shop, 0);

                    var reg = LocateRegistry.getRegistry("localhost", 2511);
                    reg.bind("shop", coffeeShopRemote);

                    // выполняем заказ
                    controller.orderCoffee(coffeeShopRemote, requests);

                    requests.clear();

                    logger.info("Coffeeshop state: " + shop.toString());
                    return;
                }
            }

            printMenu();
            switch (getCoffeeChoice()) {
                case 1 -> {
                    System.out.println("Enter the country:");
                    String country = scanner.nextLine();
                    warehouseCopy = CoffeeFinder.findByCountry(warehouseCopy, country);
                }
                case 2 -> {
                    System.out.println("Enter the name:");
                    String name = scanner.nextLine();
                    warehouseCopy = CoffeeFinder.findByName(warehouseCopy, name);
                }
                case 3 -> {
                    System.out.println("Enter the price:");
                    String price = scanner.nextLine();
                    warehouseCopy = CoffeeFinder.findByPrice(warehouseCopy,
                            new BigDecimal(price));
                }
            }
        }
    }
}
