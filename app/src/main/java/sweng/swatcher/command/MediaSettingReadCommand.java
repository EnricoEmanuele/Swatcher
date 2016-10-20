package sweng.swatcher.command;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import sweng.swatcher.R;
import sweng.swatcher.request.HttpRequest;
import sweng.swatcher.request.ReadMediaSettingRequest;
import sweng.swatcher.util.ParametersKeys;

/**
 * Created by antoniods311 on 20/10/16.
 */

public class MediaSettingReadCommand implements CommandInterface {

    private Context ctx;
    private HttpRequest request;
    private View view;

    public MediaSettingReadCommand(Context ctx, HttpRequest request, View view){
        this.request = request;
        this.ctx = ctx;
        this.view = view;
    }

    /**
     * Read Media Settings info
     */
    public void execute() {

        RequestQueue queue = Volley.newRequestQueue(ctx);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, request.getURL(), new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.i("Volley Res", response);
                request.setResponse(response);

                //Parsing Response
                StringTokenizer tokenizer = new StringTokenizer(response);
                String parameterKey = tokenizer.nextToken();
                tokenizer.nextToken();
                String parameterValue = tokenizer.nextToken();

                //Update View
                switch (parameterKey){
                    case ParametersKeys.QUALITY_PARAMETER: {
                        //Set quality parameter
                        EditText quality = (EditText) view.findViewById(R.id.quality_image);
                        quality.setText(Integer.parseInt(parameterValue));
                    }
                    case ParametersKeys.PICTURE_TYPE: {
                        //Set picture type parameter
                        Spinner spinner = (Spinner) view.findViewById(R.id.picture_type);
                        String[] valuesArray = ctx.getResources().getStringArray(R.array.picture_types);
                        if(!parameterValue.equals(null)){
                            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ctx, R.array.picture_types, android.R.layout.simple_spinner_item);
                            int spinnerPosition = adapter.getPosition(parameterValue);
                            spinner.setSelection(spinnerPosition);
                        }

                    }
                    case ParametersKeys.MAX_MOVIE_TIME: {

                    }
                    case ParametersKeys.OUTPUT_PICTURES: {

                    }
                    case ParametersKeys.THRESHOLD: {

                    }
                    case ParametersKeys.SNAPSHOT_INTERVAL: {

                    }

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
        queue.add(stringRequest);


    }


}
