package sweng.swatcher.request;

import sweng.swatcher.model.Authorization;

/**
 * Created by antoniods311 on 22/10/16.
 */

public class RestartRequest extends HttpRequest {

    private int threadNumber;

    public RestartRequest(String ipAddress, String port, Authorization authorization, int threadNumber) {
        super(ipAddress, port, authorization);
        this.threadNumber = threadNumber;
    }

    public int getThreadNumber() {
        return threadNumber;
    }

    public void setThreadNumber(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    @Override
    public String getURL() {
        return "http://"+
                super.getIpAddress()+
                ":"+super.getPort()+
                "/"+threadNumber+
                "/action/restart";
    }
}
