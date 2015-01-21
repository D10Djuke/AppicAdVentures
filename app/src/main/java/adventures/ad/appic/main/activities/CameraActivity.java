package adventures.ad.appic.main.activities;



import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.wikitude.architect.ArchitectView;

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
