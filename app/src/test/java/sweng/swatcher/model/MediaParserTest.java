package sweng.swatcher.model;

import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by WILMER on 27/10/2016.
 */

public class MediaParserTest {

    static JSONArray inputJsonArray ;
    static List<Media> oracleMediaArrayList ;

    @Before
    public void setUp() throws JSONException {


        inputJsonArray = new JSONArray("[{\"name\": \"02-20161026153311.avi.mp4\",\"extension\": \"mp4\",\"size\": 85255,\"date\": \"October 26 2016 15:33:42.\",\"path\": \"picam\\/02-20161026153311.avi.mp4\"},{\"name\": \"01-20161026145359-05.jpg\",\"extension\": \"jpg\",\"size\": 9721,\"date\": \"October 26 2016 14:54:29.\",\"path\": \"picam\\/01-20161026145359-05.jpg\"}]");


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

        System.out.println("setup");

    }


    @Test
    public void test_parse() throws JSONException {
        List<Media> mediaArrayList = MediaParser.parse(inputJsonArray);

        System.out.println("media array list size: " +mediaArrayList.size());
        System.out.println("oracle arraylist size: " +oracleMediaArrayList.size());

        System.out.println("input json array size: " +inputJsonArray.length());

        assertEquals("i due media non coincidono", mediaArrayList.get(0), oracleMediaArrayList.get(0));


    }


}
