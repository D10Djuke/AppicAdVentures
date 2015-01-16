package adventures.ad.appic.main.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.location.LocationListener;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.location.Location;
import android.location.LocationManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

import adventures.ad.appic.app.R;

public class MapActivity extends FragmentActivity implements LocationListener {

    private GoogleMap mMap = null; // Might be null if Google Play services APK is not available.
    private ArrayList<Marker> markers = new ArrayList<Marker>();
    private LocationManager locationManager;
    // private Location myLocation;

    public void msbox() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Packing List");
        alertDialog.setMessage("Could not find the file.");
        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        // myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(50.938288, 5.348595)).title("PXL gebouw B")));
                markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(50.835884, 5.189746)).title("Ergens in St. Truiden")));
                markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(50.855321, 5.383759)).title("Thuis")));
                markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(51.855321, 5.383759)).title("Random1")));
                markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(52.855321, 5.383759)).title("Random2")));
            }
        }
        for (int i = 0; i < markers.size(); i++) {
            markers.get(i).setVisible(false);
        }
    }

    private double rad(double x) {
        return x*Math.PI/180;
    }

    private void findMarkers(Location myLocation) {
        if(myLocation != null) {
            double lat = myLocation.getLatitude();
            double lng = myLocation.getLongitude();
            int R = 6371; // radius of earth in km
            for (int i = 0; i < markers.size(); i++) {
                double mlat = markers.get(i).getPosition().latitude;
                double mlng = markers.get(i).getPosition().longitude;
                double dLat = rad(mlat - lat);
                double dLong = rad(mlng - lng);
                double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(rad(lat)) * Math.cos(rad(lat)) * Math.sin(dLong / 2) * Math.sin(dLong / 2);
                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                double d = R * c;
                if (d < 5) {       //radius in km
                    markers.get(i).setVisible(true);
                }
                else{
                    markers.get(i).setVisible(false);
                }
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        if (location != null)
        {
            findMarkers(location);
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }
}