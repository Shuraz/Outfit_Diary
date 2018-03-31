package edu.csu.surajpokhrel.outfit_diary.allAccess;

/**
 * Created by surajpokhrel on 1/10/17.
 */

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class LocationInfo {
    private String lat;
    private String log;
    private String placeName;
    private String info;

    public LocationInfo() {
    }

    public LocationInfo(String lat, String log, String placeName, String info) {
        this.lat = lat;
        this.log = log;
        this.placeName = placeName;
        this.info = info;
    }

    public String getLat() {
        return lat;
    }

    public String getLog() {
        return log;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getInfo() {
        return info;
    }


}
