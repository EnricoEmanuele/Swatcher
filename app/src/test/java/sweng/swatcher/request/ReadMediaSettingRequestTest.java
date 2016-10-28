package sweng.swatcher.request;

import org.junit.Before;
import org.junit.Test;

import sweng.swatcher.model.Authorization;

import static org.junit.Assert.assertEquals;

/**
 * Created by WILMER on 28/10/2016.
 */

public class ReadMediaSettingRequestTest {

    private String oracleURL;
    private ReadMediaSettingRequest readMediaSettingRequest;
    private String parameter = "picture_type";

    @Before
    public void setUp(){
        oracleURL = "http://127.0.0.1:80/0/config/get?query=picture_type";
        readMediaSettingRequest = new ReadMediaSettingRequest("127.0.0.1","80",new Authorization("user","password","Basic"),0,parameter);
    }

    @Test
    public void getURL() throws Exception {
        //Chech getURL
        assertEquals("URL not mach", oracleURL, readMediaSettingRequest.getURL());
    }

}
