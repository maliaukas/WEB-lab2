package view;

import controller.ICoffeeControllerRemote;
import model.CoffeeShop;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    private static final Logger logger =
            LogManager.getLogger(RMIClient.class.getName());

    public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.getRegistry("localhost", 2511);
            ICoffeeControllerRemote controllerRemote =
                    (ICoffeeControllerRemote) reg.lookup("controller");

            CoffeeShop shop = new CoffeeShop(new BigDecimal("200"));
            OrderMenu menu = new OrderMenu(controllerRemote);

            menu.orderCoffee(shop);
        } catch (RemoteException | NotBoundException | AlreadyBoundException e) {
            logger.error(e.getMessage());
        }
    }
}
