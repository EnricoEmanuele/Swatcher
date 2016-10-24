package sweng.swatcher.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import sweng.swatcher.R;
import sweng.swatcher.util.PreferecesKeys;
import sweng.swatcher.command.GalleryCommand;
import sweng.swatcher.model.Authorization;
import sweng.swatcher.model.Media;
import sweng.swatcher.request.GalleryRequest;

import static java.lang.Thread.sleep;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GalleryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GalleryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GalleryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener interactionListener;

    private ArrayAdapter<Media> mediaAdapter;
    private ListView listView;
    private FloatingActionButton galleryButton;

    public GalleryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GalleryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GalleryFragment newInstance(String param1, String param2) {
        GalleryFragment fragment = new GalleryFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        this.listView = (ListView) view.findViewById(R.id.gallery_list_view);
        this.galleryButton = (FloatingActionButton)view.findViewById(R.id.gallery_button);
        this.galleryButton.setOnClickListener(this.galleryListner);

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
                    + "in onAttach Method of GalleryFragment.");

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


    private View.OnClickListener galleryListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
            Authorization auth = new Authorization(sp.getString(PreferecesKeys.USR,""),sp.getString(PreferecesKeys.PSW,""),"Basic");

            GalleryRequest gallery = new GalleryRequest(sp.getString(PreferecesKeys.IP_ADDR,""),sp.getString(PreferecesKeys.WEB_PORT,""),auth);
            GalleryCommand gc = new GalleryCommand(getContext(), gallery, listView);

            gc.execute();

        }
    };

    public ListView getListView() {
        return listView;
    }

    public ArrayAdapter<Media> getMediaAdapter() {
        return mediaAdapter;
    }

    public FloatingActionButton getGalleryButton() {
        return galleryButton;
    }

    public View.OnClickListener getGalleryListner() {
        return galleryListner;
    }

    public OnFragmentInteractionListener getInteractionListener() {
        return interactionListener;
    }
}
