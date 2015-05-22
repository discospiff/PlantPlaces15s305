package nw15s305.plantplaces.com.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jonesb on 5/22/2015.
 */
public class PlantPlacesDAO extends SQLiteOpenHelper {

    // PLANTS table
    public static final String PLANTS = "PLANTS";
    public static final String CACHE_ID = "CACHE_ID";
    public static final String GENUS = "GENUS";
    public static final String SPECIES = "SPECIES";
    public static final String GUID = "GUID";
    public static final String CULTIVAR = "CULTIVAR";
    public static final String COMMON = "COMMON";

    // SPECIMENS table
    public static final String SPECIMENS = "SPECIMENS";
    public static final String PLANT_GUID = "PLANT_GUID";
    public static final String PLANT_CACHE_ID  = "PLANT_CACHE_ID";
    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String LOCATION = "LOCATION";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String PICTURE_URI = "PICTURE_URI";

    public PlantPlacesDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createPlants = "CREATE TABLE " + PLANTS + " ( " + CACHE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GUID + " INTEGER, " + GENUS + " TEXT, " + SPECIES + " TEXT, " + CULTIVAR + " TEXT, " + COMMON + " TEXT " + " );";
        db.execSQL(createPlants);

        String createSpecimens = "CREATE TABLE " + SPECIMENS + " ( " + CACHE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GUID + " INTEGER, " + PLANT_GUID + " INTEGER, " + PLANT_CACHE_ID + " INTEGER, " + LATITUDE + " TEXT, " +
                LONGITUDE + " TEXT, " + LOCATION + " TEXT, " + DESCRIPTION + " TEXT, " + PICTURE_URI + " TEXT " + " ); ";

        db.execSQL(createSpecimens);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
