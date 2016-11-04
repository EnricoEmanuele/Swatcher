package sweng.swatcher.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import sweng.swatcher.model.Setting;

/**
 * Created by ee on 10/10/16.
 */

public class SettingManager {

    private Context context;
    private SharedPreferences sharedPreferences;

    public SettingManager(Context ctx) {
        this.context = ctx;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public Context getContext() {
        return context;
    }

    public SharedPreferences getSharedPreferences() {
        return this.sharedPreferences;
    }

    public SharedPreferences.Editor getEditor(){
        return this.sharedPreferences.edit();
    }

    public Setting getSetting(){

        Setting settings = new Setting();

        String ip1 = this.sharedPreferences.getString(PreferecesKeys.IP1,"");
        String ip2 = this.sharedPreferences.getString(PreferecesKeys.IP2,"");
        String ip3 = this.sharedPreferences.getString(PreferecesKeys.IP3,"");
        String ip4 = this.sharedPreferences.getString(PreferecesKeys.IP4,"");
        String stream_port = this.sharedPreferences.getString(PreferecesKeys.STREAM_PORT,"");
        String cmd_port = this.sharedPreferences.getString(PreferecesKeys.CMD_PORT,"");
        String web_port = this.sharedPreferences.getString(PreferecesKeys.WEB_PORT,"");
        String usr = this.sharedPreferences.getString(PreferecesKeys.USR,"");
        String psw = this.sharedPreferences.getString(PreferecesKeys.PSW,"");
        String new_usr = this.sharedPreferences.getString(PreferecesKeys.NEW_USR,"");
        String new_psw = this.sharedPreferences.getString(PreferecesKeys.NEW_PSW,"");

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

        SharedPreferences.Editor editor = this.sharedPreferences.edit();

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
        // editor.putString(PreferecesKeys.NEW_USR, stg.getNewUsername().toString());
        // editor.putString(PreferecesKeys.NEW_PSW, stg.getNewPassword().toString());
        editor.commit();
    }

    public void setSecuritySetting(Setting stg){

        SharedPreferences.Editor editor = this.sharedPreferences.edit();

        editor.putString(PreferecesKeys.NEW_USR, stg.getNewUsername().toString());
        editor.putString(PreferecesKeys.NEW_PSW, stg.getNewPassword().toString());

        editor.commit();

    }
}
