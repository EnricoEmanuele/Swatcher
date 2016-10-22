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
 * Created by antoniods311 on 22/10/16.
 */

public class RestartCommand implements CommandInterface {

    private Context ctx;
    private HttpRequest request;
    private View view;

    public RestartCommand(Context ctx, HttpRequest request, View view) {
        this.ctx = ctx;
        this.request = request;
        this.view = view;
    }

    @Override
    public void execute() {
        RequestQueue queue = Volley.newRequestQueue(ctx);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, request.getURL(), new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                Log.i("RESTART SERVER", response);
                Snackbar.make(view, "RESTART SERVER: "+response, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                request.setResponse(response);
            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.i("RESTART SERVER", error.getMessage());
                Snackbar.make(view, "Error Restarting Server: "+error.getMessage(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                request.setResponse(error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                String credentials = request.getAuthorization().getUsername()+":"+request.getAuthorization().getPassword();
                String auth = request.getAuthorization().getAuthType()+ " " + android.util.Base64.encodeToString(credentials.getBytes(), android.util.Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
