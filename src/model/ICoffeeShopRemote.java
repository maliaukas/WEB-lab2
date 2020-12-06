package model;

import model.coffee.Coffee;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Интерфейс ICoffeeShopRemote, определяющий методы для удаленной работы с кофейней
 *
 * @author Александра Малявко
 * @version 2020
 */

public interface ICoffeeShopRemote extends Remote {
    /**
     * Метод, добавляющий кофе в кофейню
     *
     * @param coffee кофе, который надо добавить
     * @throws RemoteException в случае некорректной работы с RMI
     */
    void addCoffee(Coffee coffee) throws RemoteException;

    /**
     * Метод, производящий оплату
     *
     * @param price цена, которую надо заплатить
     * @throws RemoteException в случае некорректной работы с RMI
     */
    void pay(BigDecimal price) throws RemoteException;
}
