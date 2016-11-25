package sweng.swatcher.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import sweng.swatcher.R;
import sweng.swatcher.model.Setting;
import sweng.swatcher.util.PreferecesKeys;
import sweng.swatcher.command.StreamCommand;
import sweng.swatcher.model.Authorization;
import sweng.swatcher.command.MediaCommand;
import sweng.swatcher.request.MovieRequest;
import sweng.swatcher.request.SnapshotRequest;
import sweng.swatcher.request.StreamRequest;
import sweng.swatcher.util.MediaButtonSet;
import sweng.swatcher.util.SettingManager;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String ip;
    private String port;

    private WebView webViewStreaming;
    private OnFragmentInteractionListener interactionListener;

    private MediaButtonSet mediaButtonSet;

    private FloatingActionButton playButton;
    private FloatingActionButton stopButton;
    private FloatingActionButton snapshotButton;

    private StreamRequest streaming;
    private SnapshotRequest snapshot;
    private MovieRequest movie;

    private StreamCommand streamCommand;

    String res;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //Chiamata alla funzione di gestione della home.
        WebView wv = (WebView) view.findViewById(R.id.webview_streaming);
        wv.loadUrl("file:///android_asset/streamingStop.html");
        handleHome(view);
        return view;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (this.interactionListener != null) {
            this.interactionListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.interactionListener = (OnFragmentInteractionListener) context;
        }
        else {
            Log.i("on Attach:","Context is not instance of OnFragmentInteractionListener"
                    + "in onAttach Method of HomeFragment.");

            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.interactionListener = null;
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

    //Funzione di gestione della home
    private void handleHome(View view){

       // SettingManager sm = new SettingManager(getContext());
       // Setting setting = sm.getSetting();

       // SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        //Authorization auth = new Authorization(sp.getString(PreferecesKeys.USR,""),sp.getString(PreferecesKeys.PSW,""),"Basic");

        this.webViewStreaming = (WebView)view.findViewById(R.id.webview_streaming);
      //  this.streaming = new StreamRequest(sp.getString(PreferecesKeys.IP_ADDR,""),sp.getString(PreferecesKeys.STREAM_PORT,""),auth);
       // this.snapshot = new SnapshotRequest(sp.getString(PreferecesKeys.IP_ADDR,""),sp.getString(PreferecesKeys.CMD_PORT,""),auth,0,view);
       // this.movie = new MovieRequest(sp.getString(PreferecesKeys.IP_ADDR,""),sp.getString(PreferecesKeys.CMD_PORT,""),auth,0,view);

        this.playButton = (FloatingActionButton) view.findViewById(R.id.play);
        this.stopButton = (FloatingActionButton) view.findViewById(R.id.stop);
        this.snapshotButton = (FloatingActionButton) view.findViewById(R.id.snapshot);

        this.playButton.setOnClickListener(playListner);
        this.stopButton.setOnClickListener(stopListner);
        this.snapshotButton.setOnClickListener(snapshotListner);

        this.mediaButtonSet = new MediaButtonSet(view);

        this.mediaButtonSet.addToStopList(this.playButton);
        this.mediaButtonSet.addToPlayList(this.stopButton);
        this.mediaButtonSet.addToPlayList(this.snapshotButton);

    }

    private View.OnClickListener playListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //webViewStreaming.clearHistory();
            //webViewStreaming.cancelPendingInputEvents();

            boolean error = false;

            SettingManager settingManager = new SettingManager(getContext());
            Setting setting = settingManager.getSetting();

            //Check if connection parameters are empty
            if (errorParameterEmpty(setting.getIp1())) error = true;
            if (errorParameterEmpty(setting.getIp2())) error = true;
            if (errorParameterEmpty(setting.getIp3())) error = true;
            if (errorParameterEmpty(setting.getIp4())) error = true;

            if (errorParameterEmpty(setting.getStreamingPort())) error = true;
            if (errorParameterEmpty(setting.getCommandPort())) error = true;
            if (errorParameterEmpty(setting.getWebServerPort())) error = true;

            if (errorParameterEmpty(setting.getUsername())) error = true;
            if (errorParameterEmpty(setting.getPassword())) error = true;

            if (error) {
                Snackbar.make(view, "Connection fields empty!\nSet connection parameters", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            } else {
                Authorization auth = new Authorization(setting.getUsername(),setting.getPassword(),"Basic");
                Log.i("HOME_FRAGMENT",auth.getUsername()+":"+auth.getPassword()+":"+setting.getStreamingPort());

                streaming = new StreamRequest(setting.getIpAddress(),setting.getStreamingPort(),auth);
                streamCommand = new StreamCommand(webViewStreaming, view, streaming, mediaButtonSet);
                streamCommand.execute();
            }
        }
    };

    private View.OnClickListener stopListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //webViewStreaming.clearHistory();
            //webViewStreaming.cancelPendingInputEvents();
            streamCommand.hideMediaButton();
        }
    };

    private View.OnClickListener snapshotListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SettingManager settingManager = new SettingManager(getContext());
            Setting setting = settingManager.getSetting();
            Authorization auth = new Authorization(setting.getUsername(),setting.getPassword(),"Basic");
            snapshot = new SnapshotRequest(setting.getIpAddress(),setting.getCommandPort(),auth,0,view);
            snapshot.setResponse(null);
            MediaCommand cmd = new MediaCommand(getContext(),snapshot,view);
            cmd.execute();
        }
    };


    private boolean errorParameterEmpty(String parameter){
        boolean error = false;
        if(parameter.equalsIgnoreCase(null) || parameter.equalsIgnoreCase("")) error = true;
        return error;
    }

}


