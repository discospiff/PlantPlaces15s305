package nw15s305.plantplaces.com.dao;

import java.util.Set;

import nw15s305.plantplaces.com.dto.PlantDTO;

/**
 * Created by jonesb on 4/29/2015.
 */
public interface IOfflinePlantDAO extends IPlantDAO {
    void insert(PlantDTO plant);

    int countPlants();

    Set<Integer> fetchAllGuids();
}
