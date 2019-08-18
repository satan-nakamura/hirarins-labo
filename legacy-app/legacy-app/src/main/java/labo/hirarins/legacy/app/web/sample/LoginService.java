package labo.hirarins.legacy.app.web.sample;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import labo.hirarins.legacy.app.ApplicationException;
import labo.hirarins.legacy.app.ApplicationRuntimeException;
import labo.hirarins.legacy.app.UserDaoRmi;
import labo.hirarins.legacy.app.UserDaoRmi.User;
import labo.hirarins.legacy.app.RmiClientFactory;
import labo.hirarins.legacy.app.web.Utils;

public class LoginService {

    private static final Logger log = Logger.getLogger(LoginService.class.getName());

    public LoginService() {

    }

    public User login(UserForm userForm) throws InvalidLoginException, UserLockedException {

        try {
            UserDaoRmi dao = RmiClientFactory.getUserDaoInstance();
            User user = dao.findByUserId(userForm.getUserId());

            if (user == null) {
                log.info("not exists users. userId=" + userForm.getUserId());
                throw new InvalidLoginException("login faild.");
            }
            String inputPasscode = Utils.convertToMD5String(userForm.getPasscode());
            if (user.isLockedFlag()) {
                log.info("locked user. userId=" + userForm.getUserId());
                throw new UserLockedException("already locked user.");
            } else if (!inputPasscode.equals(user.getPasscode())) {
                log.info("not match passcode. userId=" + userForm.getUserId());
                dao.loginFaild(user);
                throw new InvalidLoginException("login faild.");
            }

            return dao.findByUserId(userForm.getUserId());
        } catch (RemoteException e) {
            throw new ApplicationRuntimeException("login error occured.", e);
        } catch (NoSuchAlgorithmException e) {
            throw new ApplicationRuntimeException("login error occured.", e);
        } catch (NotBoundException e) {
            throw new ApplicationRuntimeException("login error occured.", e);
        }
    }

    public static class InvalidLoginException extends ApplicationException {

        private static final long serialVersionUID = 1L;

        public InvalidLoginException() {
        }

        public InvalidLoginException(String message) {
            super(message);
        }

        public InvalidLoginException(Throwable cause) {
            super(cause);
        }

        public InvalidLoginException(String message, Throwable cause) {
            super(message, cause);
        }

        public InvalidLoginException(String message, Throwable cause, boolean enableSuppression,
                boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }

    }

    public static final class UserLockedException extends ApplicationException {

        private static final long serialVersionUID = 1L;

        public UserLockedException() {
        }

        public UserLockedException(String message) {
            super(message);
        }

        public UserLockedException(Throwable cause) {
            super(cause);
        }

        public UserLockedException(String message, Throwable cause) {
            super(message, cause);
        }

        public UserLockedException(String message, Throwable cause, boolean enableSuppression,
                boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }

    }
}