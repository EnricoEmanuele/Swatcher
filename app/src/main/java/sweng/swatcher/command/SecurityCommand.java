package sweng.swatcher.command;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sweng.swatcher.fragment.CustomListViewAdapter;
import sweng.swatcher.R;
import sweng.swatcher.model.Media;
import sweng.swatcher.request.HttpRequest;

/**
 * Created by ee on 20/10/16.
 */

public class SecurityCommand implements CommandInterface {

    private Context ctx;
    private HttpRequest request;
    private View view;
    private ProgressBar spinner;
    private String new_usr;
    private String new_psw;


    public SecurityCommand(HttpRequest request, Context ctx, View view, ProgressBar spinner, String new_usr, String new_psw) {
        this.request = request;
        this.ctx = ctx;
        this.view = view;
        this.new_usr = new_usr;
        this.new_psw = new_psw;
        //spinner = (ProgressBar)view.findViewById(R.id.progressBar);
        this.spinner = spinner;
    }

    public void execute(){
        RequestQueue queue = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, request.getURL(), null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                Log.i("Volley Res:", response.toString());
                request.setResponse(response.toString());
                spinner.setVisibility(View.GONE);
                Snackbar.make(view, response.toString(), Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.i("Volley Res:", error.getMessage());
                request.setResponse(error.getMessage());
                spinner.setVisibility(View.GONE);
                Snackbar.make(view, "Error", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                String credentials = request.getAuthorization().getUsername()+":"+request.getAuthorization().getPassword();
                String auth = request.getAuthorization().getAuthType()+ " " + android.util.Base64.encodeToString(credentials.getBytes(), android.util.Base64.NO_WRAP);
                headers.put("Authorization", auth);

                headers.put("new_username", new_usr);
                headers.put("new_password", new_psw);

                return headers;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(jsonObjRequest);
    }


}
