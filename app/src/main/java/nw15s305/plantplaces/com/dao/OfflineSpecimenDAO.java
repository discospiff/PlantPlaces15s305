package nw15s305.plantplaces.com.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import nw15s305.plantplaces.com.dto.PlantDTO;
import nw15s305.plantplaces.com.dto.SpecimenDTO;

/**
 * Created by jonesb on 5/22/2015.
 */
public class OfflineSpecimenDAO extends PlantPlacesDAO implements ISpecimenDAO {

    public OfflineSpecimenDAO (Context ctx) {
        super(ctx, "plantplaces.db", null, 1);
    }

    @Override
    public void save(SpecimenDTO specimen) throws Exception {
        // collection to hold our data to insert.
        ContentValues values = new ContentValues();

        // populate the collection.
        values.put(PLANT_CACHE_ID, specimen.getPlantCacheId());
        values.put(PLANT_GUID, specimen.getPlantCacheId());
        values.put(LOCATION, specimen.getLocation());
        values.put(LATITUDE, specimen.getLatitude());
        values.put(LONGITUDE, specimen.getLongitude());
        values.put(DESCRIPTION, specimen.getDescription());
        values.put(PICTURE_URI, specimen.getPhoto());

        // insert this row into the database.
        long cacheId = getWritableDatabase().insert(SPECIMENS, LATITUDE, values);
        specimen.setSpecimenCacheId(cacheId);
    }

    @Override
    public List<PlantDTO> search(String searchTerm) {
        String sql = "SELECT " + "p." + GENUS +", "+ "p." + SPECIES +", " + "p." + CULTIVAR +", " + "p." + COMMON +", " +
                " s." + LATITUDE + ", " + " s." + LONGITUDE + ", " + " s." + LOCATION + ", " + " s." + DESCRIPTION
                + " FROM " + SPECIMENS + " s "
                + " JOIN " + PLANTS + " p "
                + " ON s." + PLANT_CACHE_ID  + " = p." + CACHE_ID
                + " WHERE p." + GENUS + " LIKE '%" + searchTerm + "%' OR "
                + "p." + SPECIES  + " LIKE '%" + searchTerm + "%' OR "
                + "p." + CULTIVAR + " LIKE '%" + searchTerm + "%' OR "
                + "p." + COMMON + " LIKE '%" + searchTerm + "%'";

        // execute the sql statement.
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        // declare a collection that will hold the results.
        ArrayList<PlantDTO> allPlants = new ArrayList<PlantDTO>();

        // iterate over the results
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                // plant data.

                String genus = cursor.getString(0);
                String species = cursor.getString(1);
                String cultivar = cursor.getString(2);
                String common = cursor.getString(3);

                PlantDTO plant = new PlantDTO();
                plant.setGenus(genus);
                plant.setSpecies(species);
                plant.setCultivar(cultivar);
                plant.setCommon(common);

                //specimen data
                String latitude = cursor.getString(4);
                String longitude = cursor.getString(5);
                String location = cursor.getString(6);
                String description = cursor.getString(7);

                SpecimenDTO specimen = new SpecimenDTO();
                specimen.setLatitude(latitude);
                specimen.setLongitude(longitude);
                specimen.setLongitude(location);
                specimen.setDescription(description);

                // add the specimen to the collection.
                List<SpecimenDTO> allSpecimens = new ArrayList<SpecimenDTO>();

                // add the specimen to the plant.
                plant.setSpecimens(allSpecimens);

                // add the plant to the collection of plants.
                allPlants.add(plant);

                cursor.moveToNext();
            }

        }
        cursor.close();
        return allPlants;
    }

    @Override
    public List<PlantDTO> search(double latitude, double longitude, double range) {
        return null;
    }
}
