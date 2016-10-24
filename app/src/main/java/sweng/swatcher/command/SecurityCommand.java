package sweng.swatcher.command;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sweng.swatcher.request.HttpRequest;

/**
 * Created by ee on 20/10/16.
 */

public class SecurityCommand implements CommandInterface {

    private Context context;
    private HttpRequest httpRequest;
    private View view;
    private ProgressBar spinner;
    private String newUsr;
    private String newPsw;


    public SecurityCommand(Context context, HttpRequest httpRequest, View view, ProgressBar spinner, String newUsr, String newPsw) {
        this.context = context;
        this.httpRequest = httpRequest;
        this.view = view;
        this.newUsr = newUsr;
        this.newPsw = newPsw;
        //spinner = (ProgressBar)view.findViewById(R.id.progressBar);
        this.spinner = spinner;
    }

    public void execute(){
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, httpRequest.getURL(), null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                Log.i("SecurityCommand", "onResponse Volley Res: " + response.toString());
                httpRequest.setResponse(response.toString());
                spinner.setVisibility(View.GONE);
                Snackbar.make(view, response.toString(), Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.i("SecurityCommand", "onErrorResponse Volley Res: " + error.getMessage());
                httpRequest.setResponse(error.getMessage());
                spinner.setVisibility(View.GONE);
                Snackbar.make(view, "Error", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                String credentials = httpRequest.getAuthorization().getUsername()+":"+ httpRequest.getAuthorization().getPassword();
                String auth = httpRequest.getAuthorization().getAuthType()+ " " + android.util.Base64.encodeToString(credentials.getBytes(), android.util.Base64.NO_WRAP);
                headers.put("Authorization", auth);

                headers.put("new_username", newUsr);
                headers.put("new_password", newPsw);

                return headers;
            }
        };
        // Add the httpRequest to the RequestQueue.
        queue.add(jsonObjRequest);
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

    public ProgressBar getSpinner() {
        return spinner;
    }

    public String getNewUsr() {
        return newUsr;
    }

    public String getNewPsw() {
        return newPsw;
    }

}
