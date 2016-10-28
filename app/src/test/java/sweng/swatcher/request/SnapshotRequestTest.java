package sweng.swatcher.request;

import android.view.View;

import org.junit.Before;
import org.junit.Test;

import sweng.swatcher.model.Authorization;

import static org.junit.Assert.assertEquals;

/**
 * Created by WILMER on 28/10/2016.
 */

public class SnapshotRequestTest {

    private String oracleURL;
    private SnapshotRequest snapshotRequest;
    private View view;

    @Before
    public void setUp(){
        oracleURL = "http://127.0.0.1:80/0/action/snapshot";
        snapshotRequest = new SnapshotRequest("127.0.0.1","80",new Authorization("user","password","Basic"),0,view);
    }

    @Test
    public void getURL() throws Exception {
        //Chech getURL
        assertEquals("URL not mach", oracleURL, snapshotRequest.getURL());
    }

}
