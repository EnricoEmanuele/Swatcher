package sweng.swatcher.command;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import sweng.swatcher.request.HttpRequest;

/**
 * Created by ee on 18/10/16.
 */

public class MediaCommand implements CommandInterface {

    private Context context;
    private HttpRequest httpRequest;
    private View view;

    public MediaCommand(Context context, HttpRequest httpRequest, View view){
        this.context = context;
        this.httpRequest = httpRequest;
        this.view = view;
    }

    public void execute(){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, httpRequest.getURL(), new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.i("MediaCommand", "Volley Res onResponse:" + response);
                httpRequest.setResponse(response);
                Snackbar.make(view, "Snapshot taken!", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.i("MediaCommand", "Volley Res onErrorResponse: " + error.getMessage());
                //Snackbar.make(view, "Snapshot not taken: "+error.getMessage(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                httpRequest.setResponse(error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                String credentials = httpRequest.getAuthorization().getUsername()+":"+ httpRequest.getAuthorization().getPassword();
                String auth = httpRequest.getAuthorization().getAuthType()+ " " + android.util.Base64.encodeToString(credentials.getBytes(), android.util.Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        // Add the httpRequest to the RequestQueue.
        queue.add(stringRequest);
    }

    public Context getContext() {
        return context;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public View getView() {
        return view;
    }
}
