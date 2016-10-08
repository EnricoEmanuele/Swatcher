package sweng.swatcher.model;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ee on 07/10/16.
 */

public class SnapshotRequest extends HttpRequest {

    private int threadNumber;
    private Context ctx;
    private View view;


    public SnapshotRequest(String ipAddress, String port, Authorization authorization, int threadNumber, Context ctx, View view) {
        super(ipAddress, port, authorization);
        this.threadNumber = threadNumber;
        this.ctx = ctx;
        this.view = view;
    }


    public int sendRequest() {
        RequestQueue queue = Volley.newRequestQueue(ctx);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://87.17.157.85:4321/0/action/snapshot", new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                //la chiamata è asincrona quindi la risposta arriva in un tempo dilatato rispetto alla richiesta,
                //pertanto il messaggio della snackbar deve essere chiamato all'interno di tale metodo e non fuori
                Log.i("Volley Res:", response);
                Snackbar.make(view, "Snapshot taken: "+response, Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.i("Volley Res:", error.getMessage());
                Snackbar.make(view, "Snapshot not taken: "+error.getMessage(), Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                String credentials = "user:password";
                String auth = "Basic " + android.util.Base64.encodeToString(credentials.getBytes(), android.util.Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        return 0;

    }


    public String getURL(){

        return "http://"+
                super.getIpAddress()+
                ":"+super.getPort()+
                "/"+threadNumber+
                "/action/snapshot";
    }




}
