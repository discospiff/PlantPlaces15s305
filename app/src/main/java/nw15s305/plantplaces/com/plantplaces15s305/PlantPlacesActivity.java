package nw15s305.plantplaces.com.plantplaces15s305;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by jonesb on 4/7/2015.
 */
public abstract class PlantPlacesActivity extends ActionBarActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gpsaplant, menu);

        int currentMenuId = getCurrentMenuId();
        // if we have a menu ID, remove that from our menu.
        if (currentMenuId != 0) {
            menu.removeItem(currentMenuId);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is invoked when the user clicks the GPS A Plant Menu option.
     * @param menuItem
     */
    public void gpsAPlantClicked(MenuItem menuItem) {
        Intent gpsAPlantIntent = new Intent(this, GPSAPlant.class);
        startActivity(gpsAPlantIntent);
    }

    /**
     *
     * @param menuItem
     */
    public void searchByColorClicked(MenuItem menuItem) {
        Intent searchByColorIntent = new Intent(this, ColorCaptureActivity.class);
        startActivity(searchByColorIntent);
    }

    public abstract int getCurrentMenuId();
}
