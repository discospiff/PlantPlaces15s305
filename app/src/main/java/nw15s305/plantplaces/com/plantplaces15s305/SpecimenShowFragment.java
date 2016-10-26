package nw15s305.plantplaces.com.plantplaces15s305;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import nw15s305.plantplaces.com.dao.ISpecimenDAO;
import nw15s305.plantplaces.com.dao.OfflineSpecimenDAO;
import nw15s305.plantplaces.com.dao.SpecimenDAOStub;
import nw15s305.plantplaces.com.dto.PlantDTO;
import nw15s305.plantplaces.com.dto.SpecimenDTO;

/**
 * Created by jonesb on 5/22/2015.
 */
public class SpecimenShowFragment extends ListFragment {

    private ISpecimenDAO specimenDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // this will hold our collection of specimens.
        final List<SpecimenDTO> specimens = new ArrayList<SpecimenDTO>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        databaseReference.child("foo").addValueEventListener(new ValueEventListener() {

            /**
             * This method will be invoked any time the data on the database changes.
             * Additionally, it will be invoked as soon as we connect the listener, so that we can get an initial snapshot of the data on the database.
             * @param dataSnapshot
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get all of the children at this level.
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                
                // shake hands with each of them.'
                for (DataSnapshot child : children) {
                    SpecimenDTO specimenDTO = child.getValue(SpecimenDTO.class);
                    specimens.add(specimenDTO);
                }
                
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//
//        specimenDAO = new OfflineSpecimenDAO(getActivity());
//
//        // fetch the specimens that match the search term.
//        List<PlantDTO> specimens = specimenDAO.search("e");

        // Make an ArrayAdapter to show our results.
        ArrayAdapter<SpecimenDTO> plantAdapter = new ArrayAdapter<SpecimenDTO>(getActivity(), android.R.layout.simple_list_item_1, specimens);

        // set this specimen list in the fragment
        setListAdapter(plantAdapter);

        //

    }
}
