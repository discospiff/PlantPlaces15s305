package nw15s305.plantplaces.com.plantplaces15s305;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import nw15s305.plantplaces.com.dao.IOfflinePlantDAO;
import nw15s305.plantplaces.com.dao.IPlantDAO;
import nw15s305.plantplaces.com.dao.ISpecimenDAO;
import nw15s305.plantplaces.com.dao.OfflinePlantDAO;
import nw15s305.plantplaces.com.dao.OfflineSpecimenDAO;
import nw15s305.plantplaces.com.dao.PlantDAO;
import nw15s305.plantplaces.com.dao.PlantDAOStub;
import nw15s305.plantplaces.com.dto.PlantDTO;
import nw15s305.plantplaces.com.dto.SpecimenDTO;


public class GPSAPlant extends PlantPlacesActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, GestureDetector.OnGestureListener {

    private double longitude;
    private double latitude;
    private TextView lblLongitudeValue;
    private TextView lblLatitudeValue;
    private boolean paused = false;
    private Button btnPause;
    private ProgressDialog plantProgressDialog;
    private long cacheID;
    private int guid;
    private AutoCompleteTextView location;
    private AutoCompleteTextView description;
    private Uri pictureUri;
    private GestureDetectorCompat detector;

    @Override
    public int getCurrentMenuId() {
        return R.id.gpsaplant;
    }

    public static final int CAMERA_REQUEST = 10;
    private AutoCompleteTextView actPlantName;
    private ImageView imgSpecimenPhoto;
    private FusedLocationProviderApi locationProvider = LocationServices.FusedLocationApi;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    public final static int MILLISECONDS_PER_SECOND = 1000;
    public final static int MINUTE = 60 * MILLISECONDS_PER_SECOND;

    ISpecimenDAO specimenDAO;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // associate the layout with this activity.
        setContentView(R.layout.activity_gpsaplant);

        actPlantName = (AutoCompleteTextView) findViewById(R.id.actPlantName);

        // Create an instance of the PlantSelected Listener.
        PlantSelected ps = new PlantSelected();

        // subscribe actPlantName to this PlantSelected Listener.
        actPlantName.setOnItemClickListener(ps);
        actPlantName.setOnItemSelectedListener(ps);

        // get plant names for our AutoCompleteTextView
        PlantSearchTask pst = new PlantSearchTask();
        pst.execute("e");

        // get access to the image view.
        imgSpecimenPhoto = (ImageView) findViewById(R.id.imgSpecimenPhoto);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        // initialize the location request with the accuracy and frequency in which we want GPS updates.
        locationRequest = new LocationRequest();

        locationRequest.setInterval(MINUTE);
        locationRequest.setFastestInterval(15 * MILLISECONDS_PER_SECOND);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        lblLongitudeValue = (TextView) findViewById(R.id.lblLongitudeValue);
        lblLatitudeValue = (TextView) findViewById(R.id.lblLatitudeValue);

        btnPause = (Button) findViewById(R.id.btnPause);

        specimenDAO = new OfflineSpecimenDAO(this);

        description = (AutoCompleteTextView) findViewById(R.id.actDescription);
        location = (AutoCompleteTextView) findViewById(R.id.actLocation);

