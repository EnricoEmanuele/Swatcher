package sweng.swatcher.request;

import android.view.View;

import sweng.swatcher.model.Authorization;

/**
 * Created by ee on 20/10/16.
 */

public class SecurityRequest extends HttpRequest {



    public SecurityRequest(String ipAddress, String port, Authorization authorization) {
        super(ipAddress, port, authorization);
    }

    public String getURL(){
        return "http://"+super.getIpAddress()+":"+super.getPort()+"/newCredentials.php";
    }
}
