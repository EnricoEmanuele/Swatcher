package sweng.swatcher.model;

/**
 * Created by antoniods311 on 20/10/16.
 */

public class MediaSetting {

    private int maxMovieTime;     //massima durata del video registrato
    private String outputPicture; //snapshot quando rileva movimento (on/off/first/center/best)
    private int qualityImage;     //Qualità della compressione immagine 0-99%
    private String pictureType;   //type image jpeg o ppm
    private int threshold;        //Soglia pixel che cambiano per far scattare detection (1500 def)
    private int snapshotInterval; // fa snapshot ogni N secondi. 0 disabilitato


    public MediaSetting() {

    }

    public int getMaxMovieTime() {
        return maxMovieTime;
    }

    public void setMaxMovieTime(int maxMovieTime) {
        this.maxMovieTime = maxMovieTime;
    }

    public String getOutputPicture() {
        return outputPicture;
    }

    public void setOutputPicture(String outputPicture) {
        this.outputPicture = outputPicture;
    }

    public int getQualityImage() {
        return qualityImage;
    }

    public void setQualityImage(int qualityImage) {
        this.qualityImage = qualityImage;
    }

    public String getPictureType() {
        return pictureType;
    }

    public void setPictureType(String pictureType) {
        this.pictureType = pictureType;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getSnapshotInterval() {
        return snapshotInterval;
    }

    public void setSnapshotInterval(int snapshotInterval) {
        this.snapshotInterval = snapshotInterval;
    }
}
