package nw15s305.plantplaces.com.dao;

import java.util.ArrayList;
import java.util.List;

import nw15s305.plantplaces.com.dto.PlantDTO;

/**
 * Created by jonesb on 4/23/2015.
 */
public class PlantDAOStub implements IPlantDAO {

    @Override
    public List<PlantDTO> fetchPlants(String searchTerm) {
        // declare our return type.
        List<PlantDTO> allPlants = new ArrayList<PlantDTO>();

        // populate the list of allPlants with a hardcoded, known set of plants
        PlantDTO easternRedbud = new PlantDTO();
        easternRedbud.setGenus("Cercis");
        easternRedbud.setSpecies("canadensis");
        easternRedbud.setCommon("Eastern Redbud");

        // add the eastern redbud to our collection.
        allPlants.add(easternRedbud);

        PlantDTO chineseRedbud = new PlantDTO();
        chineseRedbud.setGenus("Cercis");
        chineseRedbud.setSpecies("chinensis");
        chineseRedbud.setCommon("Chinese Redbud");

        allPlants.add(chineseRedbud);

        PlantDTO lavendarTwistRedbud = new PlantDTO();
        lavendarTwistRedbud.setGenus("Cercis");
        lavendarTwistRedbud.setSpecies("canadensis");
        lavendarTwistRedbud.setCultivar("Lavendar Twist");
        lavendarTwistRedbud.setCommon("Lavendar Twist");

        allPlants.add(lavendarTwistRedbud);

        // return the return value.
        return allPlants;
    }
}
