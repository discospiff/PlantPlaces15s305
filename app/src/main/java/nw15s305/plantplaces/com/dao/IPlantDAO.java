package nw15s305.plantplaces.com.dao;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import nw15s305.plantplaces.com.dto.PlantDTO;

/**
 * Created by jonesb on 4/24/2015.
 */
public interface IPlantDAO {
    List<PlantDTO> fetchPlants(String searchTerm) throws IOException, JSONException;
}
