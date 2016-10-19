package sweng.swatcher.util;

import android.support.design.widget.FloatingActionButton;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ee on 19/10/16.
 */

public class MediaButtonSet {

    private View view;
    private List<FloatingActionButton> buttonListPlay;
    private List<FloatingActionButton> buttonListStop;

    public MediaButtonSet(View view) {
        buttonListPlay = new ArrayList<FloatingActionButton>();
        buttonListStop = new ArrayList<FloatingActionButton>();
        this.view = view;
    }

    public void addToPlayList(FloatingActionButton button){
        buttonListPlay.add(button);
    }

    public void addToStopList(FloatingActionButton button){
        buttonListStop.add(button);
    }

    public void removeFromPlayList(FloatingActionButton button){
        buttonListPlay.remove(button);
    }

    public void removeFromStopList(FloatingActionButton button){
        buttonListStop.remove(button);
    }


    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void displayAllFromPlayList(){
        for(FloatingActionButton fb : buttonListPlay){
            fb.setVisibility(view.VISIBLE);
        }
    }

    public void displayAllFromStopList(){
        for(FloatingActionButton fb : buttonListStop){
            fb.setVisibility(view.VISIBLE);
        }
    }

    public void hideAllFromPlayList(){
        for(FloatingActionButton fb : buttonListPlay){
            fb.setVisibility(view.GONE);
        }
    }

    public void hideAllFromStopList(){
        for(FloatingActionButton fb : buttonListStop){
            fb.setVisibility(view.GONE);
        }
    }

    public void display(FloatingActionButton button){
        button.setVisibility(view.VISIBLE);

    }

    public void hide(FloatingActionButton button){
        button.setVisibility(view.GONE);
    }

    public FloatingActionButton getPlayElementAt(int index){
        return buttonListPlay.get(index);
    }

    public FloatingActionButton getStopElementAt(int index){
        return buttonListStop.get(index);
    }
}
