package adventures.ad.appic.main.activities;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.ArchitectView.ArchitectConfig;

import adventures.ad.appic.app.R;
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
        final ArchitectConfig config = new ArchitectConfig(WikitudeConstants.getWikitudeSdkKey());

        this.arView = (ArchitectView) this.findViewById(R.id.architectView);
        this.arView.onCreate(config);
    }

    /*
    private boolean isCameraInUse(){
        Camera c = null;
        try{
            c = Camera.open();
        }catch (RuntimeException e){
            c.release();
            return true;
        }finally{
            if(c != null) c.release();
        }
        return false;
    }

     */



}
