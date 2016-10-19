package sweng.swatcher.response;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import sweng.swatcher.model.Media;
import sweng.swatcher.request.HttpRequest;

import static java.lang.Thread.sleep;

/**
 * Created by ee on 18/10/16.
 */

public class GalleryResponseControl {

    private HttpRequest request;
    private List<Media> mediaList;
    private ListView gallery_listview;
    private ArrayAdapter<Media> mediaAdapter;
    private Activity activity;

    public GalleryResponseControl(HttpRequest request, List<Media> mediaList, ListView gallery_listview, Activity activity) {
        this.request = request;
        this.mediaList = mediaList;
        this.gallery_listview = gallery_listview;
        this.activity = activity;
    }

    public void checkResponse() {

        Thread thread = new Thread() {
            @Override
            public void run() {

                try {
                    while (request.getResponse() == null) {
                        sleep(0);
                    }
                  //  mediaAdapter = new CustomListViewAdapter(activity, R.layout.item_gallery, mediaList);
                   // gallery_listview.setAdapter(mediaAdapter);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
