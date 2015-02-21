package adventures.ad.appic.main.custom;

import android.content.Context;
import android.hardware.Camera;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import adventures.ad.appic.main.activities.CameraActivity;

/**
 * Created by vivid_000 on 2/19/2015.
 */
public class AR {

    private Context context;
    private Camera camera = null;
    private float horizontalFOV;
    private float verticalFOV;
    private float camAspect;
    private float displayWidth;
    public float displayHeight;
    private float displayAspect;
    private float trueVerticalFOV;
    private float trueHorizontalFOV;
    private Location myLocation;
    private Location targetLocation;
    private float distance;
    private double blindSpotY;
    private double trueDistance;
    final double MAXVIEWDISTANCEY = 4000;
    private double baseDistance;
    private double maxViewDistanceX;
    private LatLng marker;
    private float firstAngle;
    private boolean firstTry = true;
    private float angleChange;


    public AR (Camera cam, Context c, Location loc, LatLng marker){
        this.camera = cam;
        this.context = c;
        this.marker = marker;
        this.myLocation = loc;
        targetLocation = new Location("");
        targetLocation.setLatitude(marker.latitude);
        targetLocation.setLongitude(marker.longitude);
        distance = myLocation.distanceTo(targetLocation);
        init();
    }

    private void init(){
        horizontalFOV = camera.getParameters().getHorizontalViewAngle();
        verticalFOV = camera.getParameters().getVerticalViewAngle();
        camAspect = horizontalFOV/verticalFOV;
        displayWidth = context.getResources().getDisplayMetrics().widthPixels;
        displayHeight = context.getResources().getDisplayMetrics().heightPixels;
        displayAspect = displayWidth/displayHeight;
        trueVerticalFOV = horizontalFOV/displayAspect;
        trueHorizontalFOV = verticalFOV/displayAspect;
        blindSpotY = (1.6*Math.sin(trueVerticalFOV))/Math.sin(90);
        trueDistance = distance - blindSpotY;
        maxViewDistanceX = 2*((4000*Math.sin(degreesToRadians(90-trueHorizontalFOV)))/Math.sin(90));
        baseDistance = (((4*100)/(25*0.07540625))*Math.sin(degreesToRadians(90-trueHorizontalFOV)))/Math.sin(degreesToRadians(trueHorizontalFOV));

    }

    private double degreesToRadians(double degrees){
        return degrees*0.0174532925;
    }

    public float scaling() {
        float result = 1;
        result = (float)(distance/baseDistance);

        return 1/result;
    }

    private double meterToPixels(int meters){
        return meters*3779.527559055;
    }

    private double pixelToMeters(double pixels){
        return pixels*0.000264583;
    }

    public float convertY(){
        float result = 0;

        result = (float)(displayHeight - (((((displayHeight/2)-285)*100)/MAXVIEWDISTANCEY)*distance));

      //  result = (float) (((MAXVIEWDISTANCEY-blindSpotY)/displayHeight)*trueDistance);

        return result;
    }

    public float getAngle(Location loc, float bearing){

        float bearingTo;
        float angle;
        Location dest = new Location("");
        dest.setLatitude(marker.latitude);
        dest.setLongitude(marker.longitude);
        bearingTo = loc.bearingTo(dest);

        if (bearingTo < 0){
            bearingTo = 360 + bearingTo;
        }
        if (bearing < 0 ) {
            bearing = 360 + bearing;
        }
      //  Log.d("Heading: " , ""+bearing);
      //  Log.d("BearingTo: ", "" + bearingTo);
        return angle = bearing - bearingTo; //returns direction u are facing compared to the object
    }

    public float convertX(Location loc, float bearing){
        float result = 0;
        double directionDistance;
        float angle = getAngle(loc, bearing);

        if(angle < -180){
            angle = angle+360;
        }
            if (firstTry){
                firstAngle = 90-angle;
                firstTry = false;
                angleChange = angle;
            }
        if(Math.abs(angleChange -angle) > 5) {
            angleChange = angle;
            if (angle >= 0) {
                Log.e("first", firstAngle + "");
                Log.e("new", angle + "");
                directionDistance = (distance * Math.sin(degreesToRadians(angle))) / Math.sin(degreesToRadians(180 - firstAngle - angle));

            } else {
                Log.e("first", firstAngle + "");
                Log.e("new", angle + "");
                directionDistance = (distance * Math.sin(degreesToRadians(angle))) / Math.sin(degreesToRadians(firstAngle - angle));
            }

            result = (float) ((maxViewDistanceX / displayWidth) * directionDistance);
            return result;
        }

        directionDistance = (distance * Math.sin(degreesToRadians(angleChange))) / Math.sin(degreesToRadians(firstAngle - angleChange));
        result = (float) ((maxViewDistanceX / displayWidth) * directionDistance);

        return result;
    }
}
