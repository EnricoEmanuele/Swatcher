package sweng.swatcher.fragment;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.UrlConnectionDownloader;

import sweng.swatcher.R;
import sweng.swatcher.util.SettingManager;
import sweng.swatcher.model.Authorization;
import sweng.swatcher.model.Media;
import sweng.swatcher.model.Setting;


public class CustomListViewAdapter extends ArrayAdapter<Media> {

    private static final String JPEG_IMAGE_EXTENSION = "jpeg";
    private static final String JPG_IMAGE_EXTENSION = "jpg";
    private static final String PPM_IMAGE_EXTENSION = "ppm";

    private Context context;
    private SettingManager settingManager;
    private Setting setting;
    private Authorization authorization;

    private Media media;
    private Picasso customPicasso;

    public CustomListViewAdapter(Context context, int resource, List<Media> mediaList, Authorization authorization) {
        super(context, resource, mediaList);
        this.context = context;
        this.authorization = authorization;
        this.settingManager = new SettingManager(context);
        this.setting = settingManager.getSetting();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        media = getItem(position);

        // If holder not exist then locate all view from UI file.
        ViewHolder holder = null;

        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.item_gallery, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        }
        else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(media.getName());
        holder.size.setText(media.getSize());

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.listener(new CustomPicassoListner());

        if(isImage(media)){
            customPicasso = builder.downloader(new CustomPicassoLoader(context)).build();
            customPicasso.load(getMediaUrl(media)).into(holder.image);
        }
        else{
            //Media is a Video
            holder.image.setImageResource(R.drawable.video_icon);
        }

        DialogListner dialogListner = new DialogListner(media);
        holder.singleItem.setOnClickListener(dialogListner);

        return convertView;
    }


    private class CustomPicassoListner implements Picasso.Listener{
        @Override
        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private class DialogListner implements View.OnClickListener
    {
        Media dialogMedia;

        public DialogListner(Media dialogMedia) {
            this.dialogMedia = dialogMedia;
        }

        @Override
        public void onClick(View v)
        {
            //Log.i("Immagine cliccata: ",dialogMedia.getExtension());
            final Dialog dialog = new Dialog(context);
            Button dialogButton = null;

            if(isImage(dialogMedia)){
                // Media is an Image
                onImageClick(dialog, dialogButton );
            }
            else{
                // media is a Video
                onVideoClick(dialog, dialogButton );
            }
            dialog.show();
        }

        // auxiliary method used in onClick(View v)
        private void onImageClick(final Dialog dialog, Button dialogButton ){
            Log.i("Image selected: ", dialogMedia.getExtension());
            dialog.setContentView(R.layout.dialog_gallery_image);
            //dialog.setTitle("Title...");
            ImageView image = (ImageView) dialog.findViewById(R.id.dialog_image);
            //image.setImageResource(R.drawable.ic_launcher);
            customPicasso.load(getMediaUrl(dialogMedia)).into(image);

            // if button is clicked, close the custom dialog
            dialogButton = (Button) dialog.findViewById(R.id.close_button);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        // auxiliary method used in onClick(View v)
        private void onVideoClick(final Dialog dialog, Button dialogButton ){
            Log.i("Video selected: ", dialogMedia.getExtension());
            dialog.setContentView(R.layout.dialog_gallery_video);
            final VideoView videoView = (VideoView) dialog.findViewById(R.id.video_view);

            // Progress Dialog For Downloading...
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Download Video");
            progressDialog.setMessage("downloading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            //set controllers
            final MediaController mediaController = new MediaController(context);
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
            mediaController.setMediaPlayer(videoView);

            //set headers
            Map<String,String> headers = new HashMap<String,String>();
            String credentials = authorization.getUsername()+":"+ authorization.getPassword();
            String auth = authorization.getAuthType()+ " " + android.util.Base64.encodeToString(credentials.getBytes(), android.util.Base64.NO_WRAP);
            headers.put("Authorization", auth);
            //videoView.setVideoURI(Uri.parse(getMediaUrl(dialogMedia)),headers);
            try{
                Method setVideoURIMethod = videoView.getClass().getMethod("setVideoURI", Uri.class, Map.class);
                setVideoURIMethod.invoke(videoView,Uri.parse(getMediaUrl(dialogMedia)),headers);
            }
            catch (java.lang.NoSuchMethodException nsme){
                Log.i("exception","no such method");
                nsme.printStackTrace();
            }
            catch (InvocationTargetException ite) {
                Log.i("exception","invocation target");
                ite.printStackTrace();
            }
            catch (IllegalAccessException iae) {
                Log.i("exception","illegal access");
                iae.printStackTrace();
            }

            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                        /*mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                            @Override
                            public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {
                                MediaController mc = new MediaController(ctx);
                                videoView.setMediaController(mc);
                                mc.setAnchorView(videoView);
                            }
                        });*/
                    progressDialog.dismiss();
                    videoView.setMediaController(mediaController);
                    mediaController.setAnchorView(videoView);
                    videoView.seekTo(0);
                    videoView.requestFocus();
                    videoView.start();
                    mediaController.show();
                }
            });
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    //mp.release();
                }
            });
            // if button is clicked, close the custom dialog
            dialogButton = (Button) dialog.findViewById(R.id.close_video_button);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog.dismiss();
                    dialog.dismiss();
                }
            });
        }
        // end of onVideoClick method implementation

    }

    private static class ViewHolder {
        private TextView name;
        private TextView size;
        private ImageView image;
        private RelativeLayout singleItem;

        public ViewHolder(View v) {
            name = (TextView) v.findViewById(R.id.img_name);
            image = (ImageView) v.findViewById(R.id.dialog_image);
            size = (TextView) v.findViewById(R.id.img_size);
            singleItem = (RelativeLayout) v.findViewById(R.id.single_item_gallery);
        }
    }

    public String getMediaUrl(Media media){
        String path = media.getPath();
        String uri = "http://"+setting.getIpAddress()+":"+setting.getWebServerPort()+"/"+path;
        Log.i("media uri",uri);
        return uri;

    }

    public class CustomPicassoLoader extends UrlConnectionDownloader {
        public CustomPicassoLoader(Context context) {
            super(context);
        }

        @Override
        protected HttpURLConnection openConnection(Uri path) throws IOException {
            HttpURLConnection c = super.openConnection(path);
            c.setRequestProperty("Authorization", authorization.getAuthType()+ " "
                    + Base64.encodeToString((authorization.getUsername()+":"+ authorization.getPassword()).getBytes(), Base64.NO_WRAP));
            return c;
        }
    }

    private boolean isImage(Media media){
        String ext = media.getExtension();
        if(ext.equalsIgnoreCase(JPEG_IMAGE_EXTENSION) || ext.equalsIgnoreCase(JPG_IMAGE_EXTENSION) || ext.equalsIgnoreCase(PPM_IMAGE_EXTENSION)){
            return true;
        }
        else return false;
    }

}