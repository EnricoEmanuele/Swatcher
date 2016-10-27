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
import sweng.swatcher.model.MediaParser;
import sweng.swatcher.request.HttpRequest;

/**
 * Created by ee on 18/10/16.
 */

public class GalleryCommand implements CommandInterface {

    private Context context;
    private HttpRequest httpRequest;
    private List<Media> mediaCollection;
    private ListView galleryListView;
    private ArrayAdapter<Media> mediaAdapter;


    public GalleryCommand(Context context, HttpRequest httpRequest, ListView galleryListView) {
        this.context = context;
        this.httpRequest = httpRequest;
        this.galleryListView = galleryListView;
    }

    public void execute(){
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArReq = new JsonArrayRequest(Request.Method.GET, httpRequest.getURL(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {
                Log.i("GalleryCommand", "Volley Res onResponse: " + String.valueOf(response.length()));

                try {
                    mediaCollection = MediaParser.parse(response);
                    httpRequest.setResponse("Length: "+response.length());

                    mediaAdapter = new CustomListViewAdapter(context, R.layout.item_gallery, mediaCollection, httpRequest.getAuthorization());
                    galleryListView.setAdapter(mediaAdapter);

                }
                catch (JSONException e) {
                    Log.e("GalleryCommand","An Exception occurs in onResponse method.");
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.i("GalleryCommand", "Volley Res onErrorResponse: " + error.getMessage());
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
        queue.add(jsonArReq);
    }

    public List<Media> getMediaCollection(){
        return mediaCollection;
    }

    public Context getContext() {
        return context;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public ListView getGalleryListView() {
        return galleryListView;
    }

    public ArrayAdapter<Media> getMediaAdapter() {
        return mediaAdapter;
    }

}
