package sweng.swatcher.fragment;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
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

    private Context context;
    private SettingManager settingManager;
    private Setting setting;
    private Authorization authorization;

    Media media;
    Picasso customPicasso;

    public CustomListViewAdapter(Context context, int resource, List<Media> mediaList, Authorization authorization) {
        super(context, resource, mediaList);
        this.context = context;
        this.authorization = authorization;
        this.settingManager = new SettingManager(context);
        this.setting = settingManager.getSetting();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
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

        media = getItem(position);

        holder.name.setText(media.getName());
        holder.size.setText(media.getSize());
        
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.listener(new CustomPicassoListner());
        customPicasso = builder.downloader(new CustomPicassoLoader(context)).build();
        customPicasso.load(getImageUrl(media)).into(holder.image);

        DialogListner dialogListner = new DialogListner(media);

        holder.singleItem.setOnClickListener(dialogListner);

        return convertView;
    }


    private class CustomPicassoListner implements Picasso.Listener{
        @Override
        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
        {
            Log.e("Image Load Failed:","CustomPicassoListener in CustomListViewAdapter");
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
           // Log.i("Immagine cliccata: ",getImageUrl(dialogMedia));

            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_gallery_image);
            //dialog.setTitle("Title...");
            ImageView image = (ImageView) dialog.findViewById(R.id.dialog_image);
            //image.setImageResource(R.drawable.ic_launcher);
            customPicasso.load(getImageUrl(dialogMedia)).into(image);

            Button dialogButton = (Button) dialog.findViewById(R.id.close_button);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

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

    public String getImageUrl(Media media){
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
            c.setRequestProperty("Authorization", authorization.getAuthType()+ " "
                    + Base64.encodeToString((authorization.getUsername()+":"+ authorization.getPassword()).getBytes(), Base64.NO_WRAP));
            return c;
        }
    }


    @NonNull
    @Override
    public Context getContext() {
        return context;
    }

    public SettingManager getSettingManager() {
        return settingManager;
    }

    public Setting getSetting() {
        return setting;
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public Media getMedia() {
        return media;
    }

    public Picasso getCustomPicasso() {
        return customPicasso;
    }

}