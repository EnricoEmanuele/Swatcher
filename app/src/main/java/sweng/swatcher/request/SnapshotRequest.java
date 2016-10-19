package sweng.swatcher.request;

import android.content.Context;
import android.view.View;


import sweng.swatcher.model.Authorization;

/**
 * Created by ee on 07/10/16.
 */

public class SnapshotRequest extends HttpRequest {

    private int threadNumber;
    private View view;


    public SnapshotRequest(String ipAddress, String port, Authorization authorization, int threadNumber, View view) {
        super(ipAddress, port, authorization);
        this.threadNumber = threadNumber;
        this.view = view;
    }


    public String getURL(){

        return "http://"+
                super.getIpAddress()+
                ":"+super.getPort()+
                "/"+threadNumber+
                "/action/snapshot";
    }




}
