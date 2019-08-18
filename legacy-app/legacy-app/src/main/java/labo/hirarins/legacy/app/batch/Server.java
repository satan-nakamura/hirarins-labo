package labo.hirarins.legacy.app.batch;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import labo.hirarins.legacy.app.UserDaoRmi;
import labo.hirarins.legacy.app.batch.dao.UserDao;

public class Server {

    private static final Logger log = Logger.getLogger(Server.class.getName());

    public static void main(String[] args) {

        try {
            log.info("starting rmi server....");
            Runtime.getRuntime().addShutdownHook(new ShutdownHookThread());

            // bind all remote stubs
            List<BindingExceptionInfo> errors = bindRmiInterfaces(
                new RemoteBindingInfo(UserDaoRmi.RMI_NAME, UnicastRemoteObject.exportObject(new UserDao(), 0))
            );

            for (BindingExceptionInfo exceptionInfo : errors) {
                log.error("binding error occured. exceptionInfo=" + exceptionInfo);
            }

            log.info("rmi server started!");
        } catch (Exception e) {
            log.error("error occured.", e);
        }
    }

    private static class ShutdownHookThread extends Thread {

        private static final Logger log = Logger.getLogger(ShutdownHookThread.class.getName());

        @Override
        public void run() {
            log.info("shutting down batch server...");
        }
        
    }

    /**
     * bind to rmiregistory all remotes.
     * 
     * @param remotes remote binding info array
     * @return List of BindingExceptionInfo. if no error then empty.
     * @throws RemoteException if rmiregistry not started.
     */
    private static List<BindingExceptionInfo> bindRmiInterfaces(RemoteBindingInfo... remotes) throws RemoteException {
        Registry registry = LocateRegistry.getRegistry();
        List<BindingExceptionInfo> errors = new ArrayList<BindingExceptionInfo>();

        for (RemoteBindingInfo info : remotes) {
            try {
                String[] bindedNames = registry.list();
                Arrays.sort(bindedNames);
                if (Arrays.binarySearch(bindedNames, info.bindingName) < 0) {
                    registry.bind(info.bindingName, info.stub);
                } else {
                    registry.rebind(info.bindingName, info.stub);
                }

            } catch (Exception e) {
                errors.add(new BindingExceptionInfo(info, e));
            }
        }
        return errors;

    }

    private static class RemoteBindingInfo {
        private String bindingName;
        private Remote stub;

        public RemoteBindingInfo(String bindingName, Remote stub) {
            this.bindingName = bindingName;
            this.stub = stub;
        }

        @Override
        public String toString() {
            return "RemoteBindingInfo [bindingName=" + bindingName + ", stub=" + stub + "]";
        }


    }

    public static class BindingExceptionInfo {

        private RemoteBindingInfo info;
        private Exception exception;

        public BindingExceptionInfo(RemoteBindingInfo info, Exception exception) {
            this.info = info;
            this.exception = exception;
        }

        @Override
        public String toString() {
            StringWriter sw = new StringWriter();
            try {
                this.exception.printStackTrace(new PrintWriter(sw));
                return "BindingExceptionInfo [exception=" + sw.toString() + ", info=" + info + "]";
            } finally {
                try {
                    sw.close();
                } catch(IOException e) {
                    // notify close stringwriter error only.
                    log.info("BindingEceptionInfo#toString error.", e);
                }
            }
        }

        
    }
}