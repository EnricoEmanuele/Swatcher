package sweng.swatcher.request;

import sweng.swatcher.model.Authorization;

/**
 * Created by antoniods311 on 21/10/16.
 */

public class WriteMediaSettingRequest extends HttpRequest {

    private int threadNumber;

    public WriteMediaSettingRequest(String ipAddress, String port, Authorization authorization, int threadNumber) {
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
                "/config/write";
    }
}
