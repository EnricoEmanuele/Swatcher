package sweng.swatcher.request;


import sweng.swatcher.model.Authorization;

/**
 * Created by ee on 07/10/16.
 */

public class StreamRequest extends HttpRequest {

    public StreamRequest(String ipAddress, String port, Authorization authorization) {
        super(ipAddress,port,authorization);

    }

    public String getURL(){
        return "http://"+super.getIpAddress()+":"+super.getPort();
    }


}
