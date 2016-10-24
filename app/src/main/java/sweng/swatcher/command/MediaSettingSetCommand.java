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
 * Created by antoniods311 on 21/10/16.
 */

public class MediaSettingSetCommand implements CommandInterface {

    private Context context;
    private HttpRequest httpRequest;
    private View view;

    public MediaSettingSetCommand(Context context, HttpRequest httpRequest, View view){
        this.context = context;
        this.httpRequest = httpRequest;
        this.view = view;
    }

    @Override
    public void execute() {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, httpRequest.getURL(), new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                Log.i("MEDIA_SETTING_WRITE", "onResponse: " + response);
                //Snackbar.make(view, "Settings writed on Server: "+response, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                httpRequest.setResponse(response);
            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.i("MEDIA_SETTING_WRITE", "onErrorResponse: " + error.getMessage());
                Snackbar.make(view, "Error writing setting on Server: "+error.getMessage(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
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
}
