package labo.hirarins.legacy.app.batch;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.Logger;

import labo.hirarins.legacy.app.RmiClientFactory;
import labo.hirarins.legacy.app.UserDaoRmi;
import labo.hirarins.legacy.app.UserDaoRmi.User;

public class Client {

    private static final Logger log = Logger.getLogger(Client.class.getName());

    private static final int WAIT_MILLISEC = 5000;

    private static boolean terminate;

    public static void main(String[] args) {
        try {
            log.info("starting client polling....");
            Runtime.getRuntime().addShutdownHook(new ShutdownHookThread());

            log.info("client polling started!");
            UserDaoRmi dao = RmiClientFactory.getUserDaoInstance();

            while (!terminate) {
                try {
                    Thread.sleep(WAIT_MILLISEC);
                } catch (InterruptedException e) {
                    log.error("interrupted main thread.", e);
                    terminate = true;
                }

                List<User> unlockTargetList = dao.findUnlockTargetUsers();
                if (unlockTargetList.isEmpty()) {
                    log.info("not found unlockTargerUsers.");
                    continue;
                }
                for (User user : unlockTargetList) {
                    try {
                        dao.unlockUser(user);
                        log.info("unlock user. new password is " + user.getUserId() + ". userId=" + user.getUserId());
                    } catch(RemoteException e) {
                        log.error("unlockUser error occured. userId=" + user.getUserId());
                    }
                }

            }

        } catch (Exception e) {
            log.error("error occured.", e);
        }
        
    }

    private static class ShutdownHookThread extends Thread {

        private static final Logger log = Logger.getLogger(ShutdownHookThread.class.getName());

        @Override
        public void run() {
            terminate = true;
            log.info("stopping client polling...");
        }
        
    }

}