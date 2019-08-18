package labo.hirarins.legacy.app.web;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import labo.hirarins.legacy.app.ApplicationRuntimeException;
import labo.hirarins.legacy.app.RmiClientFactory;
import labo.hirarins.legacy.app.UserDaoRmi;
import labo.hirarins.legacy.app.UserDaoRmi.User;

public class Initializer {

    private static final Logger log = Logger.getLogger(Initializer.class.getName());

    private static final String[][] INIT_USERS = {
        {"user1", "test user 1", "user1@test.com"}
        , {"user2", "test user 2", "user2@test.com"}
        , {"user3", "test user 3", "user3@test.com"}
    };

    public void init() {

        try {
            log.info("start initialize...");

            UserDaoRmi userDao = RmiClientFactory.getUserDaoInstance();
            
            for (String[] rec : INIT_USERS) {
                userDao.addUser(new User(rec[0], rec[1], rec[2], Utils.convertToMD5String(rec[0])));
            }

        } catch (NoSuchAlgorithmException e) {
            throw new ApplicationRuntimeException("initialize error occured. ", e);
        } catch (RemoteException e) {
            throw new ApplicationRuntimeException("initialize error occured. ", e);
        } catch (NotBoundException e) {
            throw new ApplicationRuntimeException("initialize error occured. ", e);
        } finally {
            log.info("end initialize...");
        }

    }

}