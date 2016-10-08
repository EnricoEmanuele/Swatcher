package sweng.swatcher.model;

/**
 * Created by ee on 07/10/16.
 */

public abstract class HttpRequest {

    private String ipAddress;
    private String port;
    private Authorization authorization;

    public HttpRequest(String ipAddress, String port, Authorization authorization) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.authorization = authorization;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }

    public abstract String getURL();

    public abstract int sendRequest();
}
