package adventures.ad.appic.main.activities;


import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import adventures.ad.appic.app.R;
import adventures.ad.appic.game.Player;
import adventures.ad.appic.main.custom.MessageBox;
import adventures.ad.appic.manager.DataManager;

public class MainActivity extends ActionBarActivity {

    //private Account myAccount = new Account(getApplicationContext());

    private DataManager mDataMan;
    private Player mPlayer;
    private String events;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        //mDataMan = (DataManager) intent.getParcelableExtra("mDataMan");
        mPlayer = (Player) intent.getParcelableExtra("mPlayer");

        if(true) {

            setContentView(R.layout.activity_main);
            ((TextView) findViewById(R.id.charName)).setText(mPlayer.getName());
            ((TextView) findViewById(R.id.charLvl)).setText(mPlayer.getLevelAsText());

            ((ImageView) findViewById(R.id.ivBackpack)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), InventoryActivity.class);
                    i.putExtra("mPlayer", mPlayer);
                    startActivity(i);
                }
            });

            ((ImageView) findViewById(R.id.ivMap)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), MapActivity.class);
                    i.putExtra("mPlayer", mPlayer);
                    startActivityForResult(i, 1);

                }
            });

            ((TextView) findViewById(R.id.cam)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        startAugmentedReality();
                }
            });


        }else{
            new MessageBox("Device Error","Your Device is not Supported", MessageBox.Type.ERROR_BOX,this).popMessage();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                events = data.getStringExtra("mEvents");
                ((TextView) findViewById(R.id.tvEvents)).setText(events);
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private synchronized void startAugmentedReality(){

        int cameraId = -1;

        for(int i = 0; i < Camera.getNumberOfCameras();i++){
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if(info.facing == Camera.CameraInfo.CAMERA_FACING_BACK){
                cameraId = i;
            }
        }

        if(isCameraInUsebyApp(cameraId)){
            new MessageBox("CameraError", "Your Camera is already in use", MessageBox.Type.ERROR_BOX, this);
        }else{
            Intent i = new Intent(getApplicationContext(), CameraPreview.class);
            startActivity(i);
        }
    }

    private boolean isCameraInUsebyApp(int cameraId) {
        Camera camera = null;
        try {
            camera = Camera.open(cameraId);
        } catch (RuntimeException e) {
            return true;
        } finally {
            if (camera != null) camera.release();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.action_exit) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
