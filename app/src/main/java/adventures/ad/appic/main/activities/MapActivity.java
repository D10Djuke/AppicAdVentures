package adventures.ad.appic.main.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationListener;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.location.Location;
import android.location.LocationManager;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adventures.ad.appic.app.R;
import adventures.ad.appic.main.custom.MessageBox;

public class MapActivity extends FragmentActivity implements LocationListener {

    private GoogleMap mMap = null; // Might be null if Google Play services APK is not available.
    private ArrayList<Marker> markers = new ArrayList<Marker>();
    private LocationManager locationManager;
    private boolean FirstLocation = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        setUpMapIfNeeded();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_settings){
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_back) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirstLocation = true;
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
                mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                    @Override
                    public void onCameraChange(CameraPosition cameraPosition) {

                        if(mMap.getMyLocation() != null) {
                            if (FirstLocation) {
                               // mMap.addCircle(new CircleOptions().center(new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude())).radius(5000).fillColor(Color.RED));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()), 11));
                                FirstLocation = false;
                            }
                            findMarkers(mMap.getMyLocation());

                            List circle = new ArrayList();

                            for(int i = -180; i < 180; i++)
                            {
                                circle.add(computeOffset(new LatLng(mMap.getMyLocation().getLatitude(),mMap.getMyLocation().getLongitude()), 5, i));
                            }

                            mMap.addPolygon(new PolygonOptions()
                                    .add(new LatLng(85, -180), new LatLng(85, -90), new LatLng(85, 0), new LatLng(85, 90), new LatLng(85, 179.9), new LatLng(-85, 179.9), new LatLng(-85, 90), new LatLng(-85, 0),new LatLng(-85, -90),new LatLng(-85, -180), new LatLng(85, -180))
                                    .addHole(circle)
                                    .fillColor(Color.BLACK)
                                    .strokeWidth(1));
                        }

                    }
                });
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(50.938288, 5.348595)).title("PXL gebouw B")));
                markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(50.835884, 5.189746)).title("Ergens in St. Truiden")));
                markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(50.855321, 5.383759)).title("Thuis")));
                markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(51.855321, 5.383759)).title("Random1")));
                markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(52.855321, 5.383759)).title("Random2")));



                for (int i = 0; i < markers.size(); i++) {
                    markers.get(i).setVisible(false);
                }
                mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(5.93813318, 5.34826098)));
            }
        }
    }

    private double rad(double x) {
        return x*Math.PI/180;
    }

    /**
     * Returns the LatLng resulting from moving a distance from an origin
     * in the specified heading (expressed in degrees clockwise from north).
     * @param from     The LatLng from which to start.
     * @param distance The distance to travel.
     * @param heading  The heading in degrees clockwise from north.
     */
    public static LatLng computeOffset(LatLng from, double distance, double heading) {
        distance /= 6371;
        heading = Math.toRadians(heading);
        // http://williams.best.vwh.net/avform.htm#LL
        double fromLat = Math.toRadians(from.latitude);
        double fromLng = Math.toRadians(from.longitude);
        double cosDistance = Math.cos(distance);
        double sinDistance = Math.sin(distance);
        double sinFromLat = Math.sin(fromLat);
        double cosFromLat = Math.cos(fromLat);
        double sinLat = cosDistance * sinFromLat + sinDistance * cosFromLat * Math.cos(heading);
        double dLng = Math.atan2(
                sinDistance * cosFromLat * Math.sin(heading),
                cosDistance - sinFromLat * sinLat);
        return new LatLng(Math.toDegrees(Math.asin(sinLat)), Math.toDegrees(fromLng + dLng));
    }

    private void findMarkers(Location myLocation) {
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
              //  new MessageBox("Distance to marker",d +"", MessageBox.Type.MESSAGE_BOX,this).popMessage();
                if (d < 5) {       //radius in km
                    markers.get(i).setVisible(true);
                }
                else{
                    markers.get(i).setVisible(false);
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