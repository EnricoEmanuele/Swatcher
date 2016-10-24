package sweng.swatcher.fragment;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.UrlConnectionDownloader;
import sweng.swatcher.R;
import sweng.swatcher.util.SettingManager;
import sweng.swatcher.model.Authorization;
import sweng.swatcher.model.Media;
import sweng.swatcher.model.Setting;


public class CustomListViewAdapter extends ArrayAdapter<Media> {

    private Context ctx;
    private SettingManager sm;
    private Setting setting;
    private Authorization auth;
    public static final String JPEG = "jpeg";
    public static final String JPG = "jpg";
    public static final String PPM = "ppm";

    Media media;
    Picasso customPicasso;

    public CustomListViewAdapter(Context ctx, int resource, List<Media> mediaList, Authorization auth) {
        super(ctx, resource, mediaList);
        this.ctx = ctx;
        this.auth = auth;
        this.sm = new SettingManager(ctx);
        this.setting = sm.getSetting();


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.item_gallery, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        media = getItem(position);

        holder.name.setText(media.getName());
        holder.size.setText(media.getSize());

        Picasso.Builder builder = new Picasso.Builder(ctx);
        builder.listener(new CustomPicassoListner());
        customPicasso = builder.downloader(new CustomPicassoLoader(ctx)).build();
        customPicasso.load(getMediaUrl(media)).into(holder.image);

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
        Media dialog_media;

        public DialogListner(Media dialog_media) {
            this.dialog_media = dialog_media;
        }

        @Override
        public void onClick(View v)
        {
            //Log.i("Immagine cliccata: ",dialog_media.getExtension());
            final Dialog dialog = new Dialog(ctx);
            String extension = dialog_media.getExtension();

            //if media is an image
            if(extension.equalsIgnoreCase(JPEG) ||extension.equalsIgnoreCase(JPG)|| extension.equalsIgnoreCase(PPM)){
                Log.i("Immagine cliccata: ",dialog_media.getExtension());
                dialog.setContentView(R.layout.dialog_gallery_image);
                //dialog.setTitle("Title...");
                ImageView image = (ImageView) dialog.findViewById(R.id.dialog_image);
                //image.setImageResource(R.drawable.ic_launcher);
                customPicasso.load(getMediaUrl(dialog_media)).into(image);

                Button dialogButton = (Button) dialog.findViewById(R.id.close_button);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
            else{
                //if media is a video
                Log.i("Video selected: ",dialog_media.getExtension());

            }



            dialog.show();
        }

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
        return "http://"+setting.getIpAddress()+":"+setting.getWebServerPort()+"/"+path;

    }

    public class CustomPicassoLoader extends UrlConnectionDownloader {
        public CustomPicassoLoader(Context context) {
            super(context);
        }

        @Override
        protected HttpURLConnection openConnection(Uri path) throws IOException {
            HttpURLConnection c = super.openConnection(path);
            c.setRequestProperty("Authorization", auth.getAuthType()+ " "
                    + Base64.encodeToString((auth.getUsername()+":"+auth.getPassword()).getBytes(), Base64.NO_WRAP));
            return c;
        }
    }

}