package sweng.swatcher.request;

import org.junit.Before;
import org.junit.Test;

import sweng.swatcher.model.Authorization;
import sweng.swatcher.util.ParametersKeys;

import static org.junit.Assert.assertEquals;

/**
 * Created by WILMER on 28/10/2016.
 */

public class SetMediaSettingRequestTest {

    private String oracleURL;
    private SetMediaSettingRequest setMediaSettingRequest;
    String parameter = ParametersKeys.OUTPUT_PICTURES; //output_pictures
    String value = "on";

    @Before
    public void setUp(){
        oracleURL = "http://127.0.0.1:4321/0/config/set?output_pictures=on";
        setMediaSettingRequest = new SetMediaSettingRequest("127.0.0.1","4321",new Authorization("user","password","Basic"),0,parameter,value);
    }

    @Test
    public void getURL() throws Exception {
        //Chech getURL
        assertEquals("URL not mach", oracleURL, setMediaSettingRequest.getURL());
    }

}
