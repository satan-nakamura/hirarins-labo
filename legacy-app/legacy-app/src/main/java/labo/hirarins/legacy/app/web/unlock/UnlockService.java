package labo.hirarins.legacy.app.web.unlock;

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

public class UnlockService {

    private static final Logger log = Logger.getLogger(UnlockService.class.getName());

    public UnlockService() {

    }

    public User requestUnlock(UnlockForm unlockForm) throws InvalidUnlockRequestException {

        try {
            UserDaoRmi dao = RmiClientFactory.getUserDaoInstance();
            User user = dao.findByUserId(unlockForm.getUserId());
    
            if (user == null) {
                log.info("not exists users. userId=" + unlockForm.getUserId());
                throw new InvalidUnlockRequestException("not exists user.");
            }
                        
            String inputPasscode = Utils.convertToMD5String(unlockForm.getPasscode());
            if (!inputPasscode.equals(user.getPasscode())) {
                log.info("not match passcode. userId=" + unlockForm.getUserId());
                throw new InvalidUnlockRequestException("not match passcode.");

            } 
            
            if (!user.isLockedFlag()) {
                log.info("user is not locked. userId=" + unlockForm.getUserId());
                throw new InvalidUnlockRequestException("not locked user.");
            } else if (user.isUnlockTargetFlag()) {
                log.info("already accepted unlock request. userId=" + unlockForm.getUserId());
                throw new InvalidUnlockRequestException("already accepted unlock request.");
            }

            dao.requestUnlock(user);
            log.info("accept unlock request. userId=" + unlockForm.getUserId());
            return dao.findByUserId(unlockForm.getUserId());
    
        } catch(RemoteException e) {
            log.error("requestunlock error occured.", e);
            throw new ApplicationRuntimeException("requestunlock error occured.", e);
        } catch (NotBoundException e) {
            log.error("requestunlock error occured.", e);
            throw new ApplicationRuntimeException("requestunlock error occured.", e);
        } catch (NoSuchAlgorithmException e) {
            log.error("requestunlock error occured.", e);
            throw new ApplicationRuntimeException("requestunlock error occured.", e);
		}

    }
    

    public static class InvalidUnlockRequestException extends ApplicationException {

        private static final long serialVersionUID = 1L;

        public InvalidUnlockRequestException() {
        }

        public InvalidUnlockRequestException(String message) {
            super(message);
        }

        public InvalidUnlockRequestException(Throwable cause) {
            super(cause);
        }

        public InvalidUnlockRequestException(String message, Throwable cause) {
            super(message, cause);
        }

        public InvalidUnlockRequestException(String message, Throwable cause, boolean enableSuppression,
                boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }

    }
}