package sweng.swatcher;

import android.content.Context;
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
import android.webkit.WebView;
import android.webkit.WebViewClient;


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
    private String url;
    private FloatingActionButton play_button;
    private FloatingActionButton stop_button;
    private FloatingActionButton snapshot_button;
    private FloatingActionButton record_button;

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
        class Streaming extends WebViewClient {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        }
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        url = settings.getString(Constants.PREFS_KEY_URL, null);

        webview_streaming = (WebView)view.findViewById(R.id.webview_streaming);
        webview_streaming.setWebViewClient(new Streaming());
        play_button = (FloatingActionButton) view.findViewById(R.id.play);
        stop_button = (FloatingActionButton) view.findViewById(R.id.stop);
        snapshot_button = (FloatingActionButton) view.findViewById(R.id.snapshot);
        record_button = (FloatingActionButton) view.findViewById(R.id.record);

        play_button.setOnClickListener(playListner);
        stop_button.setOnClickListener(stopListner);
        snapshot_button.setOnClickListener(snapshotListner);
        record_button.setOnClickListener(recordListner);

    }

    private View.OnClickListener playListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            webview_streaming.getSettings().setLoadsImagesAutomatically(true);
            webview_streaming.getSettings().setJavaScriptEnabled(true);
            webview_streaming.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webview_streaming.loadUrl(url);
            Snackbar.make(view, "Successful Connected", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            displayMediaButton();
        }
    };

    private View.OnClickListener stopListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            hideMediaButton();
            Snackbar.make(view, "Stop streaming video", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    };

    private View.OnClickListener snapshotListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Snackbar.make(view, "Snapshot taken", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    };

    private View.OnClickListener recordListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Snackbar.make(view, "Start record video", Snackbar.LENGTH_LONG).setAction("Action", null).show();
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
