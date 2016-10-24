package sweng.swatcher.command;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
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
import sweng.swatcher.util.ParametersKeys;

/**
 * Created by antoniods311 on 20/10/16.
 */

public class MediaSettingReadCommand implements CommandInterface {

    private static final String TEXT_ENABLED = "Enabled";
    private static final String TEXT_DISABLED = "Disabled";
    final String VALUE_ON = "on";

    private Context context;
    private HttpRequest httpRequest;
    private View view;

    public MediaSettingReadCommand(Context context, HttpRequest httpRequest, View view){
        this.httpRequest = httpRequest;
        this.context = context;
        this.view = view;
    }

    /**
     * Read Media Settings info
     */
    public void execute() {

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, httpRequest.getURL(), new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                //Log.i("Volley Res", response);
                httpRequest.setResponse(response);

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
                        quality.setText(parameterValue);
                        Log.i("MEDIA_SETTING_READER",ParametersKeys.QUALITY_PARAMETER+" read "+parameterValue);
                        break;
                    }
                    case ParametersKeys.PICTURE_TYPE: {
                        //Set picture type parameter
                        Spinner spinner = (Spinner) view.findViewById(R.id.picture_type);
                        if(!parameterValue.equals(null)){
                            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.picture_types, android.R.layout.simple_spinner_item);
                            int spinnerPosition = adapter.getPosition(parameterValue);
                            spinner.setSelection(spinnerPosition);
                            Log.i("MEDIA_SETTING_READER",ParametersKeys.PICTURE_TYPE+" read "+parameterValue);
                        }
                        break;
                    }
                    case ParametersKeys.OUTPUT_MOVIES: {
                        //Set movie enabled parameter
                        Switch movieSwitch = (Switch) view.findViewById(R.id.movie_switch);
                        if (parameterValue.equalsIgnoreCase(VALUE_ON)) {
                            movieSwitch.setChecked(true);
                            movieSwitch.setText(TEXT_ENABLED);
                        }
                        else {
                            movieSwitch.setChecked(false);
                            movieSwitch.setText(TEXT_DISABLED);
                        }
                        Log.i("MEDIA_SETTING_READER", ParametersKeys.OUTPUT_MOVIES + " read " + parameterValue);
                        break;
                    }
                    case ParametersKeys.MAX_MOVIE_TIME: {
                        //Set max movie time parameter
                        EditText maxMovietime = (EditText) view.findViewById(R.id.max_movie_time);
                        maxMovietime.setText(parameterValue);
                        Log.i("MEDIA_SETTING_READER",ParametersKeys.MAX_MOVIE_TIME+" read "+parameterValue);
                        break;
                    }
                    case ParametersKeys.OUTPUT_PICTURES: {
                        //Set snapshot enabled parameter
                        Switch snapshotSwitch = (Switch) view.findViewById(R.id.snapshot_switch);
                        if(parameterValue.equalsIgnoreCase(VALUE_ON)) {
                            snapshotSwitch.setChecked(true);
                            snapshotSwitch.setText(TEXT_ENABLED);
                        }
                        else{
                            snapshotSwitch.setChecked(false);
                            snapshotSwitch.setText(TEXT_DISABLED);
                        }
                        Log.i("MEDIA_SETTING_READER",ParametersKeys.OUTPUT_PICTURES+" read "+parameterValue);
                        break;
                    }
                    case ParametersKeys.THRESHOLD: {
                        //Set threshold parameter
                        EditText threshold = (EditText) view.findViewById(R.id.threshold);
                        threshold.setText(parameterValue);
                        Log.i("MEDIA_SETTING_READER",ParametersKeys.THRESHOLD+" read "+parameterValue);
                        break;
                    }
                    case ParametersKeys.SNAPSHOT_INTERVAL: {
                        //Set continuous snapshot interval
                        EditText snapshotInterval = (EditText)view.findViewById(R.id.snapshot_interval);
                        snapshotInterval.setText(parameterValue);
                        Log.i("MEDIA_SETTING_READER",ParametersKeys.SNAPSHOT_INTERVAL+" read "+parameterValue);
                        break;
                    }
                }
                //Enable saveButton
                FloatingActionButton saveButton = (FloatingActionButton) view.findViewById(R.id.save_ms_button);
                saveButton.setEnabled(true);
                saveButton.setClickable(true);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("MediaSettingReadCommand", "onErrorResponse Volley Res: " + error.getMessage());
                Snackbar.make(view, "Error reading current setting on Server: "+error.getMessage(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
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

    public Context getContext() {
        return context;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public View getView() {
        return view;
    }
}
