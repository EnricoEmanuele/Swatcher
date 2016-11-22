package sweng.swatcher.command;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sweng.swatcher.R;
import sweng.swatcher.fragment.CustomListViewAdapter;
import sweng.swatcher.model.Media;
import sweng.swatcher.model.MediaParser;
import sweng.swatcher.request.HttpRequest;

/**
 * Created by ee on 22/11/16.
 */

public class DeleteMediaCommand implements CommandInterface {

    private Context context;
    private HttpRequest httpRequest;
    private String fileToDelete;
    private View view;


    public DeleteMediaCommand(Context context, HttpRequest httpRequest, String fileToDelete, View view) {
        this.context = context;
        this.httpRequest = httpRequest;
        this.fileToDelete = fileToDelete;
        this.view = view;
    }

    public void execute(){
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, httpRequest.getURL(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));
                    if(obj.getString("response").equalsIgnoreCase("true")){
                        Log.i("DeleteMediaCommand", "Media deleted: " + obj.getString("response"));
                        Snackbar.make(view, "Media deleted", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                    }
                    else{
                        Log.i("DeleteMediaCommand", "Media deleted: " + obj.getString("response"));
                        Snackbar.make(view, "Error. File not found!", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                    }
                    httpRequest.setResponse("Response Media deleted: "+response.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                    httpRequest.setResponse("Response Media deleted: "+response.toString());
                }

            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.i("DeleteMediaCommand", "Volley Res onErrorResponse: " + error.getMessage());
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

                headers.put("fileToDelete", fileToDelete);

                return headers;
            }
        };
        // Add the httpRequest to the RequestQueue.
        queue.add(jsonObjReq);
    }


    public Context getContext() {
        return context;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }


}
