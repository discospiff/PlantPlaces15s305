package nw15s305.plantplaces.com.dao;

import java.util.ArrayList;
import java.util.List;

import nw15s305.plantplaces.com.dto.PlantDTO;
import nw15s305.plantplaces.com.dto.SpecimenDTO;

/**
 * Created by jonesb on 5/16/2015.
 */
public class SpecimenDAOStub implements ISpecimenDAO {
    @Override
    public void save(SpecimenDTO specimen) throws Exception {
        if (specimen.getPlantCacheId() == 0 && specimen.getPlantGuid() == 0) {
            throw new Exception ("A plant is not associated with this specimen.  Please select a plant.");
        }
    }

    @Override
    public List<PlantDTO> search(String searchTerm) {
        List<PlantDTO> allPlants = new ArrayList<PlantDTO>();

        if (searchTerm.contains("Redbud")) {
            // create a mockup plant.
            PlantDTO plant = new PlantDTO();
            plant.setGuid(83);
            plant.setGenus("Cercis");
            plant.setSpecies("canadensis");
            plant.setCommon("Eastern Redbud");

            // create a speciemn to associate with that plant.
            SpecimenDTO specimen = new SpecimenDTO();
            specimen.setLatitude("84.57");
            specimen.setLongitude("39.47");
            specimen.setLocation("Cincinnati");

            List<SpecimenDTO> allSpecimens = new ArrayList<SpecimenDTO>();
            allSpecimens.add(specimen);

            // add the specimen collection
            // to the plant.
            plant.setSpecimens(allSpecimens);
            allPlants.add(plant);
        }

        // return the collection of plants and specimens.
        return allPlants;
    }

    @Override
    public List<PlantDTO> search(double latitude, double longitude, double range) {
        return null;
    }
}
