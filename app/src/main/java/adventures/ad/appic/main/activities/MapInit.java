package adventures.ad.appic.main.activities;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

import adventures.ad.appic.app.R;
import adventures.ad.appic.main.custom.MessageBox;

/**
 * Created by vivid_000 on 2/1/2015.
 */
public class MapInit extends FragmentActivity {

    private ArrayList<Marker> markers = new ArrayList<Marker>();
    private boolean FirstLocation = true;
    private ArrayList<Polygon> polygons = new ArrayList<Polygon>();


    public void mapInit(GoogleMap map) {
        findMarkers(map.getMyLocation());
        updateMap(map);
    }


    public void initMarkers(GoogleMap mMap) {
        markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(50.938288, 5.348595)).title("PXL gebouw B")));
        markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(50.835884, 5.189746)).title("Ergens in St. Truiden")));
        markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(50.855321, 5.383759)).title("Thuis")));
        markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(51.855321, 5.383759)).title("Random1")));
        markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(52.855321, 5.383759)).title("Random2")));

        for (int i = 0; i < markers.size(); i++) {
            markers.get(i).setVisible(false);
        }
    }

    private void updateMap(GoogleMap mMap) {

        if(polygons.size() != 0) {
            polygons.get(0).remove();
        }

        List circle = new ArrayList();

        for(int i = -180; i < 180; i++)
        {
            circle.add(computeOffset(new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()), 5, i));
        }

        PolygonOptions polygonOptions = new PolygonOptions()
                .add(new LatLng(85, -180), new LatLng(85, -90), new LatLng(85, 0), new LatLng(85, 90), new LatLng(85, 179.9), new LatLng(-85, 179.9), new LatLng(-85, 90), new LatLng(-85, 0), new LatLng(-85, -90), new LatLng(-85, -180), new LatLng(85, -180))
                .addHole(circle)
                .fillColor(0x50000000)
                .strokeColor(0x50000000)
                .strokeWidth(1);

        polygons.add(0, mMap.addPolygon(polygonOptions));
    }

    public boolean testConnection(GoogleMap mMap, int CONNECTIONATTEMPTS, int[] c, Context context) {
        if (c[0] < CONNECTIONATTEMPTS) {
            mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(0, 0)));
            c[0] = c[0] + 1;
        }
        if (c[0] == CONNECTIONATTEMPTS) {
            c[0] = c[0] + 1;
            MessageBox message = new MessageBox("NO SIGNAL", "Can't find GPS signal", MessageBox.Type.TEST_BOX, context);
            message.popMessage();
            return false;
        }
        return true;
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
    private static LatLng computeOffset(LatLng from, double distance, double heading) {
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
}
