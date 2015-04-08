package nw15s305.plantplaces.com.plantplaces15s305;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ColorCaptureActivity extends PlantPlacesActivity {

    @Override
    public int getCurrentMenuId() {
        return R.id.capturecolor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_capture);
    }


}
