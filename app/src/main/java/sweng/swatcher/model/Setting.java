package sweng.swatcher.model;

/**
 * Created by ee on 10/10/16.
 */

public class Setting implements Cloneable{

    private String ipAddress;
    private String ip1;
    private String ip2;
    private String ip3;
    private String ip4;
    private String streamingPort;
    private String commandPort;
    private String webServerPort;
    private String username;
    private String password;
    private String newUsername;
    private String newPassword;

    public Setting() {
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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

    public String getIp1() {
        return ip1;
    }

    public void setIp1(String ip1) {
        this.ip1 = ip1;
    }

    public String getIp2() {
        return ip2;
    }

    public void setIp2(String ip2) {
        this.ip2 = ip2;
    }

    public String getIp3() {
        return ip3;
    }

    public void setIp3(String ip3) {
        this.ip3 = ip3;
    }

    public String getIp4() {
        return ip4;
    }

    public void setIp4(String ip4) {
        this.ip4 = ip4;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Setting setting = (Setting) o;

        if (!ipAddress.equals(setting.ipAddress)) return false;
        if (!ip1.equals(setting.ip1)) return false;
        if (!ip2.equals(setting.ip2)) return false;
        if (!ip3.equals(setting.ip3)) return false;
        if (!ip4.equals(setting.ip4)) return false;
        if (!streamingPort.equals(setting.streamingPort)) return false;
        if (!commandPort.equals(setting.commandPort)) return false;
        if (!webServerPort.equals(setting.webServerPort)) return false;
        if (!username.equals(setting.username)) return false;
        if (!password.equals(setting.password)) return false;
        if (!newUsername.equals(setting.newUsername)) return false;
        return newPassword.equals(setting.newPassword);
    }

    @Override
    public int hashCode() {
        int result = ipAddress.hashCode();
        result = 31 * result + ip1.hashCode();
        result = 31 * result + ip2.hashCode();
        result = 31 * result + ip3.hashCode();
        result = 31 * result + ip4.hashCode();
        result = 31 * result + streamingPort.hashCode();
        result = 31 * result + commandPort.hashCode();
        result = 31 * result + webServerPort.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + newUsername.hashCode();
        result = 31 * result + newPassword.hashCode();
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
