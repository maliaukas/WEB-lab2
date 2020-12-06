package controller;

import model.CoffeeRequest;
import model.CoffeeWarehouse;
import model.ICoffeeShopRemote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Интерфейс ICoffeeControllerRemote, определяющий методы
 * для удаленной работы с контроллером
 *
 * @author Александра Малявко
 * @version 2020
 */

public interface ICoffeeControllerRemote extends Remote {
    void orderCoffee(ICoffeeShopRemote shop, ArrayList<CoffeeRequest> requests)
            throws RemoteException;

    CoffeeWarehouse getWarehouse() throws RemoteException;
}
