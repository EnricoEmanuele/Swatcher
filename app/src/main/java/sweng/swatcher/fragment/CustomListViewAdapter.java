package sweng.swatcher.fragment;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
        LayoutInflater inflater = (LayoutInflater) ctx
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
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

        Media media = getItem(position);

        holder.name.setText(media.getName());
        holder.size.setText(media.getSize());
        //Picasso.with(ctx).load("http://i.imgur.com/DvpvklR.png").into(holder.image);
        Picasso customPicasso = new Picasso.Builder(ctx).downloader(new CustomPicassoLoader(ctx)).build();
        customPicasso.load(getImageUrl(media)).into(holder.image);
        return convertView;
    }

    private static class ViewHolder {
        private TextView name;
        private TextView size;
        private ImageView image;

        public ViewHolder(View v) {
            name = (TextView) v.findViewById(R.id.img_name);
            image = (ImageView) v.findViewById(R.id.image);
            size = (TextView) v.findViewById(R.id.img_size);
        }
    }

    public String getImageUrl(Media media){
        String path = media.getPath();
        Log.i("Path", "http://"+setting.getIpAddress()+":"+setting.getWebServerPort()+"/"+path);
        return "http://"+setting.getIpAddress()+":"+setting.getWebServerPort()+"/"+path;

    }

    public class CustomPicassoLoader extends UrlConnectionDownloader {
        public CustomPicassoLoader(Context context) {
            super(context);
        }

        @Override
        protected HttpURLConnection openConnection(Uri path) throws IOException {
            HttpURLConnection c = super.openConnection(path);
            c.setRequestProperty("Authorization", auth.getAuthType() +
                    Base64.encodeToString((auth.getUsername()+":"+auth.getPassword()).getBytes(), Base64.NO_WRAP));

            Log.i("Credentials", auth.getUsername()+":"+auth.getPassword());

            return c;
        }
    }

}