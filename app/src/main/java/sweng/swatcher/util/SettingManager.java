package sweng.swatcher.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import sweng.swatcher.model.Setting;

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
        String ip1 = sp.getString(PreferecesKeys.IP1,"");
        String ip2 = sp.getString(PreferecesKeys.IP2,"");
        String ip3 = sp.getString(PreferecesKeys.IP3,"");
        String ip4 = sp.getString(PreferecesKeys.IP4,"");
        String stream_port = sp.getString(PreferecesKeys.STREAM_PORT,"");
        String cmd_port = sp.getString(PreferecesKeys.CMD_PORT,"");
        String web_port = sp.getString(PreferecesKeys.WEB_PORT,"");
        String usr = sp.getString(PreferecesKeys.USR,"");
        String psw = sp.getString(PreferecesKeys.PSW,"");
        String new_usr = sp.getString(PreferecesKeys.NEW_USR,"");
        String new_psw = sp.getString(PreferecesKeys.NEW_PSW,"");
        settings.setIp1(ip1);
        settings.setIp2(ip2);
        settings.setIp3(ip3);
        settings.setIp4(ip4);
        settings.setIpAddress(ip1 + "." + ip2 + "." + ip3 + "." + ip4);
        settings.setStreamingPort(stream_port);
        settings.setCommandPort(cmd_port);
        settings.setWebServerPort(web_port);
        settings.setUsername(usr);
        settings.setPassword(psw);
        settings.setNewUsername(new_usr);
        settings.setNewPassword(new_psw);

        return settings;
    }

    public void setConnectionSetting(Setting stg){
        editor = sp.edit();
        editor.putString(PreferecesKeys.IP1, stg.getIp1().toString());
        editor.putString(PreferecesKeys.IP2, stg.getIp2().toString());
        editor.putString(PreferecesKeys.IP3, stg.getIp3().toString());
        editor.putString(PreferecesKeys.IP4, stg.getIp4().toString());
        editor.putString(PreferecesKeys.IP_ADDR, stg.getIpAddress().toString());
        editor.putString(PreferecesKeys.STREAM_PORT, stg.getStreamingPort().toString());
        editor.putString(PreferecesKeys.CMD_PORT, stg.getCommandPort().toString());
        editor.putString(PreferecesKeys.WEB_PORT, stg.getWebServerPort().toString());
        editor.putString(PreferecesKeys.USR, stg.getUsername().toString());
        editor.putString(PreferecesKeys.PSW, stg.getPassword().toString());
//        editor.putString(PreferecesKeys.NEW_USR, stg.getNewUsername().toString());
 //       editor.putString(PreferecesKeys.NEW_PSW, stg.getNewPassword().toString());
        editor.commit();
    }

    public void setSecuritySetting(Setting stg){
        editor.putString(PreferecesKeys.NEW_USR, stg.getNewUsername().toString());
        editor.putString(PreferecesKeys.NEW_PSW, stg.getNewPassword().toString());
        editor.commit();
    }
}
