package nw15s305.plantplaces.com.plantplaces15s305;

import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class GPSAPlant extends PlantPlacesActivity {

    @Override
    public int getCurrentMenuId() {
        return R.id.gpsaplant;
    }

    public static final int CAMERA_REQUEST = 10;
    private AutoCompleteTextView actPlantName;
    private ImageView imgSpecimenPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // associate the layout with this activity.
        setContentView(R.layout.activity_gpsaplant);

        actPlantName = (AutoCompleteTextView) findViewById(R.id.actPlantName);

        // get access to the image view.
        imgSpecimenPhoto = (ImageView) findViewById(R.id.imgSpecimenPhoto);

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
        Toast.makeText(this, "CLicked", Toast.LENGTH_LONG).show();
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
}
