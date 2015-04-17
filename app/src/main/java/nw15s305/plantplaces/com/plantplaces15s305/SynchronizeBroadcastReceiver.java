package nw15s305.plantplaces.com.plantplaces15s305;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.widget.Toast;

public class SynchronizeBroadcastReceiver extends BroadcastReceiver {
    public SynchronizeBroadcastReceiver() {
    }

    boolean power = false;
    boolean wifi = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
            power = true;
        } else if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
            power = false;
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        
        if (activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            NetworkInfo.DetailedState detailedState = networkInfo.getDetailedState();
            if (detailedState == NetworkInfo.DetailedState.CONNECTED) {
                wifi = true;
            } else if (detailedState == NetworkInfo.DetailedState.DISCONNECTED || detailedState == NetworkInfo.DetailedState.DISCONNECTING) {
                pauseActiveProcess();
            } else {
                wifi = false;
            }
        } else {
            wifi = false;
        }

        if (power && wifi) {
            upload(context);
        }
        
        if (wifi) {
            download(context);
        }
    }

    private void pauseActiveProcess() {

    }

    private void download(Context context) {
        Toast.makeText(context, "Downloading...", Toast.LENGTH_LONG).show();
    }

    private void upload(Context context) {
        Toast.makeText(context, "Upload...", Toast.LENGTH_LONG).show();
    }
}
