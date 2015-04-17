package nw15s305.plantplaces.com.plantplaces15s305;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class GPSAPlant extends PlantPlacesActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private double longitude;
    private double latitude;
    private TextView lblLongitudeValue;
    private TextView lblLatitudeValue;
    private boolean paused = false;
    private Button btnPause;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // associate the layout with this activity.
        setContentView(R.layout.activity_gpsaplant);

        actPlantName = (AutoCompleteTextView) findViewById(R.id.actPlantName);

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

    }

    /**
     * This method will be invoked when a button is clicked, and that button's onClick method is btnShowSavedClicked
     * @param v
     */
    public void btnShowSavedClicked(View v) {
        // get the name the user entered.
        String plantName = actPlantName.getText().toString();

        String foo = plantName;

        // show the user the name entered.
        Toast.makeText(this, plantName, Toast.LENGTH_LONG).show();
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
        Uri pictureUri = Uri.fromFile(imageFile);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
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
}
