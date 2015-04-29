package nw15s305.plantplaces.com.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import nw15s305.plantplaces.com.dto.PlantDTO;

/**
 * Created by jonesb on 4/27/2015.
 */
public class OfflinePlantDAO extends SQLiteOpenHelper implements IPlantDAO {

    public static final String PLANTS = "PLANTS";
    public static final String CACHE_ID = "CACHE_ID";
    public static final String GENUS = "GENUS";
    public static final String SPECIES = "SPECIES";
    public static final String GUID = "GUID";
    public static final String CULTIVAR = "CULTIVAR";
    public static final String COMMON = "COMMON";

    public OfflinePlantDAO(Context ctx) {
        super(ctx, "plantplaces.db", null, 1);
    }

    @Override
    public List<PlantDTO> fetchPlants(String searchTerm) throws IOException, JSONException {
        return null;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String createPlants = "CREATE TABLE " + PLANTS + " ( " + CACHE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GUID + " INTEGER, " + GENUS + " TEXT, " + SPECIES + " TEXT, " + CULTIVAR + " TEXT, " + COMMON + " TEXT " + " );";
        db.execSQL(createPlants);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
