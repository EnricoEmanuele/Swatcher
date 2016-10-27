package sweng.swatcher.model;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.*;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import java.util.List;
import sweng.swatcher.model.Media;
import sweng.swatcher.model.MediaParser;
import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertSame;

/**
 * Created by WILMER on 27/10/2016.
 */

@RunWith(AndroidJUnit4.class)
public class MediaParserTest {

    private JSONArray inputJsonArray;
    private List<Media> oracleMediaArrayList;
    private List<Media> mediaArrayList;

    @Before
    public void setUp() {

        String jsonString = "[" +
                "{ \"name\": \"02-20161026153311.avi.mp4\",\"extension\": \"mp4\",\"size\": 85255,\"date\": \"October 26 2016 15:33:42.\",\"path\": \"picam/02-20161026153311.avi.mp4\"}, " +
                "{\"name\": \"01-20161026145359-05.jpg\",\"extension\": \"jpg\",\"size\": 9721,\"date\": \"October 26 2016 14:54:29.\",\"path\": \"picam/01-20161026145359-05.jpg\"}" +
                "]";
        try {
            inputJsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Errore nella generazione del jsonArray");
        }

        Media media1 = new Media();
        media1.setDate("October 26 2016 15:33:42.");
        media1.setExtension("mp4");
        media1.setPath("picam/02-20161026153311.avi.mp4");
        media1.setSize("85255");
        media1.setName("02-20161026153311.avi.mp4");

        Media media2 = new Media();
        media2.setDate("October 26 2016 14:54:29.");
        media2.setExtension("jpg");
        media2.setPath("picam/01-20161026145359-05.jpg");
        media2.setSize("9721");
        media2.setName("01-20161026145359-05.jpg");

        oracleMediaArrayList = new ArrayList<Media>();
        oracleMediaArrayList.add(media1);
        oracleMediaArrayList.add(media2);

    }

    @Test
    public void test_parse() throws JSONException {

        mediaArrayList = MediaParser.parse(inputJsonArray);

        System.out.println("media array list size: " +mediaArrayList.size());
        System.out.println("oracle arraylist size: " +oracleMediaArrayList.size());
        Log.i("size array: " ,""+inputJsonArray.length());

        //Check size
        assertEquals("Different size",mediaArrayList.size(),oracleMediaArrayList.size());

        //Check media
        for(int i=0; i<mediaArrayList.size(); i++){
            assertEquals("Different media", mediaArrayList.get(i), oracleMediaArrayList.get(i));
        }
    }
}
