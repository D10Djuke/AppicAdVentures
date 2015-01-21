package adventures.ad.appic.main.activities;



import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.wikitude.architect.ArchitectView;

import adventures.ad.appic.app.R;
import adventures.ad.appic.main.custom.MessageBox;
import adventures.ad.appic.main.custom.WikitudeConstants;

public class CameraActivity extends FragmentActivity {

    private ArchitectView arView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        init();
    }

    private void init(){

        new MessageBox("Game Over",isCameraInUse(), MessageBox.Type.ERROR_BOX,this).popMessage();
        this.arView = (ArchitectView) findViewById(R.id.architectView);
        final ArchitectView.ArchitectConfig config = new ArchitectView.ArchitectConfig(WikitudeConstants.getWikitudeSdkKey());
        this.arView.onCreate(config);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);

        if(arView != null){
            this.arView.onPostCreate();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(arView != null) {
            this.arView.onPause();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(arView != null){
            this.arView.onDestroy();
        }
    }

    private String isCameraInUse(){

        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
                && ((Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP))) {
            return Integer.toString(Camera.getNumberOfCameras());

        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            return "21";
        } else {
            return "null";
        }


       /*
        Camera c = null;
        try{
            c = Camera.open();
        }catch (RuntimeException e){
            c.release();
            return "true";
        }finally{
            if(c != null) c.release();
        }
        return "false";
        */
    }

}
