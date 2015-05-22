package nw15s305.plantplaces.com.dto;

/**
 * Created by jonesb on 5/16/2015.
 */
public class SpecimenDTO {
    private int plantGuid;
    private long plantCacheId;
    private int specimenGuid;
    private long specimenCacheId;
    private String latitude;
    private String longitude;
    private String location;
    private String description;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    private String photo;


    public int getPlantGuid() {
        return plantGuid;
    }

    public void setPlantGuid(int plantGuid) {
        this.plantGuid = plantGuid;
    }

    public long getPlantCacheId() {
        return plantCacheId;
    }

    public void setPlantCacheId(long plantCacheId) {
        this.plantCacheId = plantCacheId;
    }

    public int getSpecimenGuid() {
        return specimenGuid;
    }

    public void setSpecimenGuid(int specimenGuid) {
        this.specimenGuid = specimenGuid;
    }

    public long getSpecimenCacheId() {
        return specimenCacheId;
    }

    public void setSpecimenCacheId(long specimenCacheId) {
        this.specimenCacheId = specimenCacheId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return location + " " + description;
    }
}
