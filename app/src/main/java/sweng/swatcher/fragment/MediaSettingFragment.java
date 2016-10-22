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
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import sweng.swatcher.R;
import sweng.swatcher.command.MediaSettingReadCommand;
import sweng.swatcher.command.MediaSettingWriteCommand;
import sweng.swatcher.model.Authorization;
import sweng.swatcher.model.Setting;
import sweng.swatcher.request.HttpRequest;
import sweng.swatcher.request.ReadMediaSettingRequest;
import sweng.swatcher.request.SetMediaSettingRequest;
import sweng.swatcher.util.SettingManager;
import static sweng.swatcher.util.ParametersKeys.MAX_MOVIE_TIME;
import static sweng.swatcher.util.ParametersKeys.OUTPUT_PICTURES;
import static sweng.swatcher.util.ParametersKeys.PICTURE_TYPE;
import static sweng.swatcher.util.ParametersKeys.QUALITY_PARAMETER;
import static sweng.swatcher.util.ParametersKeys.THRESHOLD;
import static sweng.swatcher.util.ParametersKeys.SNAPSHOT_INTERVAL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MediaSettingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MediaSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MediaSettingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private View msView;
    private FloatingActionButton updateButton;
    private FloatingActionButton saveButton;
    private Switch snapshotSwitch;
    private Switch movieSwitch;
    private MediaSettingReadCommand mediaSettingReadCommand;
    private MediaSettingWriteCommand mediaSettingWriteCommand;
    private SettingManager sm;
    private Setting setting;

    public MediaSettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MediaSettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MediaSettingFragment newInstance(String param1, String param2) {
        MediaSettingFragment fragment = new MediaSettingFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Settings
        sm = new SettingManager(getContext());
        setting = sm.getSetting();

        //View informations
        msView = inflater.inflate(R.layout.fragment_media_setting, container, false);
        updateButton = (FloatingActionButton) msView.findViewById(R.id.update_ms_button);
        saveButton = (FloatingActionButton) msView.findViewById(R.id.save_ms_button);
        snapshotSwitch = (Switch) msView.findViewById(R.id.snapshot_switch);
        movieSwitch = (Switch) msView.findViewById(R.id.movie_switch);
        updateButton.setOnClickListener(updateListener);
        saveButton.setOnClickListener(saveListener);
        movieSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked)
                    movieSwitch.setText("Enabled");
                else
                    movieSwitch.setText("Disabled");
            }
        });
        snapshotSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked)
                    snapshotSwitch.setText("Enabled");
                else
                    snapshotSwitch.setText("Disabled");
            }
        });

        //Disable save button. First read settings from Server
        saveButton.setEnabled(false);
        saveButton.setClickable(false);

        //Set spinner entries
        Spinner spinner = (Spinner) msView.findViewById(R.id.picture_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.picture_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return msView;
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /*
     * Read media parameters from Server
     */
    private View.OnClickListener updateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //read image quality parameter
            HttpRequest qualityImage = new ReadMediaSettingRequest(setting.getIpAddress(),
                    setting.getCommandPort(),
                    new Authorization(setting.getUsername(),setting.getPassword(),"Basic"), 0, QUALITY_PARAMETER);
            mediaSettingReadCommand = new MediaSettingReadCommand(getContext(), qualityImage, msView);
            mediaSettingReadCommand.execute();

            //read picture type parameter
            HttpRequest pictureType = new ReadMediaSettingRequest(setting.getIpAddress(),
                    setting.getCommandPort(),
                    new Authorization(setting.getUsername(),setting.getPassword(),"Basic"), 0, PICTURE_TYPE);
            mediaSettingReadCommand = new MediaSettingReadCommand(getContext(), pictureType, msView);
            mediaSettingReadCommand.execute();

            //read max movie time
            HttpRequest maxMovieTime = new ReadMediaSettingRequest(setting.getIpAddress(),
                    setting.getCommandPort(),
                    new Authorization(setting.getUsername(),setting.getPassword(),"Basic"), 0, MAX_MOVIE_TIME);
            mediaSettingReadCommand = new MediaSettingReadCommand(getContext(), maxMovieTime, msView);
            mediaSettingReadCommand.execute();

            //read snapshot on detection parameter
            HttpRequest snapshotOnDetection = new ReadMediaSettingRequest(setting.getIpAddress(),
                    setting.getCommandPort(),
                    new Authorization(setting.getUsername(),setting.getPassword(),"Basic"), 0, OUTPUT_PICTURES);
            mediaSettingReadCommand = new MediaSettingReadCommand(getContext(), snapshotOnDetection, msView);
            mediaSettingReadCommand.execute();

            //read threshold parameter
            HttpRequest threshold = new ReadMediaSettingRequest(setting.getIpAddress(),
                    setting.getCommandPort(),
                    new Authorization(setting.getUsername(),setting.getPassword(),"Basic"), 0, THRESHOLD);
            mediaSettingReadCommand = new MediaSettingReadCommand(getContext(), threshold, msView);
            mediaSettingReadCommand.execute();

            //read continuous snapshot interval parameter
            HttpRequest snapshotInterval = new ReadMediaSettingRequest(setting.getIpAddress(),
                    setting.getCommandPort(),
                    new Authorization(setting.getUsername(),setting.getPassword(),"Basic"), 0, SNAPSHOT_INTERVAL);
            mediaSettingReadCommand = new MediaSettingReadCommand(getContext(), snapshotInterval, msView);
            mediaSettingReadCommand.execute();

        }
    };

    /*
     * Update media parameters on Server
     */
    private View.OnClickListener saveListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            boolean settingError = false;

            //View resources
            EditText qualityImageEditText = (EditText) msView.findViewById(R.id.quality_image);
            Spinner pictureTypeSpinenr = (Spinner) msView.findViewById(R.id.picture_type);
            EditText maxMovieTimeEditText = (EditText) msView.findViewById(R.id.max_movie_time);
            Switch snapshotSwitch = (Switch) msView.findViewById(R.id.snapshot_switch);
            EditText thresholdEditText = (EditText) msView.findViewById(R.id.threshold);
            EditText snapshotIntervalEditText = (EditText) msView.findViewById(R.id.snapshot_interval);

            //set image quality parameter
            HttpRequest quality;
            String qualityValue = qualityImageEditText.getText().toString();
            if(qualityValue!=null){
                quality = new SetMediaSettingRequest(setting.getIpAddress(), setting.getCommandPort(),
                        new Authorization(setting.getUsername(),setting.getPassword(),"Basic"),0,QUALITY_PARAMETER,qualityValue);
                mediaSettingWriteCommand = new MediaSettingWriteCommand(getContext(),quality,msView);
                mediaSettingWriteCommand.execute();
            }
            else {settingError = true;}

            //set picture type parameter
            String picTypeValue = pictureTypeSpinenr.getSelectedItem().toString();
            HttpRequest pictureType;
            if(picTypeValue!=null){
                pictureType = new SetMediaSettingRequest(setting.getIpAddress(), setting.getCommandPort(),
                        new Authorization(setting.getUsername(),setting.getPassword(),"Basic"),0,PICTURE_TYPE,picTypeValue);
                mediaSettingWriteCommand = new MediaSettingWriteCommand(getContext(),pictureType,msView);
                mediaSettingWriteCommand.execute();
            }
            else {settingError = true;}

            //set max movie time parameter
            String maxMovieTimeValue = maxMovieTimeEditText.getText().toString();
            HttpRequest maxMovieTime;
            if(maxMovieTimeValue!=null){
                maxMovieTime = new SetMediaSettingRequest(setting.getIpAddress(), setting.getCommandPort(),
                        new Authorization(setting.getUsername(),setting.getPassword(),"Basic"),0,MAX_MOVIE_TIME,maxMovieTimeValue);
                mediaSettingWriteCommand = new MediaSettingWriteCommand(getContext(),maxMovieTime,msView);
                mediaSettingWriteCommand.execute();
            }
            else {settingError = true;}

            //set output picture parameter
            boolean outputPicEnabled = snapshotSwitch.isChecked();
            String outPicValue;
            if(outputPicEnabled) {outPicValue = "on";}
            else {outPicValue = "off";}
            HttpRequest snapshotOnDetection = new SetMediaSettingRequest(setting.getIpAddress(), setting.getCommandPort(),
                    new Authorization(setting.getUsername(),setting.getPassword(),"Basic"),0,OUTPUT_PICTURES,outPicValue);
            mediaSettingWriteCommand = new MediaSettingWriteCommand(getContext(),snapshotOnDetection,msView);
            mediaSettingWriteCommand.execute();

            //set threshold parameter
            String thresholdValue = thresholdEditText.getText().toString();
            HttpRequest threshold;
            if(thresholdValue!=null){
                threshold = new SetMediaSettingRequest(setting.getIpAddress(), setting.getCommandPort(),
                        new Authorization(setting.getUsername(),setting.getPassword(),"Basic"),0,THRESHOLD,thresholdValue);
                mediaSettingWriteCommand = new MediaSettingWriteCommand(getContext(),threshold,msView);
                mediaSettingWriteCommand.execute();
            }
            else{settingError = true;}

            //set snapshot interval
            String snapIntervalValue = snapshotIntervalEditText.getText().toString();
            HttpRequest snapshotInterval;
            if(snapIntervalValue!=null){
                snapshotInterval = new SetMediaSettingRequest(setting.getIpAddress(), setting.getCommandPort(),
                        new Authorization(setting.getUsername(),setting.getPassword(),"Basic"),0,SNAPSHOT_INTERVAL,snapIntervalValue);
                mediaSettingWriteCommand = new MediaSettingWriteCommand(getContext(),snapshotInterval,msView);
                mediaSettingWriteCommand.execute();
            }
            else{settingError = true;}

            if(settingError){
                Snackbar.make(view, "Error writing setting on Server: ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }

        }
    };
}
