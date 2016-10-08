package sweng.swatcher.model;

/**
 * Created by ee on 07/10/16.
 */

public class Image {

    private String name;
    private String extension;
    private String size;
    private String date;

    public Image(String name, String extension, String size, String date) {
        this.name = name;
        this.extension = extension;
        this.size = size;
        this.date = date;
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
}
