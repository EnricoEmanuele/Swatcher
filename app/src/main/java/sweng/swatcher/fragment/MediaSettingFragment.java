package sweng.swatcher.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import sweng.swatcher.command.RestartCommand;
import sweng.swatcher.model.Authorization;
import sweng.swatcher.model.Setting;
import sweng.swatcher.request.HttpRequest;
import sweng.swatcher.request.ReadMediaSettingRequest;
import sweng.swatcher.request.RestartRequest;
import sweng.swatcher.request.SetMediaSettingRequest;
import sweng.swatcher.request.WriteMediaSettingRequest;
import sweng.swatcher.util.SettingManager;
import static sweng.swatcher.util.ParametersKeys.MAX_MOVIE_TIME;
import static sweng.swatcher.util.ParametersKeys.OUTPUT_MOVIES;
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

    private static final String TEXT_ENABLED = "Enabled";
    private static final String TEXT_DISABLED = "Disabled";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Setting setting;
    private SettingManager settingManager;

    private OnFragmentInteractionListener interactionListener;
    private View mediaSettingView;

    private FloatingActionButton updateButton;
    private FloatingActionButton saveButton;

    private Switch snapshotSwitch;
    private Switch movieSwitch;

    private MediaSettingReadCommand mediaSettingReadCommand;
    private MediaSettingWriteCommand mediaSettingWriteCommand;
    private RestartCommand restartCommand;


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
        this.settingManager = new SettingManager(getContext());
        setting = this.settingManager.getSetting();

        //View informations
        this.mediaSettingView = inflater.inflate(R.layout.fragment_media_setting, container, false);
        this.updateButton = (FloatingActionButton) this.mediaSettingView.findViewById(R.id.update_ms_button);
        this.saveButton = (FloatingActionButton) this.mediaSettingView.findViewById(R.id.save_ms_button);
        this.snapshotSwitch = (Switch) this.mediaSettingView.findViewById(R.id.snapshot_switch);
        this.movieSwitch = (Switch) this.mediaSettingView.findViewById(R.id.movie_switch);
        this.updateButton.setOnClickListener(updateListener);
        this.saveButton.setOnClickListener(saveListener);
        this.movieSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked) {
                    movieSwitch.setText(TEXT_ENABLED);
                }
                else {
                    movieSwitch.setText(TEXT_DISABLED);
                }
            }
        });
        this.snapshotSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked) {
                    snapshotSwitch.setText(TEXT_ENABLED);
                }
                else {
                    snapshotSwitch.setText(TEXT_DISABLED);
                }
            }
        });

        //Disable save button. First read settings from Server
        this.saveButton.setEnabled(false);
        this.saveButton.setClickable(false);

        //Set spinner entries
        Spinner spinner = (Spinner) this.mediaSettingView.findViewById(R.id.picture_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.picture_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return this.mediaSettingView;
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
                    + "in onAttach Method of MediaSettingFragment.");

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
            mediaSettingReadCommand = new MediaSettingReadCommand(getContext(), qualityImage, mediaSettingView);
            mediaSettingReadCommand.execute();

            //read picture type parameter
            HttpRequest pictureType = new ReadMediaSettingRequest(setting.getIpAddress(),
                    setting.getCommandPort(),
                    new Authorization(setting.getUsername(),setting.getPassword(),"Basic"), 0, PICTURE_TYPE);
            mediaSettingReadCommand = new MediaSettingReadCommand(getContext(), pictureType, mediaSettingView);
            mediaSettingReadCommand.execute();

            //read movie on detection parameter
            HttpRequest movieOnDetection = new ReadMediaSettingRequest(setting.getIpAddress(),
                    setting.getCommandPort(),
                    new Authorization(setting.getUsername(),setting.getPassword(),"Basic"), 0, OUTPUT_MOVIES);
            mediaSettingReadCommand = new MediaSettingReadCommand(getContext(), movieOnDetection, mediaSettingView);
            mediaSettingReadCommand.execute();

            //read max movie time
            HttpRequest maxMovieTime = new ReadMediaSettingRequest(setting.getIpAddress(),
                    setting.getCommandPort(),
                    new Authorization(setting.getUsername(),setting.getPassword(),"Basic"), 0, MAX_MOVIE_TIME);
            mediaSettingReadCommand = new MediaSettingReadCommand(getContext(), maxMovieTime, mediaSettingView);
            mediaSettingReadCommand.execute();

            //read snapshot on detection parameter
            HttpRequest snapshotOnDetection = new ReadMediaSettingRequest(setting.getIpAddress(),
                    setting.getCommandPort(),
                    new Authorization(setting.getUsername(),setting.getPassword(),"Basic"), 0, OUTPUT_PICTURES);
            mediaSettingReadCommand = new MediaSettingReadCommand(getContext(), snapshotOnDetection, mediaSettingView);
            mediaSettingReadCommand.execute();

            //read threshold parameter
            HttpRequest threshold = new ReadMediaSettingRequest(setting.getIpAddress(),
                    setting.getCommandPort(),
                    new Authorization(setting.getUsername(),setting.getPassword(),"Basic"), 0, THRESHOLD);
            mediaSettingReadCommand = new MediaSettingReadCommand(getContext(), threshold, mediaSettingView);
            mediaSettingReadCommand.execute();

            //read continuous snapshot interval parameter
            HttpRequest snapshotInterval = new ReadMediaSettingRequest(setting.getIpAddress(),
                    setting.getCommandPort(),
                    new Authorization(setting.getUsername(),setting.getPassword(),"Basic"), 0, SNAPSHOT_INTERVAL);
            mediaSettingReadCommand = new MediaSettingReadCommand(getContext(), snapshotInterval, mediaSettingView);
            mediaSettingReadCommand.execute();

        }
    };

    /*
     * Update media parameters on Server
     */
    private View.OnClickListener saveListener = new View.OnClickListener() {

        final String VALUE_ON = "on";
        final String VALUE_OFF = "off";

        @Override
        public void onClick(View view) {

            boolean settingError = false;

            //View resources
            EditText qualityImageEditText = (EditText) mediaSettingView.findViewById(R.id.quality_image);
            Spinner pictureTypeSpinenr = (Spinner) mediaSettingView.findViewById(R.id.picture_type);
            Switch movieSwitch = (Switch) mediaSettingView.findViewById(R.id.movie_switch);
            EditText maxMovieTimeEditText = (EditText) mediaSettingView.findViewById(R.id.max_movie_time);
            Switch snapshotSwitch = (Switch) mediaSettingView.findViewById(R.id.snapshot_switch);
            EditText thresholdEditText = (EditText) mediaSettingView.findViewById(R.id.threshold);
            EditText snapshotIntervalEditText = (EditText) mediaSettingView.findViewById(R.id.snapshot_interval);

            //set image quality parameter
            HttpRequest quality;
            String qualityValue = qualityImageEditText.getText().toString();
            if(qualityValue!=null){
                quality = new SetMediaSettingRequest(setting.getIpAddress(), setting.getCommandPort(),
                        new Authorization(setting.getUsername(),setting.getPassword(),"Basic"),0,QUALITY_PARAMETER,qualityValue);
                mediaSettingWriteCommand = new MediaSettingWriteCommand(getContext(),quality, mediaSettingView);
                mediaSettingWriteCommand.execute();
            }
            else {
                settingError = true;
            }

            //set picture type parameter
            String picTypeValue = pictureTypeSpinenr.getSelectedItem().toString();
            HttpRequest pictureType;
            if(picTypeValue!=null){
                pictureType = new SetMediaSettingRequest(setting.getIpAddress(), setting.getCommandPort(),
                        new Authorization(setting.getUsername(),setting.getPassword(),"Basic"),0,PICTURE_TYPE,picTypeValue);
                mediaSettingWriteCommand = new MediaSettingWriteCommand(getContext(),pictureType, mediaSettingView);
                mediaSettingWriteCommand.execute();
            }
            else {
                settingError = true;
            }

            //set record movie on detection
            boolean recordMovieEnabled = movieSwitch.isChecked();
            String recMovieValue;

            if(recordMovieEnabled){
                recMovieValue = VALUE_ON ;
            }
            else {
                recMovieValue = VALUE_OFF ;
            }

            HttpRequest outputMovie = new SetMediaSettingRequest(setting.getIpAddress(), setting.getCommandPort(),
                    new Authorization(setting.getUsername(),setting.getPassword(),"Basic"),0,OUTPUT_MOVIES,recMovieValue);
            mediaSettingWriteCommand = new MediaSettingWriteCommand(getContext(),outputMovie, mediaSettingView);
            mediaSettingWriteCommand.execute();

            //set max movie time parameter
            String maxMovieTimeValue = maxMovieTimeEditText.getText().toString();
            HttpRequest maxMovieTime;
            if(maxMovieTimeValue!=null){
                maxMovieTime = new SetMediaSettingRequest(setting.getIpAddress(), setting.getCommandPort(),
                        new Authorization(setting.getUsername(),setting.getPassword(),"Basic"),0,MAX_MOVIE_TIME,maxMovieTimeValue);
                mediaSettingWriteCommand = new MediaSettingWriteCommand(getContext(),maxMovieTime, mediaSettingView);
                mediaSettingWriteCommand.execute();
            }
            else {settingError = true;}

            //set output picture parameter
            boolean outputPicEnabled = snapshotSwitch.isChecked();
            String outPicValue;

            if(outputPicEnabled) {
                outPicValue = VALUE_ON ;
            }
            else {
                outPicValue = VALUE_OFF ;
            }

            HttpRequest snapshotOnDetection = new SetMediaSettingRequest(setting.getIpAddress(), setting.getCommandPort(),
                    new Authorization(setting.getUsername(),setting.getPassword(),"Basic"),0,OUTPUT_PICTURES,outPicValue);
            mediaSettingWriteCommand = new MediaSettingWriteCommand(getContext(),snapshotOnDetection, mediaSettingView);
            mediaSettingWriteCommand.execute();

            //set threshold parameter
            String thresholdValue = thresholdEditText.getText().toString();
            HttpRequest threshold;
            if(thresholdValue!=null){
                threshold = new SetMediaSettingRequest(setting.getIpAddress(), setting.getCommandPort(),
                        new Authorization(setting.getUsername(),setting.getPassword(),"Basic"),0,THRESHOLD,thresholdValue);
                mediaSettingWriteCommand = new MediaSettingWriteCommand(getContext(),threshold, mediaSettingView);
                mediaSettingWriteCommand.execute();
            }
            else{
                settingError = true;
            }

            //set snapshot interval
            String snapIntervalValue = snapshotIntervalEditText.getText().toString();
            HttpRequest snapshotInterval;
            if(snapIntervalValue!=null){
                snapshotInterval = new SetMediaSettingRequest(setting.getIpAddress(), setting.getCommandPort(),
                        new Authorization(setting.getUsername(),setting.getPassword(),"Basic"),0,SNAPSHOT_INTERVAL,snapIntervalValue);
                mediaSettingWriteCommand = new MediaSettingWriteCommand(getContext(),snapshotInterval, mediaSettingView);
                mediaSettingWriteCommand.execute();
            }
            else{
                settingError = true;
            }

            //write new config on Server
            HttpRequest writeRequest = new WriteMediaSettingRequest(setting.getIpAddress(), setting.getCommandPort(),
                    new Authorization(setting.getUsername(),setting.getPassword(),"Basic"),0);
            mediaSettingWriteCommand = new MediaSettingWriteCommand(getContext(),writeRequest, mediaSettingView);

            mediaSettingWriteCommand.execute();

            //restart server here....
            HttpRequest restartRequest = new RestartRequest(setting.getIpAddress(), setting.getCommandPort(),
                    new Authorization(setting.getUsername(),setting.getPassword(),"Basic"),0);
            restartCommand = new RestartCommand(getContext(),restartRequest, mediaSettingView);

            restartCommand.execute();

            if(settingError){
                Snackbar.make(view, "Error writing setting on Server: ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }

        }
    };
}
