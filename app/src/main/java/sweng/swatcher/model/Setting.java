package sweng.swatcher.model;

/**
 * Created by ee on 10/10/16.
 */

public class Setting {

    private String ipAddress;
    private String streamingPort;
    private String commandPort;
    private String webServerPort;
    private String username;
    private String password;
    private String newUsername;
    private String newPassword;

    public Setting() {
    }

    public String getCommandPort() {
        return commandPort;
    }

    public void setCommandPort(String commandPort) {
        this.commandPort = commandPort;
    }

    public void setWebServerPort(String webServerPort) {
        this.webServerPort = webServerPort;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public String getWebServerPort() {
        return webServerPort;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getStreamingPort() {
        return streamingPort;
    }

    public void setStreamingPort(String streamingPort) {
        this.streamingPort = streamingPort;
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


}
