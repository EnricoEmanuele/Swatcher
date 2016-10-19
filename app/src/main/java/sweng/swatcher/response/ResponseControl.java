package sweng.swatcher.response;

import android.support.design.widget.Snackbar;
import android.view.View;

import sweng.swatcher.request.HttpRequest;

/**
 * Created by ee on 18/10/16.
 */

public class ResponseControl {

    private HttpRequest request;
    private View view;

    public ResponseControl(HttpRequest request, View view) {
        this.request = request;
        this.view = view;
    }

    public void checkResponse(){
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while(request.getResponse() == null) {
                        sleep(0);
                    }
                    Snackbar.make(view, request.getResponse(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }



}
