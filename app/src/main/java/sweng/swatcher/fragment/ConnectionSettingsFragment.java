package sweng.swatcher.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import sweng.swatcher.R;
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
    NumberPicker ip1;
    NumberPicker ip2;
    NumberPicker ip3;
    NumberPicker ip4;
    NumberPicker stream_port;
    EditText username, new_username;
    EditText password, new_password;
    Button change;
    Button save;
    String ipAddress;


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
        handleConnectionSettings(view);

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
        ip1 = (NumberPicker) view.findViewById(R.id.IP1);
        ip2 = (NumberPicker) view.findViewById(R.id.IP2);
        ip3 = (NumberPicker) view.findViewById(R.id.IP3);
        ip4 = (NumberPicker) view.findViewById(R.id.IP4);

        ip1.setMinValue(0);
        ip1.setMaxValue(255);

        ip2.setMinValue(0);
        ip2.setMaxValue(255);

        ip3.setMinValue(0);
        ip3.setMaxValue(255);

        ip4.setMinValue(0);
        ip4.setMaxValue(255);


        stream_port = (NumberPicker) view.findViewById(R.id.streamingPort);
        stream_port.setMinValue(0);
        stream_port.setMaxValue(99999);


        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);

        new_username = (EditText) view.findViewById(R.id.new_username);
        new_password = (EditText) view.findViewById(R.id.new_password);

        change = (Button) view.findViewById(R.id.change);
        save = (Button) view.findViewById(R.id.save);


        save.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        ipAddress = Integer.toString(ip1.getValue())+"."+Integer.toString(ip2.getValue())+"."+Integer.toString(ip3.getValue())+"."+Integer.toString(ip4.getValue());
                        //Set shared preferences
                        Setting stg = new Setting();
                        stg.setIpAddress(ipAddress);
                        stg.setStreamingPort(Integer.toString(stream_port.getValue()));
                        stg.setUsername(username.getText().toString());
                        stg.setPassword(password.getText().toString());

                        //CAMBIARE
                        stg.setCommandPort("4321");
                        stg.setWebServerPort("80");

                        stg.setNewPassword("");
                        stg.setNewUsername("");

                        SettingManager stgMng = new SettingManager(getContext());
                        stgMng.setSetting(stg);
                        Snackbar.make(view, "Settings Saved", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                    }
                });

        change.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        //Set shared preferences
                    }
                });






    }
}
