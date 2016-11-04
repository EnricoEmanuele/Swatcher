package sweng.swatcher;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.test.mock.MockContext;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sweng.swatcher.model.Setting;
import sweng.swatcher.util.PreferecesKeys;
import sweng.swatcher.util.SettingManager;

import static org.junit.Assert.assertEquals;
/**
 * Created by WILMER on 22/10/2016.
 */

public class SettingManagerTest {

    private SettingManager settingManager;
    private SharedPreferences sharedPreferences;
    private Setting oracleGet;

    @Before
    public void setUp(){

        Context context;
        //context = new MockContext();   //mock for Context dependency
        context = InstrumentationRegistry.getTargetContext();
        settingManager = new SettingManager(context);

        //Set oracle for getSetting() test
        oracleGet = new Setting();
        oracleGet.setIp1("127");
        oracleGet.setIp2("0");
        oracleGet.setIp3("0");
        oracleGet.setIp4("1");
        oracleGet.setIpAddress("127.0.0.1");
        oracleGet.setStreamingPort("5432");
        oracleGet.setCommandPort("4321");
        oracleGet.setWebServerPort("80");
        oracleGet.setUsername("user");
        oracleGet.setPassword("password");
        oracleGet.setNewUsername("u");
        oracleGet.setNewPassword("p");

        //Load Shared Preferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(PreferecesKeys.IP1, "127");
        editor.putString(PreferecesKeys.IP2, "0");
        editor.putString(PreferecesKeys.IP3, "0");
        editor.putString(PreferecesKeys.IP4, "1");
        editor.putString(PreferecesKeys.IP_ADDR, "127.0.0.1");
        editor.putString(PreferecesKeys.STREAM_PORT, "5432");
        editor.putString(PreferecesKeys.CMD_PORT, "4321");
        editor.putString(PreferecesKeys.WEB_PORT, "80");
        editor.putString(PreferecesKeys.USR, "user");
        editor.putString(PreferecesKeys.PSW, "password");
        editor.putString(PreferecesKeys.NEW_USR, "u");
        editor.putString(PreferecesKeys.NEW_PSW, "p");
        editor.commit();

    }

    @Test
    public void testGetSetting(){
        //Check if Setting objects are equal
        Setting setting = settingManager.getSetting();
        assertEquals("Setting objects are not equal",oracleGet,setting);
    }


    @Test
    public void testSetConnectionSetting(){
        // Logic to test setConnectionSetting method
    }

    @Test
    public void testSetSecuritySetting(){
        // Logic to test setSecuritySetting method
    }
}
