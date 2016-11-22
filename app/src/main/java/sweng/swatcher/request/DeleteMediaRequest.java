package sweng.swatcher.request;

import sweng.swatcher.model.Authorization;

/**
 * Created by ee on 22/11/16.
 */

public class DeleteMediaRequest  extends HttpRequest{

    public DeleteMediaRequest(String ipAddress, String port, Authorization authorization) {
        super(ipAddress, port, authorization);
    }

    public String getURL(){

        return "http://"+
                super.getIpAddress()+
                ":"+super.getPort()+
                "/deleteMedia.php";
    }
}
