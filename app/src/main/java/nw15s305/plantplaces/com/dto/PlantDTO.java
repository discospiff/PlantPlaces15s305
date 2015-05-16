package nw15s305.plantplaces.com.dto;

import java.util.List;

/**
 * This class represents the attributes that describe a plant.
 * Created by jonesb on 4/23/2015.
 */
public class PlantDTO {

    private long cacheID;
    int guid;
    String genus;
    String species;
    String cultivar;
    String common;

    public List<SpecimenDTO> getSpecimens() {
        return specimens;
    }

    public void setSpecimens(List<SpecimenDTO> specimens) {
        this.specimens = specimens;
    }

    List<SpecimenDTO> specimens;

    public int getGuid() {
        return guid;
    }

    public void setGuid(int guid) {
        this.guid = guid;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getCultivar() {
        return cultivar;
    }

    public void setCultivar(String cultivar) {
        this.cultivar = cultivar;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public String toString() {
        return genus + " " + species + " " + cultivar + " " + common;
    }

    public long getCacheID() {
        return cacheID;
    }

    public void setCacheID(long cacheID) {
        this.cacheID = cacheID;
    }
}
