package nw15s305.plantplaces.com.dao;

import android.content.Context;

/**
 * Created by jonesb on 5/22/2015.
 */
public class OfflineSpecimenDAO extends PlantPlacesDAO {

    public OfflineSpecimenDAO (Context ctx) {
        super(ctx, "plantplaces.db", null, 1);
    }
}
