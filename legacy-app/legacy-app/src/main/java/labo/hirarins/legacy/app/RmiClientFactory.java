package labo.hirarins.legacy.app;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import labo.hirarins.legacy.app.UserDaoRmi;

public class RmiClientFactory {

    private static Registry registry;

    private RmiClientFactory() {

    }

    public static UserDaoRmi getUserDaoInstance() throws RemoteException, NotBoundException {
        if (registry == null) {
            setRegistry();
        }
        return (UserDaoRmi) registry.lookup(UserDaoRmi.RMI_NAME);
    }

    private static synchronized void setRegistry() throws RemoteException {
        if (registry == null) {
            registry = LocateRegistry.getRegistry("localhost");
        }
    }
}