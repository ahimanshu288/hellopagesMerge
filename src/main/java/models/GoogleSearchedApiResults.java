package models;

/**
 * Created by LinchPin-Dev on 6/5/2015.
 */
public class GoogleSearchedApiResults {
    public GoogleSearchedApiResults(Long latitude,Long longitude,String name)
    {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }
    Long latitude;
    Long longitude;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public Long getLatitude() {
        return latitude;
    }
    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }
}
