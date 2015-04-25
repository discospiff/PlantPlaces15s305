package nw15s305.plantplaces.com.dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nw15s305.plantplaces.com.dto.PlantDTO;

/**
 * Created by jonesb on 4/24/2015.
 */
public class PlantDAO implements IPlantDAO {

    private final NetworkDAO networkDAO;

    public PlantDAO() {
        networkDAO = new NetworkDAO();
    }

    @Override
    public List<PlantDTO> fetchPlants(String searchTerm) throws IOException, JSONException {
        // the string is the result of our request.
        String uri = "http://plantplaces.com/perl/mobile/viewplantsjson.pl?Combined_Name="+searchTerm;
        String request = networkDAO.request(uri);

        // Create a variable to hold our return data.
        List<PlantDTO> allPlants = new ArrayList<PlantDTO>();

        // Parse the entire JSON String
        JSONObject root = new JSONObject(request);
        // get the array of plants from JSON
        JSONArray plants = root.getJSONArray("plants");

        for (int i = 0; i < plants.length(); i++) {
            // parse the JSON object into its fields and values.
            JSONObject jsonPlant = plants.getJSONObject(i);
            int guid = jsonPlant.getInt("id");
            String genus = jsonPlant.getString("genus");
            String species = jsonPlant.getString("species");
            String cultivar = jsonPlant.getString("cultivar");
            String common = jsonPlant.getString("common");

            // create a PLantDTO object that we will populate with JSON Data.
            PlantDTO plant = new PlantDTO();
            plant.setGuid(guid);
            plant.setGenus(genus);
            plant.setSpecies(species);
            plant.setCultivar(cultivar);
            plant.setCommon(common);

            // add our newly created Plant DTO to our collection.
            allPlants.add(plant);
        }
        // return our collection of planst.
        return allPlants;
    }
}
