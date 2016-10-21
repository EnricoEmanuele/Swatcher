package sweng.swatcher.request;

import sweng.swatcher.model.Authorization;

/**
 * Created by antoniods311 on 21/10/16.
 */

public class SetMediaSettingRequest extends HttpRequest {

    private int threadNumber;
    private String parameter;
    private String value;

    public SetMediaSettingRequest(String ipAddress, String port, Authorization authorization, int threadNumber, String parameter, String value) {
        super(ipAddress, port, authorization);
        this.threadNumber = threadNumber;
        this.parameter = parameter;
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getURL() {
        return "http://"+
                super.getIpAddress()+
                ":"+super.getPort()+
                "/"+threadNumber+
                "/config/set?"+parameter+"="+value;
    }
}
