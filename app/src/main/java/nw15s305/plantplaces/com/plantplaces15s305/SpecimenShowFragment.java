package nw15s305.plantplaces.com.plantplaces15s305;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;

import nw15s305.plantplaces.com.dao.ISpecimenDAO;
import nw15s305.plantplaces.com.dao.OfflineSpecimenDAO;
import nw15s305.plantplaces.com.dao.SpecimenDAOStub;
import nw15s305.plantplaces.com.dto.PlantDTO;

/**
 * Created by jonesb on 5/22/2015.
 */
public class SpecimenShowFragment extends ListFragment {

    private ISpecimenDAO specimenDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        specimenDAO = new OfflineSpecimenDAO(getActivity());

        // fetch the specimens that match the search term.
        List<PlantDTO> specimens = specimenDAO.search("e");

        // Make an ArrayAdapter to show our results.
        ArrayAdapter<PlantDTO> plantAdapter = new ArrayAdapter<PlantDTO>(getActivity(), android.R.layout.simple_list_item_1, specimens);

        // set this specimen list in the fragment
        setListAdapter(plantAdapter);

        //

    }
}
