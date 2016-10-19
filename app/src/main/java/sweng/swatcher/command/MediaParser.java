package sweng.swatcher.command;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sweng.swatcher.model.Media;

/**
 * Created by ee on 18/10/16.
 */

public class MediaParser {

    public static List<Media> parse(JSONArray jsonArray) throws JSONException {

        List<Media> mediaArrayList = new ArrayList<Media>();

        for(int i=0; i<jsonArray.length(); i++){
            Media media = new Media();
            JSONObject jObject = jsonArray.getJSONObject(i);

            media.setName(jObject.getString("name"));
            media.setExtension(jObject.getString("extension"));
            media.setSize(jObject.getString("size"));
            media.setDate(jObject.getString("date"));
            media.setPath(jObject.getString("path"));

            mediaArrayList.add(media);

        }
        return mediaArrayList;
    }
}
