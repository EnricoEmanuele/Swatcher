package sweng.swatcher.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.SharedPreferencesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import sweng.swatcher.R;
import sweng.swatcher.util.PreferecesKeys;
import sweng.swatcher.util.SettingManager;
import sweng.swatcher.model.Setting;


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

    /*************************************/
    Setting stg;
    EditText ip1;
    EditText ip2;
    EditText ip3;
    EditText ip4;
    EditText streaming_port;
    EditText command_port;
    EditText web_port;
    EditText username;
    EditText password;
    FloatingActionButton save_button;


    SharedPreferences sp;
    SharedPreferences.Editor editor;



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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connection_settings, container, false);
        //Handle page
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sp.edit();

        handleConnectionSettings(view);
        setPreviousValue();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        ip1 = (EditText) view.findViewById(R.id.IP1);
        ip2 = (EditText) view.findViewById(R.id.IP2);
        ip3 = (EditText) view.findViewById(R.id.IP3);
        ip4 = (EditText) view.findViewById(R.id.IP4);

        streaming_port = (EditText) view.findViewById(R.id.streaming_port);
        command_port = (EditText) view.findViewById(R.id.command_port);
        web_port = (EditText) view.findViewById(R.id.web_port);

        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);

        save_button = (FloatingActionButton) view.findViewById(R.id.save_button);

        save_button.setOnClickListener(saveListner);
    }

    private View.OnClickListener saveListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
/*

            //Set shared preferences
            stg.setIp1(ip1.getText().toString());
            stg.setIp2(ip2.getText().toString());
            stg.setIp3(ip3.getText().toString());
            stg.setIp4(ip4.getText().toString());
            stg.setIpAddress(stg.getIp1() + "." + stg.getIp2() + "." + stg.getIp3() + "." + stg.getIp4());
            stg.setStreamingPort(streaming_port.getText().toString());
            stg.setCommandPort(command_port.getText().toString());
            stg.setWebServerPort(web_port.getText().toString());
            stg.setUsername(username.getText().toString());
            stg.setPassword(password.getText().toString());

            SettingManager stgMng = new SettingManager(getContext());
            stgMng.setConnectionSetting(stg);
*/
            editor.putString(PreferecesKeys.IP1, ip1.getText().toString());
            editor.putString(PreferecesKeys.IP2, ip2.getText().toString());
            editor.putString(PreferecesKeys.IP3, ip3.getText().toString());
            editor.putString(PreferecesKeys.IP4, ip4.getText().toString());
            editor.putString(PreferecesKeys.IP_ADDR, ip1.getText().toString()+"."+
                                                     ip2.getText().toString()+"."+
                                                     ip3.getText().toString()+"."+
                                                     ip4.getText().toString());
            editor.putString(PreferecesKeys.STREAM_PORT, streaming_port.getText().toString());
            editor.putString(PreferecesKeys.CMD_PORT, command_port.getText().toString());
            editor.putString(PreferecesKeys.WEB_PORT, web_port.getText().toString());
            editor.putString(PreferecesKeys.USR, username.getText().toString());
            editor.putString(PreferecesKeys.PSW, password.getText().toString());
//        editor.putString(PreferecesKeys.NEW_USR, stg.getNewUsername().toString());
            //       editor.putString(PreferecesKeys.NEW_PSW, stg.getNewPassword().toString());
            editor.commit();
           // spinner.setVisibility(View.GONE);
            Snackbar.make(view, "Settings Saved", Snackbar.LENGTH_LONG).setAction("Action", null).show();

        }
    };

    private void setPreviousValue(){
        ip1.setText(sp.getString(PreferecesKeys.IP1,""));
        ip2.setText(sp.getString(PreferecesKeys.IP2,""));
        ip3.setText(sp.getString(PreferecesKeys.IP3,""));
        ip4.setText(sp.getString(PreferecesKeys.IP4,""));
        streaming_port.setText(sp.getString(PreferecesKeys.STREAM_PORT,""));
        command_port.setText(sp.getString(PreferecesKeys.CMD_PORT,""));
        web_port.setText(sp.getString(PreferecesKeys.WEB_PORT,""));
        username.setText(sp.getString(PreferecesKeys.USR,""));
        password.setText(sp.getString(PreferecesKeys.PSW,""));
    }
}
