package sweng.swatcher.request;

import android.content.Context;
import android.view.View;

import sweng.swatcher.model.Authorization;

/**
 * Created by ee on 18/10/16.
 */

public class MovieRequest extends HttpRequest{

    private int threadNumber;
    private View view;


    public MovieRequest(String ipAddress, String port, Authorization authorization, int threadNumber, View view) {
        super(ipAddress, port, authorization);
        this.threadNumber = threadNumber;
        this.view = view;
    }


    public String getURL(){

        return "http://"+
                super.getIpAddress()+
                ":"+super.getPort()+
                "/"+threadNumber+
                "/action/makemovie";
    }

}
