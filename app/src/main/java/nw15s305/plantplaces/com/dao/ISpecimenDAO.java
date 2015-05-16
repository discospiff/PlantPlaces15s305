package nw15s305.plantplaces.com.dao;

import java.util.List;

import nw15s305.plantplaces.com.dto.PlantDTO;
import nw15s305.plantplaces.com.dto.SpecimenDTO;

/**
 * Created by jonesb on 5/16/2015.
 */
public interface ISpecimenDAO {

    /**
     * Save the SpecimenDTO to the persistence layer.
     */
    public void save(SpecimenDTO specimen) throws Exception;

    /**
     * Return all plants with specimens that match the search term.
     * @param searchTerm
     * @return
     */
    public List<PlantDTO> search(String searchTerm);

    /**
     * Return all specimens near a certain point.
     * @param latitude
     * @param longitude
     * @param range
     * @return
     */
    public List<PlantDTO> search (double latitude, double longitude, double range);
}
