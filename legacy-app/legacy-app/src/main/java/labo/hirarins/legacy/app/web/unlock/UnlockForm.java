package labo.hirarins.legacy.app.web.unlock;

public class UnlockForm {

    private String userId;
    private String passcode;

    public UnlockForm(String userId, String passcode) {
        this.userId = userId;
        this.passcode = passcode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
   
}