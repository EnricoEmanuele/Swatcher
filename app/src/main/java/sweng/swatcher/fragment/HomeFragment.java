package sweng.swatcher.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import sweng.swatcher.R;
import sweng.swatcher.util.SettingManager;
import sweng.swatcher.command.StreamCommand;
import sweng.swatcher.model.Authorization;
import sweng.swatcher.command.MediaCommand;
import sweng.swatcher.model.Setting;
import sweng.swatcher.request.MovieRequest;
import sweng.swatcher.request.SnapshotRequest;
import sweng.swatcher.request.StreamRequest;
import sweng.swatcher.util.MediaButtonSet;


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

    private OnFragmentInteractionListener mListener;

    private WebView webview_streaming;
    private String ip, port;
    private FloatingActionButton play_button;
    private FloatingActionButton stop_button;
    private FloatingActionButton snapshot_button;
    private FloatingActionButton record_button;

    private StreamRequest streaming;
    private SnapshotRequest snapshot;
    private MovieRequest movie;
    private MediaButtonSet mediaButtonSet;

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
        handleHome(view);
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

    //Funzione di gestione della home
    private void handleHome(View view){

        SettingManager sm = new SettingManager(getContext());
        Setting setting = sm.getSetting();

        webview_streaming = (WebView)view.findViewById(R.id.webview_streaming);
       // webview_streaming.setWebViewClient(new Streaming());
        streaming = new StreamRequest(setting.getIpAddress(),setting.getStreamingPort(),new Authorization(setting.getUsername(),setting.getPassword(),"Basic"));
        snapshot = new SnapshotRequest(setting.getIpAddress(),"4321",new Authorization(setting.getUsername(),setting.getPassword(),"Basic"),0,view);
        movie = new MovieRequest(setting.getIpAddress(),"4321",new Authorization(setting.getUsername(),setting.getPassword(),"Basic"),0,view);

        play_button = (FloatingActionButton) view.findViewById(R.id.play);
        stop_button = (FloatingActionButton) view.findViewById(R.id.stop);
        snapshot_button = (FloatingActionButton) view.findViewById(R.id.snapshot);
        record_button = (FloatingActionButton) view.findViewById(R.id.record);

        play_button.setOnClickListener(playListner);
        stop_button.setOnClickListener(stopListner);
        snapshot_button.setOnClickListener(snapshotListner);
        record_button.setOnClickListener(recordListner);

        mediaButtonSet = new MediaButtonSet(view);

        mediaButtonSet.addToStopList(play_button);
        mediaButtonSet.addToPlayList(stop_button);
        mediaButtonSet.addToPlayList(snapshot_button);
        mediaButtonSet.addToPlayList(record_button);


    }

    private View.OnClickListener playListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            streamCommand = new StreamCommand(webview_streaming, view, streaming, mediaButtonSet);
            streamCommand.execute();

        }
    };

    private View.OnClickListener stopListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            streamCommand.hideMediaButton();

        }
    };

    private View.OnClickListener snapshotListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            snapshot.setResponse(null);
            MediaCommand cmd = new MediaCommand(getContext(),snapshot,view);
            cmd.execute();
            /*
            ResponseControl control = new ResponseControl(snapshot,getView());
            control.checkResponse();
            */
        }
    };

    private View.OnClickListener recordListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MediaCommand cmd = new MediaCommand(getContext(),movie, view);
            cmd.execute();
            /*
            ResponseControl control = new ResponseControl(movie, getView());
            control.checkResponse();
            */
        }
    };

    private void displayMediaButton(){
        play_button.setVisibility(View.GONE);
        stop_button.setVisibility(View.VISIBLE);
        snapshot_button.setVisibility(View.VISIBLE);
        record_button.setVisibility(View.VISIBLE);
    }

    private void hideMediaButton(){
        play_button.setVisibility(View.VISIBLE);
        stop_button.setVisibility(View.GONE);
        snapshot_button.setVisibility(View.GONE);
        record_button.setVisibility(View.GONE);
    }


}


