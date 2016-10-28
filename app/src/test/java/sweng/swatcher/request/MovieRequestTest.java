package sweng.swatcher.request;

import android.view.View;

import org.junit.Before;
import org.junit.Test;

import sweng.swatcher.R;
import sweng.swatcher.model.Authorization;

import static org.junit.Assert.assertEquals;

/**
 * Created by WILMER on 28/10/2016.
 */

public class MovieRequestTest {

    private String oracleURL;
    private MovieRequest movieRequest;
    View view ;

    @Before
    public void setUp(){
        oracleURL = "http://127.0.0.1:4321/0/action/makemovie";
        movieRequest = new MovieRequest("127.0.0.1","4321",new Authorization("user","password","Basic"),0,view);
    }

    @Test
    public void getURL() throws Exception {
        //Chech getURL
        assertEquals("URL not mach", oracleURL, movieRequest.getURL());
    }

}
