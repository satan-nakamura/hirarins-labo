package labo.hirarins.legacy.app.web.sample;


public class UserForm {

    private String userId;
    private String passcode;

    public UserForm(String userId, String passcode) {
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

    @Override
    public String toString() {
        return "UserForm [passcode=" + passcode + ", userId=" + userId + "]";
    }
    
}