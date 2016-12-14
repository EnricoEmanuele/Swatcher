package sweng.swatcher.command;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
    private final static int MEDIA_LIMIT_VALUE = 50;
    private final static String MEDIA_LIMIT_SEVERITY = "Warning";
    private final static String MEDIA_LIMIT_MESSAGE = "Too many media on Server! You shoud delete some pictures or videos!";

    public GalleryCommand(Context context, HttpRequest httpRequest, ListView galleryListView) {
        this.context = context;
        this.httpRequest = httpRequest;
        this.galleryListView = galleryListView;
    }

    public void execute() {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArReq = new JsonArrayRequest(Request.Method.GET, httpRequest.getURL(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response.length() > MEDIA_LIMIT_VALUE) {
                    //Warning for user

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());//Context parameter
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do stuff
                        }
                    });
                    builder.setTitle(MEDIA_LIMIT_SEVERITY);
                    builder.setMessage(MEDIA_LIMIT_MESSAGE);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

                Log.i("GalleryCommand", "Volley Res onResponse: " + String.valueOf(response.length()));

                try {
                    mediaCollection = MediaParser.parse(response);
                    httpRequest.setResponse("Length: " + response.length());

                    mediaAdapter = new CustomListViewAdapter(context, R.layout.item_gallery, mediaCollection, httpRequest.getAuthorization(), galleryListView);
                    galleryListView.setAdapter(mediaAdapter);

                } catch (JSONException e) {
                    Log.e("GalleryCommand", "An Exception occurs in onResponse method.");
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("GalleryCommand", "Volley Res onErrorResponse: " + error.getMessage());
                //Snackbar.make(view, "Snapshot not taken: "+error.getMessage(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                httpRequest.setResponse(error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                String credentials = httpRequest.getAuthorization().getUsername() + ":" + httpRequest.getAuthorization().getPassword();
                String auth = httpRequest.getAuthorization().getAuthType() + " " + android.util.Base64.encodeToString(credentials.getBytes(), android.util.Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        // Add the httpRequest to the RequestQueue.
        queue.add(jsonArReq);
    }

    public List<Media> getMediaCollection() {
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
