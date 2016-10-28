package sweng.swatcher.request;

import android.view.View;

import org.junit.Before;
import org.junit.Test;

import sweng.swatcher.model.Authorization;

import static org.junit.Assert.assertEquals;

/**
 * Created by WILMER on 28/10/2016.
 */

public class WriteMediaSettingRequestTest {

    private String oracleURL;
    private WriteMediaSettingRequest writeMediaSettingRequest;

    @Before
    public void setUp(){
        oracleURL = "http://127.0.0.1:4321/0/config/write";
        writeMediaSettingRequest = new WriteMediaSettingRequest("127.0.0.1","4321",new Authorization("user","password","Basic"),0);
    }

    @Test
    public void getURL() throws Exception {
        //Chech getURL
        assertEquals("URL not mach", oracleURL, writeMediaSettingRequest.getURL());
    }

}
