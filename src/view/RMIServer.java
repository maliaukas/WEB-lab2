package view;

import controller.ICoffeeControllerRemote;
import controller.OrderController;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer {
    private static final Logger logger =
            LogManager.getLogger(RMIServer.class.getName());

    public static void main(String[] args) {
        try {
            OrderController orderController = new OrderController();
            ICoffeeControllerRemote stub = (ICoffeeControllerRemote)
                    UnicastRemoteObject.exportObject(orderController, 0);

            Registry reg = LocateRegistry.createRegistry(2511);
            reg.bind("controller", stub);
        } catch (RemoteException | AlreadyBoundException e) {
            logger.error(e.getMessage());
        }
    }
}
