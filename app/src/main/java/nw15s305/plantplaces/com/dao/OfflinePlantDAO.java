package nw15s305.plantplaces.com.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nw15s305.plantplaces.com.dto.PlantDTO;

/**
 * Created by jonesb on 4/27/2015.
 */
public class OfflinePlantDAO extends PlantPlacesDAO implements IOfflinePlantDAO {

    public OfflinePlantDAO(Context ctx) {
        super(ctx, "plantplaces.db", null, 1);
    }

    @Override
    public List<PlantDTO> fetchPlants(String searchTerm) throws IOException, JSONException {
        String sql = "SELECT * FROM " + PLANTS + " ";
        String where = " WHERE " + GENUS + " LIKE '%" + searchTerm + "%' OR " + SPECIES + " LIKE '%" + searchTerm + "%' OR " + CULTIVAR + " LIKE '%" + searchTerm + "%' OR " + COMMON + " LIKE '%" + searchTerm + "%'";
        return innerSelect(sql + where);

    }

    private List<PlantDTO> innerSelect(String sql) {
        // declare my return variable.
        List<PlantDTO> allPlants = new ArrayList<PlantDTO>();

        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                PlantDTO plant = new PlantDTO();
                plant.setCacheID(cursor.getInt(cursor.getColumnIndex(CACHE_ID)));
                plant.setGuid(cursor.getInt(cursor.getColumnIndex(GUID)));
                plant.setGenus(cursor.getString(cursor.getColumnIndex(GENUS)));
                plant.setSpecies(cursor.getString(cursor.getColumnIndex(SPECIES)));
                plant.setCultivar(cursor.getString(cursor.getColumnIndex(CULTIVAR)));
                plant.setCommon(cursor.getString(cursor.getColumnIndex(COMMON)));
                allPlants.add(plant);
                // move to the next row.
                cursor.moveToNext();
            }
        }

        cursor.close();
        return allPlants;
    }


    @Override
    public void insert(PlantDTO plant){
        // create our COntent Values
        ContentValues cv = new ContentValues();
        cv.put(GUID, plant.getGuid() );
        cv.put(GENUS, plant.getGenus());
        cv.put(SPECIES, plant.getSpecies());
        cv.put(CULTIVAR, plant.getCultivar());
        cv.put(COMMON, plant.getCommon());

        // insert the record into the database
        long cacheID = getWritableDatabase().insert(PLANTS, GENUS, cv);

        // store the cache ID back in our DTO.
        plant.setCacheID(cacheID);


    }

    @Override
    public int countPlants() {
        int plantCount = 0;

        // our SQL statement
        String sql = "SELECT COUNT(*) FROM " + PLANTS;

        // run the query
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        // did we get a result?
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            plantCount = cursor.getInt(0);
        }

        // close the cursor
        cursor.close();

        return plantCount;
    }


    @Override
    public Set<Integer> fetchAllGuids(){
        // declare the return type.
        Set<Integer> allGuids = new HashSet<Integer>();

        // assemble SQL Statement.
        String sql = "SELECT " + GUID + " FROM " + PLANTS;

        // run the query.
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        // did we get results?
        if (cursor.getCount() > 0) {
            // move to the first result.
            cursor.moveToFirst();
            //iterate over the results.
            while (!cursor.isAfterLast()) {
                // get the value.
                int guid = cursor.getInt(cursor.getColumnIndex(GUID));

                // add this GUID to our set of GUIDs.
                allGuids.add(Integer.valueOf(guid));

                // go to the next row.
                cursor.moveToNext();

            }

        }

        cursor.close();

        return allGuids;

    }




}
