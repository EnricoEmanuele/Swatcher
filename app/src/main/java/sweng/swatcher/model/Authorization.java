package sweng.swatcher.model;


import android.util.Base64;

/**
 * Created by ee on 07/10/16.
 */

public class Authorization {

    private String username;
    private String password;
    private String authType;

    public Authorization(String username, String password, String authType) {
        this.username = username;
        this.authType = authType;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String encodeAuthorization(){
        String auth = username + ":" + password;
        return Base64.encodeToString(auth.getBytes(),Base64.NO_WRAP);


    }
}
