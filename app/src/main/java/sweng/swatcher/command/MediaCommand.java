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

import sweng.swatcher.command.CommandInterface;
import sweng.swatcher.request.HttpRequest;

/**
 * Created by ee on 18/10/16.
 */

public class MediaCommand implements CommandInterface {

    private Context ctx;
    private HttpRequest request;
    private View view;

    public MediaCommand(Context ctx, HttpRequest request, View view){
        this.ctx = ctx;
        this.request = request;
        this.view = view;
    }

    public void execute(){
        RequestQueue queue = Volley.newRequestQueue(ctx);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, request.getURL(), new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.i("Volley Res:", response);
                request.setResponse(response);
                Snackbar.make(view, request.getResponse(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.i("Volley Res:", error.getMessage());
                //Snackbar.make(view, "Snapshot not taken: "+error.getMessage(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
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
