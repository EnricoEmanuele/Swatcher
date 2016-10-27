package sweng.swatcher.request;

import org.junit.Before;
import org.junit.Test;
import sweng.swatcher.model.Authorization;
import static org.junit.Assert.*;

/**
 * Created by antoniods311 on 27/10/16.
 */
public class GalleryRequestTest {

    private String oracleURL;
    private GalleryRequest galleryRequest;

    @Before
    public void setUp(){
        oracleURL = "http://127.0.0.1:80/jsonEncoder.php";
        galleryRequest = new GalleryRequest("127.0.0.1","80",new Authorization("user","password","Basic"));
    }

    @Test
    public void getURL() throws Exception {
        //Chech getURL
        assertEquals("URL not mach", oracleURL, galleryRequest.getURL());
    }

}