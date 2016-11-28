package sweng.swatcher.util;

/**
 * Created by antoniods311 on 23/11/16.
 */

public class MediaParameterLimit {

    /*
     * MIN & MAX included
     */

    public static final int QUALITY_MIN_VALUE = 1;
    public static final int QUALITY_MAX_VALUE = 100;

    public static final int MOVIE_TIME_MIN_VALUE = 1;
    public static final int MOVIE_TIME_MAX_VALUE = 60;

    public static final int THRESHOLD_MIN_VALUE = 250;
    public static final int THRESHOLD_MAX_VALUE = 18000;

    public static final int SNAPSHOT_INTERVAL_MIN_VALUE= 0;

    /*
     * Methods to check orrectness into interval values allowed
     * this methods are null and empty safe
    */

    public static boolean qualityValueIsCorrect(String qualityValue){
        if( qualityValue!=null && !qualityValue.isEmpty() ) {
            int qualityValueInt = Integer.parseInt(qualityValue);
            //check Boundary Values
            if(qualityValueInt >= MediaParameterLimit.QUALITY_MIN_VALUE
                    && qualityValueInt <= MediaParameterLimit.QUALITY_MAX_VALUE) {
                return true;
            }
            else
                return false;
        }
        else {
            //qualityValue is null or empty
            return false;
        }
    }

    public static boolean maxMovieTimeValueIsCorrect(String maxMovieTimeValue){
        if( maxMovieTimeValue!=null && !maxMovieTimeValue.isEmpty() ) {
            int maxMovieTimeValueInt = Integer.parseInt(maxMovieTimeValue);
            //check Boundary Values
            if( maxMovieTimeValueInt >= MediaParameterLimit.MOVIE_TIME_MIN_VALUE
                    && maxMovieTimeValueInt <= MediaParameterLimit.MOVIE_TIME_MAX_VALUE){
                return true;
            }
            else {
                return false;
            }
        }
        else{
            //maxMovieTimeValue is null or empty
            return false;
        }
    }

    public static boolean thresholdValueIsCorrect(String thresholdValue ){
        if( thresholdValue!=null && !thresholdValue.isEmpty() ) {
            int thresholdValueInt = Integer.parseInt(thresholdValue);
            //check Boundary Values
            if( thresholdValueInt >= MediaParameterLimit.THRESHOLD_MIN_VALUE
                    && thresholdValueInt <= MediaParameterLimit.THRESHOLD_MAX_VALUE){
                return true;
            }
            else {
                return false;
            }
        }
        else{
            //thresholdValue is null or empty
            return false;
        }
    }

    public static boolean snapshotIntervalValueIsCorrect(String snapshotIntervalValue){
        if( snapshotIntervalValue!=null && !snapshotIntervalValue.isEmpty() ){
            int snapIntervalValueInt = Integer.parseInt(snapshotIntervalValue);
            //check Boundary Values
            if( snapIntervalValueInt >= MediaParameterLimit.SNAPSHOT_INTERVAL_MIN_VALUE ){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            //snapshotIntervalValue is null or empty
            return false;
        }

    }


}
