package sweng.swatcher.model;

import android.animation.ObjectAnimator;

import java.util.Objects;

/**
 * Created by ee on 07/10/16.
 */

public class Media {

    private String name;
    private String extension;
    private String size;
    private String date;
    private String path;

    public Media() {

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // equals is null safe but s taken for granted that all proprties
    // of this Object are always different from null

    @Override
    public boolean equals(Object object){
        Media otherMedia = (Media) object;
        if (otherMedia == null)
            return false;

        String otherName = otherMedia.getName();
        String otherExtension = otherMedia.getExtension();
        String otherDate = otherMedia.getDate();
        String otherPath = otherMedia.getPath();
        String otherSize = otherMedia.getSize();

        if( (this.name != null && this.name.equals(otherName))
                &&
                (this.extension != null && this.extension.equals(otherExtension))
                &&
                (this.date != null && this.date.equals(otherDate))
                &&
                (this.path != null && this.path.equals(otherPath))
                &&
                (this.size != null && this.size.equals(otherSize))
                )
            return true;

        else return false;

    }

}
