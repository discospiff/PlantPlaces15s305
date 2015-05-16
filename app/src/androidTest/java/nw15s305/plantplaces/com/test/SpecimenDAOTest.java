package nw15s305.plantplaces.com.test;

import android.test.InstrumentationTestCase;

import java.util.List;

import nw15s305.plantplaces.com.dao.ISpecimenDAO;
import nw15s305.plantplaces.com.dao.SpecimenDAOStub;
import nw15s305.plantplaces.com.dto.PlantDTO;
import nw15s305.plantplaces.com.dto.SpecimenDTO;

/**
 * Created by jonesb on 5/16/2015.
 */
public class SpecimenDAOTest extends InstrumentationTestCase {

    private SpecimenDTO specimenDTO;
    private ISpecimenDAO specimenDAO;
    private boolean specimenHasSaved;
    private String searchTerm;
    private List<PlantDTO> plantDTOs;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        specimenDAO = new SpecimenDAOStub();
    }

    public void testSaveValidSpecimen() {
        givenSpecimenIsInitializedWithData();
        whenSpecimenIsSaved();
        thenVerifyNoException();
    }

    public void testInvalidSpecimenThrowsExceptionOnSave () {
        givenSpecimenIsNotInitialized();
        whenSpecimenIsSaved();
        thenVerifyException();
    }

    public void testRedbudSearchReturnsRedbud() {
        givenSearchTermInitilaizedToRedbud();
        whenSearched();
        thenVerifyRedbudReturned();
    }

    public void testRedbudSearchDoesNotReturnPawpaw() {
        givenSearchTermInitilaizedToRedbud();
        whenSearched();
        thenVerifyPawpawNotReturned();
    }

    private void thenVerifyPawpawNotReturned() {
        boolean pawpawReturned = false;

        for (PlantDTO plantDTO : plantDTOs) {
            if (plantDTO.getCommon().contains("Pawpaw")) {
                pawpawReturned = true;
                break;
            }
        }
        assertFalse(pawpawReturned);
    }

    private void thenVerifyRedbudReturned() {
        boolean redbudReturned = false;

        for (PlantDTO plantDTO : plantDTOs) {
            if (plantDTO.getCommon().contains("Redbud")) {
                redbudReturned = true;
                break;
            }
        }
        assertTrue(redbudReturned);
    }

    private void whenSearched() {
        plantDTOs = specimenDAO.search(searchTerm);
    }

    private void givenSearchTermInitilaizedToRedbud() {
        searchTerm = "Redbud";
    }


    private void thenVerifyException() {
        assertFalse(specimenHasSaved);
    }

    
    
    private void givenSpecimenIsNotInitialized() {
        specimenDTO = new SpecimenDTO();
    }

    private void thenVerifyNoException() {
        assertTrue(specimenHasSaved);
    }

    private void whenSpecimenIsSaved() {

        try {
            specimenDAO.save(specimenDTO);
            specimenHasSaved = true;
        } catch (Exception e) {
            e.printStackTrace();
            specimenHasSaved = false;
        }

    }

    private void givenSpecimenIsInitializedWithData() {
        specimenDTO = new SpecimenDTO();
        specimenDTO.setDescription("Test Specimen");
        specimenDTO.setLocation("Test Location");
        specimenDTO.setPlantGuid(84);
        specimenDTO.setPlantCacheId(100);
    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
