package labo.hirarins.legacy.app;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

public interface UserDaoRmi extends Remote {

    public static final String RMI_NAME = "userDao";

    public User findByUserId(String userId) throws RemoteException;

    public void addUser(User user) throws RemoteException;

    public User loginFaild(User user) throws RemoteException;

    public void requestUnlock(User user) throws RemoteException;

    public List<User> findUnlockTargetUsers() throws RemoteException;

    public User unlockUser(User user) throws RemoteException;
   
    public static class User implements Serializable {
        public static final String SESSION_KEY = "user";
        private static final long serialVersionUID = 1L;

        private String userId;
        private String userName;
        private String mailAddress;
        private String passcode;
        private Date lastLoginDate;
        private Date lastChangePasswordDate;
        private int loginFaildCount;
        private boolean lockedFlag;
        private boolean unlockTargetFlag;

        public User() {
        }

        public User(String userId, String userName, String mailAddress, String passcode) {
            this(userId, userName, mailAddress, passcode, null, null, 0, false, false);
        }

        public User(String userId, String userName, String mailAddress, String passcode, Date lastLoginDate,
                Date lastChangePasswordDate, int loginFaildCount, boolean lockedFlag, boolean unlockTargetFlag) {
            this.userId = userId;
            this.userName = userName;
            this.mailAddress = mailAddress;
            this.passcode = passcode;
            this.lastLoginDate = lastLoginDate;
            this.lastChangePasswordDate = lastChangePasswordDate;
            this.loginFaildCount = loginFaildCount;
            this.lockedFlag = lockedFlag;
            this.unlockTargetFlag = unlockTargetFlag;
        }

        public String getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }

        public String getMailAddress() {
            return mailAddress;
        }

        public String getPasscode() {
            return passcode;
        }

        public Date getLastLoginDate() {
            return this.lastLoginDate == null ? null :new Date(this.lastLoginDate.getTime());
        }

        public Date getLastChangePasswordDate() {
            return this.lastChangePasswordDate == null ? null : new Date(this.lastChangePasswordDate.getTime());
        }

        public int getLoginFaildCount() {
            return loginFaildCount;
        }

        public boolean isLockedFlag() {
            return lockedFlag;
        }
        
        public boolean isUnlockTargetFlag() {
            return unlockTargetFlag;
        }

        @Override
        public String toString() {
            return "User [lastChangePasswordDate=" + lastChangePasswordDate + ", lastLoginDate=" + lastLoginDate
                    + ", lockedFlag=" + lockedFlag + ", loginFaildCount=" + loginFaildCount + ", mailAddress="
                    + mailAddress + ", passcode=********, unlockTargetFlag=" + unlockTargetFlag + ", userId="
                    + userId + ", userName=" + userName + "]";
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setMailAddress(String mailAddress) {
            this.mailAddress = mailAddress;
        }

        public void setPasscode(String passcode) {
            this.passcode = passcode;
        }

        public void setLastLoginDate(Date lastLoginDate) {
            this.lastLoginDate = lastLoginDate;
        }

        public void setLastChangePasswordDate(Date lastChangePasswordDate) {
            this.lastChangePasswordDate = lastChangePasswordDate;
        }

        public void setLoginFaildCount(int loginFaildCount) {
            this.loginFaildCount = loginFaildCount;
        }

        public void setLockedFlag(boolean lockedFlag) {
            this.lockedFlag = lockedFlag;
        }

        public void setUnlockTargetFlag(boolean unlockTargetFlag) {
            this.unlockTargetFlag = unlockTargetFlag;
        }

    }
}
