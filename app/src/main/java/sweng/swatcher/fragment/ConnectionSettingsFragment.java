package sweng.swatcher.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import sweng.swatcher.R;
import sweng.swatcher.activity.MainActivity;
import sweng.swatcher.util.ConnectionParameterLimit;
import sweng.swatcher.util.PreferecesKeys;
import sweng.swatcher.model.Setting;
import sweng.swatcher.util.ConnectionParameterLimit.*;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConnectionSettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConnectionSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConnectionSettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Setting setting;

    EditText ip1;
    EditText ip2;
    EditText ip3;
    EditText ip4;

    EditText streamingPort;
    EditText commandPort;
    EditText webPort;

    EditText username;
    EditText password;

    FloatingActionButton saveButton;

    SharedPreferences sharedPreferences;
    //SharedPreferences.Editor editor;

    public ConnectionSettingsFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConnectionSettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConnectionSettingsFragment newInstance(String param1, String param2) {
        ConnectionSettingsFragment fragment = new ConnectionSettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mParam1 = getArguments().getString(ARG_PARAM1);
            this.mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connection_settings, container, false);
        //Handle page
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        // Get Editor from SharedPreferencies
        SharedPreferences.Editor editor = this.sharedPreferences.edit();

        handleConnectionSettings(view);
        setPreviousValue();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (this.mListener != null) {
            this.mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.mListener = (OnFragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    //Funzione di gestione della pagina Connection Settings
    private void handleConnectionSettings(View view){

        //Initialize page element
        this.ip1 = (EditText) view.findViewById(R.id.IP1);
        this.ip2 = (EditText) view.findViewById(R.id.IP2);
        this.ip3 = (EditText) view.findViewById(R.id.IP3);
        this.ip4 = (EditText) view.findViewById(R.id.IP4);

        this.streamingPort = (EditText) view.findViewById(R.id.streaming_port);
        this.commandPort = (EditText) view.findViewById(R.id.command_port);
        this.webPort = (EditText) view.findViewById(R.id.web_port);

        this.username = (EditText) view.findViewById(R.id.username);
        this.password = (EditText) view.findViewById(R.id.password);

        this.saveButton = (FloatingActionButton) view.findViewById(R.id.save_button);

        this.saveButton.setOnClickListener(saveListner);
    }

    private View.OnClickListener saveListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            /*
            //Set shared preferences
            setting.setIp1(ip1.getText().toString());
            setting.setIp2(ip2.getText().toString());
            setting.setIp3(ip3.getText().toString());
            setting.setIp4(ip4.getText().toString());
            setting.setIpAddress(setting.getIp1() + "." + setting.getIp2() + "." + setting.getIp3() + "." + setting.getIp4());
            setting.setStreamingPort(streamingPort.getText().toString());
            setting.setCommandPort(commandPort.getText().toString());
            setting.setWebServerPort(wePort.getText().toString());
            setting.setUsername(username.getText().toString());
            setting.setPassword(password.getText().toString());

            SettingManager settingManager = new SettingManager(getContext());
            settingManager.setConnectionSetting(setting);
            */

            /*
             *  Check new values
             */
            boolean ipError = false;
            boolean portError = false;
            boolean credentialsError = false;

            //chek ip1
            if(errorIP(ip1)) ipError = true;

            //check ip2
            if(errorIP(ip2)) ipError = true;

            //check ip3
            if(errorIP(ip3)) ipError = true;

            //check ip4
            if(errorIP(ip4)) ipError = true;

            //check stream port
            if(errorPort(streamingPort)) portError = true;

            //check command port
            if(errorPort(commandPort)) portError = true;

            //check web port
            if(errorPort(webPort)) portError = true;

            //check credentials
            if(errorCredentials(username, password)) credentialsError = true;

            if(ipError){
                Snackbar.make(view, "ERROR in Ip Address values, settings not saved!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }else{
               if(portError){
                   Snackbar.make(view, "ERROR in Ports values, settings not saved!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
               }else{
                   if(credentialsError){
                       Snackbar.make(view, "ERROR in Credentials values, settings not saved!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                   }else{
                       // Get Editor from SharedPreferencies
                       SharedPreferences.Editor editor = sharedPreferences.edit();

                       editor.putString(PreferecesKeys.IP1, ip1.getText().toString());
                       editor.putString(PreferecesKeys.IP2, ip2.getText().toString());
                       editor.putString(PreferecesKeys.IP3, ip3.getText().toString());
                       editor.putString(PreferecesKeys.IP4, ip4.getText().toString());
                       editor.putString(PreferecesKeys.IP_ADDR, ip1.getText().toString()+"."+
                               ip2.getText().toString()+"."+
                               ip3.getText().toString()+"."+
                               ip4.getText().toString());
                       editor.putString(PreferecesKeys.STREAM_PORT, streamingPort.getText().toString());
                       editor.putString(PreferecesKeys.CMD_PORT, commandPort.getText().toString());
                       editor.putString(PreferecesKeys.WEB_PORT, webPort.getText().toString());
                       editor.putString(PreferecesKeys.USR, username.getText().toString());
                       editor.putString(PreferecesKeys.PSW, password.getText().toString());
                       // editor.putString(PreferecesKeys.NEW_USR, setting.getNewUsername().toString());
                       // editor.putString(PreferecesKeys.NEW_PSW, setting.getNewPassword().toString());

                       editor.commit();
                       // spinner.setVisibility(View.GONE);
                       Snackbar.make(view, "Settings Saved!", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                   }
               }
            }
        }
    };

    private boolean errorIP(EditText ip){

        boolean error = false;

        String ipString = ip.getText().toString();
        int ipInt;
        if(ipString.equalsIgnoreCase(null) || ipString.equalsIgnoreCase("")){
            error = true;
        }else{
            try{
                ipInt  = Integer.parseInt(ipString);
                if(ipInt < ConnectionParameterLimit.IP_MIN_VALUE || ipInt > ConnectionParameterLimit.IP_MAX_VALUE){
                    error = true;
                }
            }catch (NumberFormatException exception){
                error = true;
            }

        }
        return error;
    }

    private boolean errorPort(EditText port){

        boolean error = false;

        String portString = port.getText().toString();
        int portInt;
        if(portString.equalsIgnoreCase(null) || portString.equalsIgnoreCase("")){
            error = true;
        }else{
            try{
                portInt = Integer.parseInt(portString);
                if(portInt < ConnectionParameterLimit.PORT_MIN_VALUE || portInt > ConnectionParameterLimit.PORT_MAX_VALUE){
                    error = true;
                }
            }catch(NumberFormatException exception){
                error = true;
            }

        }
        return error;
    }

    private boolean errorCredentials(EditText usernameET, EditText passwordET){

        boolean error = false;
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();

        if(username.equalsIgnoreCase(null) || username.equalsIgnoreCase("") || password.equalsIgnoreCase(null) || password.equalsIgnoreCase("")){
            error = true;
        }

        return error;
    }

    private void setPreviousValue(){

        this.ip1.setText(this.sharedPreferences.getString(PreferecesKeys.IP1,""));
        this.ip2.setText(this.sharedPreferences.getString(PreferecesKeys.IP2,""));
        this.ip3.setText(this.sharedPreferences.getString(PreferecesKeys.IP3,""));
        this.ip4.setText(this.sharedPreferences.getString(PreferecesKeys.IP4,""));

        this.streamingPort.setText(this.sharedPreferences.getString(PreferecesKeys.STREAM_PORT,""));
        this.commandPort.setText(this.sharedPreferences.getString(PreferecesKeys.CMD_PORT,""));
        this.webPort.setText(this.sharedPreferences.getString(PreferecesKeys.WEB_PORT,""));

        this.username.setText(this.sharedPreferences.getString(PreferecesKeys.USR,""));
        this.password.setText(this.sharedPreferences.getString(PreferecesKeys.PSW,""));

    }

    public EditText getIp1() {
        return ip1;
    }

    public EditText getIp2() {
        return ip2;
    }

    public EditText getIp3() {
        return ip3;
    }

    public EditText getIp4() {
        return ip4;
    }

    public EditText getStreamingPort() {
        return streamingPort;
    }

    public EditText getCommandPort() {
        return commandPort;
    }

    public EditText getWebPort() {
        return webPort;
    }

    public EditText getUsername() {
        return username;
    }

    public EditText getPassword() {
        return password;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

}
