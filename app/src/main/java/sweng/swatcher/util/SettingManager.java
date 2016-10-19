package sweng.swatcher.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import sweng.swatcher.model.Setting;
import sweng.swatcher.util.PreferecesKeys;

/**
 * Created by ee on 10/10/16.
 */

public class SettingManager {

    SharedPreferences sp;
    Context ctx;
    SharedPreferences.Editor editor;

    public SettingManager(Context ctx) {
        this.ctx = ctx;
        this.sp = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public Setting getSetting(){
        Setting settings = new Setting();
        String ip = sp.getString(PreferecesKeys.IP,"");
        String stream_port = sp.getString(PreferecesKeys.STREAM_PORT,"");
        String cmd_port = sp.getString(PreferecesKeys.CMD_PORT,"");
        String web_port = sp.getString(PreferecesKeys.WEB_PORT,"");
        String usr = sp.getString(PreferecesKeys.USR,"");
        String psw = sp.getString(PreferecesKeys.PSW,"");
        String new_usr = sp.getString(PreferecesKeys.NEW_USR,"");
        String new_psw = sp.getString(PreferecesKeys.NEW_PSW,"");
        settings.setIpAddress(ip);
        settings.setStreamingPort(stream_port);
        settings.setCommandPort(cmd_port);
        settings.setWebServerPort(web_port);
        settings.setUsername(usr);
        settings.setPassword(psw);
        settings.setNewUsername(new_usr);
        settings.setNewPassword(new_psw);

        return settings;
    }

    public void setSetting(Setting stg){
        editor = sp.edit();
        editor.putString(PreferecesKeys.IP, stg.getIpAddress().toString());
        editor.putString(PreferecesKeys.STREAM_PORT, stg.getStreamingPort().toString());
        editor.putString(PreferecesKeys.CMD_PORT, stg.getCommandPort().toString());
        editor.putString(PreferecesKeys.WEB_PORT, stg.getWebServerPort().toString());
        editor.putString(PreferecesKeys.USR, stg.getUsername().toString());
        editor.putString(PreferecesKeys.PSW, stg.getPassword().toString());
        editor.putString(PreferecesKeys.NEW_USR, stg.getNewUsername().toString());
        editor.putString(PreferecesKeys.NEW_PSW, stg.getNewPassword().toString());
        editor.commit();
    }
}
