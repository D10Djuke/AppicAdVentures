package adventures.ad.appic.main.activities;

import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.location.Location;
import android.location.LocationManager;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import adventures.ad.appic.app.R;
import adventures.ad.appic.game.Player;
import adventures.ad.appic.main.custom.MessageBox;
import adventures.ad.appic.web.Connection;

import static java.lang.Thread.sleep;

public class MapActivity extends FragmentActivity implements LocationListener {

    private GoogleMap mMap = null; // Might be null if Google Play services APK is not available.
   // private ArrayList<Marker> markers = new ArrayList<Marker>();
    private LocationManager locationManager;
    private Location loc;
    private boolean FirstLocation = true;
    private MapInit mapInit = new MapInit();
    final int CONNECTIONATTEMPTS = 1000;
    final int[] c = {0};
    private Player mPlayer;
    private Connection con = null;

   /* List circle;
    private ArrayList<Polygon> polygons = new ArrayList<Polygon>();
    PolygonOptions polygonOptions;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Intent intent = getIntent();
        mPlayer = (Player) intent.getParcelableExtra("mPlayer");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        con = new Connection(this);
        new DownloadFilesTask().execute();
        setUpMapIfNeeded();
    }

    private class DownloadFilesTask extends AsyncTask<Void, Void, Boolean> {
        protected void onPreExecute() {

        }

        protected Boolean doInBackground(Void... urls) {
         //   con.getLocations();

            return false;
        }

        protected void onPostExecute(Boolean result) {

           // MessageBox message = new MessageBox("NO SIGNAL", con.getLocationlist().get(0).getName(), MessageBox.Type.TEST_BOX, MapActivity.this);
        /*    MessageBox message = new MessageBox("test", loc.toString() + " ",  MessageBox.Type.MESSAGE_BOX, MapActivity.this);
            message.popMessage();*/
        }
    }

    private class InitTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... urls) {
            mapInit.initMarkers(mMap, con);

            return null;
        }

        protected void onProgressUpdate(Void... progress) {

        }

        protected void onPostExecute(Void result) {

        }
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
            recreate();
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
        Intent i = new Intent();
        if(mapInit.checkLocationService(locationManager)) {
                // Do a null check to confirm that we have not already instantiated the map.
                if (mMap == null) {
                    // Try to obtain the map from the SupportMapFragment.
                    mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                            .getMap();
                    // Setting a custom info window adapter for the google map
                    mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                        // Use default InfoWindow frame
                        @Override
                        public View getInfoWindow(Marker arg0) {
                            return null;
                        }


                        // Defines the contents of the InfoWindow
                        @Override
                        public View getInfoContents(Marker arg0) {

                            // Getting view from the layout file info_window_layout
                            View v = getLayoutInflater().inflate(R.layout.marker_layout, null);
                           // ((TextView) findViewById(R.id.tvName)).setText(con.getLocation(arg0.getPosition()).getName());
                            Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

                            if (display.getRotation() == Surface.ROTATION_0) {
                                v.setLayoutParams(new RelativeLayout.LayoutParams(400, 600));
                            }

                            if (display.getRotation() == Surface.ROTATION_90) {
                                v.setLayoutParams(new RelativeLayout.LayoutParams(600, 400));
                            }

                            if (display.getRotation() == Surface.ROTATION_180) {
                                v.setLayoutParams(new RelativeLayout.LayoutParams(400, 600));
                            }

                            if (display.getRotation() == Surface.ROTATION_270) {
                                v.setLayoutParams(new RelativeLayout.LayoutParams(600, 400));
                            }

                            //v.setScaleX(0.5f);
                            //v.setScaleY(0.5f);
                            // Returning the view containing InfoWindow contents
                            return v;
                        }
                    });

                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {

                            if (mapInit.checkLocationService(locationManager)) {
                                if (mapInit.inRange(loc, marker)) {
                                    if (mapInit.isFacing(loc, marker)) {
                                        Intent i = new Intent(MapActivity.this, CameraPreview.class);
                                        i.putExtra("mPlayer", mPlayer);
                                        MapActivity.this.startActivity(i);
                                    }
                                        else{
                                            MessageBox message = new MessageBox("Target out of sight", "Please face the target.", MessageBox.Type.MESSAGE_BOX, MapActivity.this);
                                            message.popMessage();
                                        }
                                }
                                else{
                                    MessageBox message = new MessageBox("Target out of range", "Please get within 5 meters of the target.", MessageBox.Type.MESSAGE_BOX, MapActivity.this);
                                    message.popMessage();
                                }
                            } else {
                                Intent i = new Intent();
                                i.putExtra("mEvents", "No Signal!");
                                setResult(RESULT_OK, i);
                                MessageBox message = new MessageBox("NO SIGNAL", "Can't find GPS signal", MessageBox.Type.TEST_BOX, MapActivity.this);
                                message.popMessage();
                            }
                        }
                    });

                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                        @Override
                        public boolean onMarkerClick(Marker marker) {

                            marker.showInfoWindow();
                            return true;
                        }
                    });
                    // Check if we were successful in obtaining the map.
                    if (mMap != null) {
                        mMap.setMyLocationEnabled(true);
                        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                            @Override
                            public void onCameraChange(CameraPosition cameraPosition) {
                                Intent i = new Intent();
                                if (mMap.getMyLocation() == null) {
                                    if (mapInit.checkLocationService(locationManager)) {
                                        if (!mapInit.testConnection(mMap, CONNECTIONATTEMPTS, c)) {
                                            i.putExtra("mEvents", "No Signal!");
                                            setResult(RESULT_OK, i);
                                            MessageBox message = new MessageBox("NO SIGNAL", "Can't find GPS signal", MessageBox.Type.TEST_BOX, MapActivity.this);
                                            message.popMessage();
                                        }
                                    } else {
                                        i.putExtra("mEvents", "No Signal!");
                                        setResult(RESULT_OK, i);
                                        MessageBox message = new MessageBox("NO SIGNAL", "Location Service Disabled", MessageBox.Type.TEST_BOX, MapActivity.this);
                                        message.popMessage();
                                    }
                                }
                                if (mMap.getMyLocation() != null) {

                                    loc = mMap.getMyLocation();
                                    if (FirstLocation) {
                                        // mMap.addCircle(new CircleOptions().center(new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude())).radius(5000).fillColor(Color.RED));
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()), 11));
                                        FirstLocation = false;
                                    }
                                    i.putExtra("mEvents", mapInit.mapInit(mMap) + " event(s) nearby.");
                                    setResult(RESULT_OK, i);
                                }
                            }
                        });
                        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        mapInit.initMarkers(mMap, con);

                       // new InitTask().execute();
                    }
                }
            }
        else {
            i.putExtra("mEvents", "No Signal!");
            setResult(RESULT_OK, i);
            MessageBox message = new MessageBox("NO SIGNAL", "Location Service Disabled", MessageBox.Type.TEST_BOX, MapActivity.this);
            message.popMessage();
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