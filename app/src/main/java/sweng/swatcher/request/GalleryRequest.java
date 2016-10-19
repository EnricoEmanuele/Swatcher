package sweng.swatcher.request;

import android.view.View;

import sweng.swatcher.model.Authorization;

/**
 * Created by ee on 18/10/16.
 */

public class GalleryRequest extends HttpRequest {


    public GalleryRequest(String ipAddress, String port, Authorization authorization) {
        super(ipAddress, port, authorization);
    }

    public String getURL(){

        return "http://"+
                super.getIpAddress()+
                ":"+super.getPort()+
                "/jsonEncoder.php";
    }
}