        detector = new GestureDetectorCompat(this, this);

    }

    /**
     * This method will be invoked when a button is clicked, and that button's onClick method is btnShowSavedClicked
     * @param v
     */
    public void btnShowSavedClicked(View v) {
        // add an explicit intent to invoke our Specimen Show Fragment
        Intent specimenShowIntent = new Intent(this, SpecimenShowActivity.class);
        startActivity(specimenShowIntent);

    }

    public void btnPauseClicked (View v) {

        if (paused == false) {
            // we are un-paused, we want to pause.
            pauseGPS();
            paused = true;
            Toast.makeText(this, "Paused", Toast.LENGTH_LONG).show();
            // change the label on the button.
            btnPause.setText(R.string.lblResume);
        } else {
            // we are paused, we want to un-pause.
            resumeGPS();
            paused = false;
            Toast.makeText(this, "Resumed", Toast.LENGTH_LONG).show();
            // change the label on the button.
            btnPause.setText(getString(R.string.lblPause));
        }
    }

    /**
     * This method will be called when the Take Photo button is clicked.
     * @param v
     */
    public void btnTakePhotoClicked(View v) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureName = getPictureName();
        File imageFile = new File(pictureDirectory, pictureName);
        pictureUri = Uri.fromFile(imageFile);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    public void onSaveClicked(View v) {
        // create a DTO to hold our specimen information.
        SpecimenDTO specimen = new SpecimenDTO();

        // populate the specimen with values from the screen.
        specimen.setPlantCacheId(cacheID);
        specimen.setPlantGuid(guid);
        specimen.setLocation(location.getText().toString());
        specimen.setDescription(description.getText().toString());
        specimen.setLatitude(Double.toString(latitude));
        specimen.setLongitude(Double.toString(longitude));
        if (pictureUri != null) {
            specimen.setPhoto(pictureUri.toString());
        }

        // save the specimen.
        try {
            // specimenDAO.save(specimen);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = database.getReference();
            databaseReference.child("foo").push().setValue(specimen);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.unableToSaveSpecimen, Toast.LENGTH_LONG).show();
        }

    }

    private String getPictureName() {
       SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        return "PlantPlacesImage" + timestamp + ".jpg";

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Did the user choose OK?  If so, the code inside these curly braces will execute.
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                // we are hearing back from the camera.
                Bitmap cameraImage = (Bitmap) data.getExtras().get("data");
                // at this point, we have the image from the camera.
                imgSpecimenPhoto.setImageBitmap(cameraImage);
            }
        }


    }



    @Override
    public void onConnected(Bundle bundle) {
        requestLocationUpdates();
    }

    private void requestLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();

    }

    @Override
    protected void onResume() {
        super.onResume();
        resumeGPS();
    }

    private void resumeGPS() {
        if (googleApiClient.isConnected()) {
            requestLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseGPS();
    }

    private PendingResult<Status> pauseGPS() {
        return LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        // Toast.makeText(this, "Location changed; " + location.getLatitude() + " "  + location.getLongitude(), Toast.LENGTH_LONG).show();
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        lblLatitudeValue.setText(Double.toString(latitude));
        lblLongitudeValue.setText(Double.toString(longitude));


    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        // add an explicit intent to invoke our Specimen Show Fragment
        Intent specimenShowIntent = new Intent(this, SpecimenShowActivity.class);
        startActivity(specimenShowIntent);
        return true;
    }

    class PlantSearchTask extends AsyncTask<String, Integer, List<PlantDTO>> {

        @Override
        protected void onPostExecute(List<PlantDTO> plantDTOs) {
            super.onPostExecute(plantDTOs);
            plantProgressDialog.dismiss();
            ArrayAdapter<PlantDTO> plantAdapter = new ArrayAdapter<PlantDTO>(GPSAPlant.this.getApplicationContext(), android.R.layout.simple_list_item_1, plantDTOs);
            actPlantName.setAdapter(plantAdapter);

        }

        @Override
        protected List<PlantDTO> doInBackground(String... params) {
            publishProgress(1);
            IPlantDAO plantDAO = new PlantDAO();
            IOfflinePlantDAO offlinePlantDAO = new OfflinePlantDAO(GPSAPlant.this);
            List<PlantDTO> allPlants = new ArrayList<PlantDTO>();

            int countPlants = offlinePlantDAO.countPlants();

            int plantCounter = 0;

            // if we have less than 1000 plants, we don't have them all; let's get them.
            if (countPlants < 10000) {
                try {
                    publishProgress(2);
                    allPlants = plantDAO.fetchPlants(params[0]);
                    publishProgress(3);

                    Set<Integer> localGUIDs = offlinePlantDAO.fetchAllGuids();


                    // iterate over all of the plants we fetched, and place them into the local database.
                    for (PlantDTO plant : allPlants) {

                        // do we have a valid GUID, and is it NOT in our local database?  If so, then insert.
                        if (plant.getGuid() > 0 && !localGUIDs.contains(Integer.valueOf(plant.getGuid()))) {
                            // insert into database.
                            offlinePlantDAO.insert(plant);
                        }
                        // update the progress indicator to show how much we have saved into the database
                        plantCounter++;
                        if (plantCounter % (allPlants.size() / 25) == 0) {
                            // update progress
                            publishProgress(plantCounter * 100 / allPlants.size());
                        }
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                    try {
                        allPlants = offlinePlantDAO.fetchPlants(params[0]);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                try {
                    allPlants = offlinePlantDAO.fetchPlants(params[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return allPlants;
        }


        @Override
        protected void onPreExecute() {
            // Setup our plant progress dialog
            plantProgressDialog = new ProgressDialog(GPSAPlant.this);
            plantProgressDialog.setCancelable(true);
            plantProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            plantProgressDialog.setProgressStyle(0);
            plantProgressDialog.setMax(100);
            plantProgressDialog.setMessage(getString(R.string.downladingPlantNames));

            // make a button.
            plantProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.lblCancel), new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });


            plantProgressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            plantProgressDialog.setProgress(values[0]);
        }
    }

    class PlantSelected implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // get the selected item.
            PlantDTO plant = (PlantDTO) actPlantName.getAdapter().getItem(position);
            cacheID = plant.getCacheID();
            guid = plant.getGuid();

        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // get the selected item.
            PlantDTO plant = (PlantDTO) actPlantName.getAdapter().getItem(position);
            cacheID = plant.getCacheID();
            guid = plant.getGuid();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}





