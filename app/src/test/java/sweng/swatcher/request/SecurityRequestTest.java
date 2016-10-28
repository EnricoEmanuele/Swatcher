package sweng.swatcher.request;

import org.junit.Before;
import org.junit.Test;

import sweng.swatcher.model.Authorization;

import static org.junit.Assert.assertEquals;

/**
 * Created by WILMER on 28/10/2016.
 */

public class SecurityRequestTest {

    private String oracleURL;
    private SecurityRequest securityRequest;

    @Before
    public void setUp(){
        oracleURL = "http://127.0.0.1:80/newCredentials.php";
        securityRequest = new SecurityRequest("127.0.0.1","80",new Authorization("user","password","Basic"));
    }

    @Test
    public void getURL() throws Exception {
        //Chech getURL
        assertEquals("URL not mach", oracleURL, securityRequest.getURL());
    }

}
