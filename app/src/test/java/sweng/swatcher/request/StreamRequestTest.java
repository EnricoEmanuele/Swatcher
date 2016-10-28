package sweng.swatcher.request;

import android.view.View;

import org.junit.Before;
import org.junit.Test;

import sweng.swatcher.model.Authorization;

import static org.junit.Assert.assertEquals;

/**
 * Created by WILMER on 28/10/2016.
 */

public class StreamRequestTest {

    private String oracleURL;
    private StreamRequest streamRequest;


    @Before
    public void setUp(){
        oracleURL = "http://127.0.0.1:5432";
        streamRequest = new StreamRequest("127.0.0.1","5432",new Authorization("user","password","Basic"));
    }

    @Test
    public void getURL() throws Exception {
        //Chech getURL
        assertEquals("URL not mach", oracleURL, streamRequest.getURL());
    }

}
