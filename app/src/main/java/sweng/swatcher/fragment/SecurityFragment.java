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
import android.widget.EditText;
import android.widget.ProgressBar;

import sweng.swatcher.R;
import sweng.swatcher.command.SecurityCommand;
import sweng.swatcher.model.Authorization;
import sweng.swatcher.request.SecurityRequest;
import sweng.swatcher.util.PreferecesKeys;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SecurityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SecurityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecurityFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener interactionListener;

    private FloatingActionButton changeButton;

    private EditText newUsernameText;
    private EditText newPasswordText;

    private ProgressBar spinner;

    private static final String EMPTY_STRING = "";
    private static final String CREDENTIAL_ERROR_INPUT_MESSAGE = "Username or Password empty!";

    public SecurityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecurityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecurityFragment newInstance(String param1, String param2) {
        SecurityFragment fragment = new SecurityFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_security, container, false);

        changeButton = (FloatingActionButton) view.findViewById(R.id.change_button);
        this.newUsernameText = (EditText) view.findViewById(R.id.new_username);
        this.newPasswordText = (EditText) view.findViewById(R.id.new_password);
        this.spinner = (ProgressBar)view.findViewById(R.id.progressBar);
        this.spinner.setVisibility(View.GONE);

        changeButton.setOnClickListener(changeListner);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (interactionListener != null) {
            interactionListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            interactionListener = (OnFragmentInteractionListener) context;
        }
        else {
            Log.i("on Attach:","Context is not instance of OnFragmentInteractionListener"
                    + "in onAttach Method of SecurityFragment");

            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private View.OnClickListener changeListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            spinner.setVisibility(View.VISIBLE);
            String new_username = newUsernameText.getText().toString();
            String new_password = newPasswordText.getText().toString();

            if(new_username.equalsIgnoreCase(EMPTY_STRING) || new_password.equalsIgnoreCase(EMPTY_STRING)){
                Snackbar.make(view, CREDENTIAL_ERROR_INPUT_MESSAGE, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
            else{
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());

                Authorization auth = new Authorization(sp.getString(PreferecesKeys.USR,""),sp.getString(PreferecesKeys.PSW,""),"Basic");
                SecurityRequest security = new SecurityRequest(sp.getString(PreferecesKeys.IP_ADDR,""),sp.getString(PreferecesKeys.WEB_PORT,""),auth);
                SecurityCommand sc = new SecurityCommand(getContext(), security, view, spinner, new_username, new_password);
                sc.execute();
            }
        }
    };
}
