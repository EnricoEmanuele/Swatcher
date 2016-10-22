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
    private ListView galleryListView;
    private ArrayAdapter<Media> mediaAdapter;
    private Activity activity;

    public GalleryResponseControl(HttpRequest request, List<Media> mediaList, ListView galleryListView, Activity activity) {
        this.request = request;
        this.mediaList = mediaList;
        this.galleryListView = galleryListView;
        this.activity = activity;
    }

    public void checkResponse() {

        Thread thread = new Thread() {
            @Override
            public void run() {

                while (request.getResponse() == null) {}

                //  mediaAdapter = new CustomListViewAdapter(activity, R.layout.item_gallery, mediaList);
                // galleryListView.setAdapter(mediaAdapter);

            }
        };
        thread.start();
    }

    // Only Get Method are available
    public HttpRequest getRequest() {
        return request;
    }

    public List<Media> getMediaList() {
        return mediaList;
    }

    public ListView getGalleryListView() {
        return galleryListView;
    }

    public ArrayAdapter<Media> getMediaAdapter() {
        return mediaAdapter;
    }

    public Activity getActivity() {
        return activity;
    }

}
