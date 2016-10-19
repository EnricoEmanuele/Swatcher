package sweng.swatcher.command;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sweng.swatcher.fragment.CustomListViewAdapter;
import sweng.swatcher.R;
import sweng.swatcher.model.Media;
import sweng.swatcher.request.HttpRequest;

/**
 * Created by ee on 18/10/16.
 */

public class GalleryCommand implements CommandInterface {

    private Context ctx;
    private HttpRequest request;
    private List<Media> mediaCollection;
    private ListView gallery_listview;
    private ArrayAdapter<Media> mediaAdapter;


    public GalleryCommand(HttpRequest request, Context ctx, ListView gallery_listview) {
        this.request = request;
        this.ctx = ctx;
        this.gallery_listview = gallery_listview;
    }

    public void execute(){
        RequestQueue queue = Volley.newRequestQueue(ctx);
        JsonArrayRequest jsonArReq = new JsonArrayRequest(Request.Method.GET, request.getURL(), null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                Log.i("Volley Res:", String.valueOf(response.length()));

                try {
                    mediaCollection = MediaParser.parse(response);
                    request.setResponse("Length: "+response.length());

                    ////////////////////////////////
                    mediaAdapter = new CustomListViewAdapter(ctx, R.layout.item_gallery, mediaCollection,request.getAuthorization());
                    gallery_listview.setAdapter(mediaAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
        queue.add(jsonArReq);
    }

    public List<Media> getMediaCollection(){
        return mediaCollection;
    }
}
