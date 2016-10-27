package sweng.swatcher.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WILMER on 27/10/2016.
 */

public class MediaParserTest {

    JSONArray inputJsonArray ;
    List<Media> oracleMediaArrayList ;

    @BeforeClass
    public void setUpJsonArray() throws JSONException {

        //this.inputJsonArray = new JSONArray("[{\"name\":\"02-20161026153311.avi.mp4\",\"extension\":\"mp4\",\"size\":85255,\"date\":\"October 26 2016 15:33:42.\",\"path\":\"picam\/02-20161026153311.avi.mp4\"},{\"name\":\"01-20161026145359.avi.mp4\",\"extension\":\"mp4\",\"size\":145265,\"date\":\"October 26 2016 14:54:31.\",\"path\":\"picam\/01-20161026145359.avi.mp4\"},{\"name\":\"02-20161026153312-01.jpg\",\"extension\":\"jpg\",\"size\":9995,\"date\":\"October 26 2016 15:33:41.\",\"path\":\"picam\/02-20161026153312-01.jpg\"},{\"name\":\"01-20161026145359-05.jpg\",\"extension\":\"jpg\",\"size\":9721,\"date\":\"October 26 2016 14:54:29.\",\"path\":\"picam\/01-20161026145359-05.jpg\"}]");

        /*JSONParser parser = new JSONParser();

        this.inputJsonArray = (JSONArray) parser.parse(new FileReader("c:\\exer4-courses.json"));*/


    }


    @Test
    public void test_parse(){
        List<Media> mediaArrayList = new ArrayList<Media>();
    }


}
