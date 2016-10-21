package sweng.swatcher.request;

import sweng.swatcher.model.Authorization;

/**
 * Created by antoniods311 on 20/10/16.
 */

public class ReadMediaSettingRequest extends HttpRequest {

    private int threadNumber;
    private String parameter;

    public ReadMediaSettingRequest(String ipAddress, String port, Authorization authorization, int threadNumber, String parameter) {
        super(ipAddress, port, authorization);
        this.threadNumber = threadNumber;
        this.parameter = parameter;
    }

    public String getURL(){
        return "http://"+
                super.getIpAddress()+
                ":"+super.getPort()+
                "/"+threadNumber+
                "/config/get?query="+
                parameter;
    }

    public int getThreadNumber() {
        return threadNumber;
    }

    public void setThreadNumber(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
